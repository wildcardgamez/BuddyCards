package com.wildcard.buddycards.blocks;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Blocks;
import net.minecraft.block.OreBlock;
import net.minecraft.util.math.MathHelper;

import java.util.Random;

public class LuminisOreBlock extends OreBlock {
    public LuminisOreBlock() {
        super(AbstractBlock.Properties.copy(Blocks.GOLD_ORE).lightLevel((state) -> 1));
    }

    @Override
    protected int xpOnDrop(Random random) {
        return MathHelper.nextInt(random, 1, 5);
    }
}
