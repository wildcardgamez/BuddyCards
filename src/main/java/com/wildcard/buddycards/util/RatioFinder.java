package com.wildcard.buddycards.util;

import com.wildcard.buddycards.BuddyCards;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.ModList;

public class RatioFinder {
    public static float getRatio(ServerPlayerEntity player) {
        int max = 3;
        int sets = 0;
        if (player.getAdvancements().getProgress(player.server.getAdvancementManager().getAdvancement(new ResourceLocation(BuddyCards.MOD_ID, "main/complete_set1s"))).isDone())
            sets++;
        if (player.getAdvancements().getProgress(player.server.getAdvancementManager().getAdvancement(new ResourceLocation(BuddyCards.MOD_ID, "main/complete_set2s"))).isDone())
            sets++;
        if (player.getAdvancements().getProgress(player.server.getAdvancementManager().getAdvancement(new ResourceLocation(BuddyCards.MOD_ID, "main/complete_set3s"))).isDone())
            sets++;
        if (ModList.get().isLoaded("byg")) {
            max++;
            if (player.getAdvancements().getProgress(player.server.getAdvancementManager().getAdvancement(new ResourceLocation(BuddyCards.MOD_ID, "main/complete_set4s"))).isDone())
                sets++;
        }
        if (ModList.get().isLoaded("create")) {
            max++;
            if (player.getAdvancements().getProgress(player.server.getAdvancementManager().getAdvancement(new ResourceLocation(BuddyCards.MOD_ID, "main/complete_set5s"))).isDone())
                sets++;
        }
        if (ModList.get().isLoaded("aquaculture")) {
            max++;
            if (player.getAdvancements().getProgress(player.server.getAdvancementManager().getAdvancement(new ResourceLocation(BuddyCards.MOD_ID, "main/complete_set6s"))).isDone())
                sets++;
        }
        if (sets > max)
            sets = max;
        return (float) sets / max;
    }
}
