package com.wildcard.buddycards.integration.aquaculture;

import com.teammetallurgy.aquaculture.api.fishing.Hook;
import com.teammetallurgy.aquaculture.item.AquaFishingRodItem;
import com.wildcard.buddycards.BuddyCards;
import com.wildcard.buddycards.items.BuddysteelSwordItem;
import com.wildcard.buddycards.util.ConfigManager;
import com.wildcard.buddycards.util.RegistryHandler;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.item.ExperienceOrbEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootContext;
import net.minecraft.loot.LootParameterSets;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.event.entity.player.ItemFishedEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.RegistryObject;

import javax.annotation.Nonnull;
import java.util.List;

public class AquacultureIntegration {
    public static void init() {
        BUDDY_HOOK = new Hook.HookBuilder("buddycard").setModID("buddycards").setColor(TextFormatting.AQUA).setDurabilityChance(0.20).build();
        BUDDYSTEEL_FILET_KNIFE = RegistryHandler.ITEMS.register("buddysteel_fillet_knife", BuddysteelFilletKnifeItem::new);
        BUDDYSTEEL_FISHING_ROD = RegistryHandler.ITEMS.register("buddysteel_fishing_rod", BuddysteelFishingRodItem::new);
    }

    static Hook BUDDY_HOOK;
    public static RegistryObject<Item> BUDDYSTEEL_FILET_KNIFE;
    public static RegistryObject<Item> BUDDYSTEEL_FISHING_ROD;

    @SubscribeEvent
    public void onItemFished(ItemFishedEvent event) {
        float oddsOfGettingBuddyLoot = 0;
        ItemStack rod = event.getPlayer().getHeldItemMainhand();
        //If the rod has a buddysteel hook, give it the chance to get a pack
        if(AquaFishingRodItem.getHookType(rod).equals(BUDDY_HOOK))
            oddsOfGettingBuddyLoot += ConfigManager.aquacultureFishingChance.get();
        //If the rod is a buddysteel rod, give it a chance based on the completion level of the player
        if(rod.getItem() instanceof BuddysteelFishingRodItem && rod.hasTag())
            oddsOfGettingBuddyLoot += ConfigManager.aquacultureFishingChance.get() * 2 * rod.getTag().getFloat("completion");
        //With the odds added up, pray to RNGesus
        if(oddsOfGettingBuddyLoot != 0 && Math.random() < oddsOfGettingBuddyLoot) {
            //Roll the special loot
            List<ItemStack> list = event.getPlayer().getServer().getLootTableManager().getLootTableFromLocation(
                    new ResourceLocation(BuddyCards.MOD_ID, "inject/aquaculture_buddyhook")).generate(
                    new LootContext.Builder(event.getPlayer().getServer().getWorld(event.getPlayer().getEntityWorld().getDimensionKey()))
                            .withRandom(event.getPlayer().getEntityWorld().rand).build(LootParameterSets.EMPTY));
            //Throw the loot at the player from the same position as the fish
            for(ItemStack itemstack : list) {
                ItemEntity itementity = new ItemEntity(event.getHookEntity().world, event.getHookEntity().getPosX(), event.getHookEntity().getPosY(), event.getHookEntity().getPosZ(), itemstack);
                double d0 = event.getPlayer().getPosX() - event.getHookEntity().getPosX();
                double d1 = event.getPlayer().getPosY() - event.getHookEntity().getPosY();
                double d2 = event.getPlayer().getPosZ() - event.getHookEntity().getPosZ();
                itementity.setMotion(d0 * 0.1D, d1 * 0.1D + Math.sqrt(Math.sqrt(d0 * d0 + d1 * d1 + d2 * d2)) * 0.08D, d2 * 0.1D);
                event.getHookEntity().world.addEntity(itementity);
                event.getPlayer().world.addEntity(new ExperienceOrbEntity(event.getPlayer().world, event.getPlayer().getPosX(), event.getPlayer().getPosY() + 0.5D, event.getPlayer().getPosZ() + 0.5D, event.getPlayer().getEntityWorld().rand.nextInt(6) + 1));
            }
        }
    }
    
    
}
