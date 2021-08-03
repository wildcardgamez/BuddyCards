package com.wildcard.buddycards.datagen;

import com.wildcard.buddycards.BuddyCards;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.forge.event.lifecycle.GatherDataEvent;

@Mod.EventBusSubscriber(modid = BuddyCards.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class DataGen {

    @SubscribeEvent
    static void onGatherData(GatherDataEvent event) {
        event.getGenerator().addProvider(new CardModelGen(event.getGenerator(), BuddyCards.MOD_ID, event.getExistingFileHelper()));
    }
}
