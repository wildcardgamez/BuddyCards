package com.wildcard.buddycards.integration;

import com.teammetallurgy.aquaculture.api.fishing.Hook;
import com.teammetallurgy.aquaculture.item.AquaFishingRodItem;
import com.wildcard.buddycards.BuddyCards;
import com.wildcard.buddycards.util.ConfigManager;
import com.wildcard.buddycards.util.LootInjectionHandler;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootContext;
import net.minecraft.loot.LootParameterSets;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.LootTables;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.event.LootTableLoadEvent;
import net.minecraftforge.event.entity.player.ItemFishedEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AquacultureIntegration {
    public static void init() {
        BUDDY_HOOK = new Hook.HookBuilder("buddycard").setModID("buddycards").setColor(TextFormatting.AQUA).setDurabilityChance(0.20).build();
    }

    private static Hook BUDDY_HOOK;

    private Map<String, NonNullList<ItemStack>> items = new HashMap<String, NonNullList<ItemStack>>();

    @SubscribeEvent
    public static void itemFished(ItemFishedEvent event) {
        if(event.getPlayer().getHeldItemMainhand().getItem() instanceof AquaFishingRodItem &&
                AquaFishingRodItem.getHookType(event.getPlayer().getHeldItemMainhand()).equals(BUDDY_HOOK) &&
                Math.random() < 1/*ConfigManager.aquacultureFishingChance.get()*/) {

        }
    }

    @SubscribeEvent
    public static void addLoot(LootTableLoadEvent event) {

    }
}
