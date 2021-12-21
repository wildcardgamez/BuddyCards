package com.wildcard.buddycards.integration.aquaculture;

import com.teammetallurgy.aquaculture.api.fishing.Hook;
import com.teammetallurgy.aquaculture.item.AquaFishingRodItem;
import com.wildcard.buddycards.BuddyCards;
import com.wildcard.buddycards.registries.BuddycardsItems;
import com.wildcard.buddycards.util.ConfigManager;
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

import java.util.List;

public class AquacultureIntegration {
    public static void init() {
        BUDDY_HOOK = new Hook.HookBuilder("buddycard").setModID("buddycards").setColor(TextFormatting.AQUA).setDurabilityChance(0.20).build();
        BUDDYSTEEL_FILET_KNIFE = BuddycardsItems.ITEMS.register("buddysteel_fillet_knife", BuddysteelFilletKnifeItem::new);
        BUDDYSTEEL_FISHING_ROD = BuddycardsItems.ITEMS.register("buddysteel_fishing_rod", BuddysteelFishingRodItem::new);
    }

    static Hook BUDDY_HOOK;
    public static RegistryObject<Item> BUDDYSTEEL_FILET_KNIFE;
    public static RegistryObject<Item> BUDDYSTEEL_FISHING_ROD;

    @SubscribeEvent
    public void onItemFished(ItemFishedEvent event) {
        float oddsOfGettingBuddyLoot = 0f;
        ItemStack rod = event.getPlayer().getMainHandItem();
        //Make sure it's not in the offhand
        if(event.getPlayer().getOffhandItem().getItem() instanceof AquaFishingRodItem)
            rod = event.getPlayer().getOffhandItem();
        //If the rod has a buddysteel hook, give it the chance to get a pack
        if(AquaFishingRodItem.getHookType(rod).equals(BUDDY_HOOK))
            oddsOfGettingBuddyLoot += ConfigManager.aquacultureFishingChance.get();
        //If the rod is a buddysteel rod, give it a chance based on the completion level of the player
        if(rod.getItem() instanceof BuddysteelFishingRodItem && rod.hasTag())
            oddsOfGettingBuddyLoot += ConfigManager.aquacultureFishingChance.get() * 2 * rod.getTag().getFloat("completion");
        //With the odds added up, pray to RNGesus
        if(oddsOfGettingBuddyLoot != 0 && Math.random() < oddsOfGettingBuddyLoot) {
            //Roll the special loot
            List<ItemStack> list = event.getPlayer().getServer().getLootTables().get(
                    new ResourceLocation(BuddyCards.MOD_ID, "item/aquaculture_buddyhook")).getRandomItems(
                    new LootContext.Builder(event.getPlayer().getServer().getLevel(event.getPlayer().getCommandSenderWorld().dimension()))
                            .withRandom(event.getPlayer().getCommandSenderWorld().random).create(LootParameterSets.EMPTY));
            //Throw the loot at the player from the same position as the fish
            for(ItemStack itemstack : list) {
                ItemEntity itementity = new ItemEntity(event.getHookEntity().level, event.getHookEntity().getX(), event.getHookEntity().getY(), event.getHookEntity().getZ(), itemstack);
                double d0 = event.getPlayer().getX() - event.getHookEntity().getX();
                double d1 = event.getPlayer().getY() - event.getHookEntity().getY();
                double d2 = event.getPlayer().getZ() - event.getHookEntity().getZ();
                itementity.setDeltaMovement(d0 * 0.1D, d1 * 0.1D + Math.sqrt(Math.sqrt(d0 * d0 + d1 * d1 + d2 * d2)) * 0.08D, d2 * 0.1D);
                event.getHookEntity().level.addFreshEntity(itementity);
                event.getPlayer().level.addFreshEntity(new ExperienceOrbEntity(event.getPlayer().level, event.getPlayer().getX(), event.getPlayer().getY() + 0.5D, event.getPlayer().getZ() + 0.5D, event.getPlayer().getCommandSenderWorld().random.nextInt(6) + 1));
            }
        }
    }
    
    
}
