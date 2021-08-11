package com.wildcard.buddycards.util;

import com.google.common.collect.ImmutableList;
import com.wildcard.buddycards.BuddyCards;
import com.wildcard.buddycards.registries.BuddycardsBlocks;
import net.minecraft.core.Registry;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.data.worldgen.Features;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

public class OreGeneration {
    public static ConfiguredFeature<?,?> LUMINIS_ORE;

    public static void setup(FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            LUMINIS_ORE = Feature.ORE.configured(new OreConfiguration(ImmutableList.of(
                    OreConfiguration.target(OreConfiguration.Predicates.STONE_ORE_REPLACEABLES,  BuddycardsBlocks.LUMINIS_ORE.get().defaultBlockState()),
                    OreConfiguration.target(OreConfiguration.Predicates.DEEPSLATE_ORE_REPLACEABLES, BuddycardsBlocks.LUMINIS_ORE_DEEPSLATE.get().defaultBlockState())),
                    ConfigManager.luminisVeinSize.get())
            ).rangeTriangle(VerticalAnchor.bottom(), VerticalAnchor.absolute(ConfigManager.luminisMaxY.get())).squared().count(ConfigManager.luminisPerChunk.get());

            Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, new ResourceLocation(BuddyCards.MOD_ID, "luminis_ore"), LUMINIS_ORE);
        });
    }

    @SubscribeEvent
    public void genOres(final BiomeLoadingEvent event) {
        event.getGeneration().getFeatures(GenerationStep.Decoration.UNDERGROUND_ORES).add(() -> LUMINIS_ORE);
    }
}
