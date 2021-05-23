package com.wildcard.buddycards.util;

import com.wildcard.buddycards.BuddyCards;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.OreFeatureConfig;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

public class OreGeneration {
    public static ConfiguredFeature<?,?> LUMINIS_ORE;

    public static void setup(FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            LUMINIS_ORE = Feature.ORE.configured(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, RegistryHandler.LUMINIS_ORE.get()
                    .defaultBlockState(), 12)).range(24).squared().count(2);
            Registry.register(WorldGenRegistries.CONFIGURED_FEATURE, new ResourceLocation(BuddyCards.MOD_ID, "luminis_ore"), LUMINIS_ORE);
        });
    }

    @SubscribeEvent
    public void genOres(final BiomeLoadingEvent event) {
        event.getGeneration().getFeatures(GenerationStage.Decoration.UNDERGROUND_ORES).add(() -> LUMINIS_ORE);
    }
}
