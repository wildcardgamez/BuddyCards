package com.wildcard.buddycards.datagen;

import com.wildcard.buddycards.BuddyCards;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.GatherDataEvent;

@Mod.EventBusSubscriber(modid = BuddyCards.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class DataGeneration {
    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        if(event.includeServer()) {
            event.getGenerator().addProvider(new RecipeGen(event.getGenerator()));
        }
    }
}
