package com.wildcard.buddycards.integration;

import com.teammetallurgy.aquaculture.api.fishing.Hook;
import com.teammetallurgy.aquaculture.item.AquaFishingRodItem;
import com.wildcard.buddycards.BuddyCards;
import com.wildcard.buddycards.util.ConfigManager;
import net.minecraft.entity.item.ExperienceOrbEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootContext;
import net.minecraft.loot.LootParameterSets;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.event.entity.player.ItemFishedEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.List;

public class AquacultureIntegration {
    public static void init() {
        BUDDY_HOOK = new Hook.HookBuilder("buddycard").setModID("buddycards").setColor(TextFormatting.AQUA).setDurabilityChance(0.20).build();
    }

    static Hook BUDDY_HOOK;

    @SubscribeEvent
    public void onItemFished(ItemFishedEvent event) {
        if(AquaFishingRodItem.getHookType(event.getPlayer().getHeldItemMainhand()).equals(BUDDY_HOOK) &&
                Math.random() < ConfigManager.aquacultureFishingChance.get()) {
            List<ItemStack> list = event.getPlayer().getServer().getLootTableManager().getLootTableFromLocation(
                    new ResourceLocation(BuddyCards.MOD_ID, "inject/aquaculture_buddyhook")).generate(
                    new LootContext.Builder(event.getPlayer().getServer().getWorld(event.getPlayer().getEntityWorld().getDimensionKey()))
                            .withRandom(event.getPlayer().getEntityWorld().rand).build(LootParameterSets.EMPTY));
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
