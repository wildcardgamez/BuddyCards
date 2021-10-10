package com.wildcard.buddycards.blocks;

import com.wildcard.buddycards.registries.BuddycardsItems;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.state.BlockState;

public class YannelCropBlock extends CropBlock {
    public YannelCropBlock() {
        super(Properties.copy(Blocks.WHEAT));
    }

    @Override
    protected ItemLike getBaseSeedId() {
        return BuddycardsItems.YANNEL_SEEDS.get();
    }

    @Override
    protected int getBonemealAgeIncrease(Level level) {
        return Mth.nextInt(level.random, 0, 2);
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader levelReader, BlockPos pos) {
        return levelReader.getBiome(pos).getBiomeCategory().equals(Biome.BiomeCategory.THEEND) && state.is(Blocks.FARMLAND);
    }
}
