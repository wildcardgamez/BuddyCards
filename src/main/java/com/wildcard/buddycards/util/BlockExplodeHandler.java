package com.wildcard.buddycards.util;

import java.util.List;

import com.google.common.collect.Lists;
import com.wildcard.buddycards.blocks.CardDisplayBlock;
import com.wildcard.buddycards.blocks.CardDisplayTile;
import com.wildcard.buddycards.blocks.CardStandBlock;
import com.wildcard.buddycards.blocks.CardStandTile;

import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.event.world.ExplosionEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class BlockExplodeHandler 
{
	@SubscribeEvent
	public void explosionModify(ExplosionEvent.Detonate event) {
		// this list of blocks will replace the list of blocks broken by the explosion
		List<BlockPos> replacedExplosion = Lists.newArrayList();

		// loop through the list of exploded blocks, but do not modify it yet
		for (int i = 0; i < event.getAffectedBlocks().size(); i++) {
			BlockState targetBlock = event.getWorld().getBlockState(event.getAffectedBlocks().get(i));

			// exclude this block from the explosion if it is a locked CardDisplayBlock
			if (targetBlock.getBlock() instanceof CardDisplayBlock) {
				CardDisplayTile tile = (CardDisplayTile) event.getWorld().getTileEntity(event.getAffectedBlocks().get(i));
				if (!tile.isLocked()) {
					replacedExplosion.add(event.getAffectedBlocks().get(i));
				}
			}
			// exclude this block from the explosion if it is a locked CardStandBlock
			else if (targetBlock.getBlock() instanceof CardStandBlock) {
				CardStandTile tile = (CardStandTile) event.getWorld().getTileEntity(event.getAffectedBlocks().get(i));
				if (!tile.isLocked()) {
					replacedExplosion.add(event.getAffectedBlocks().get(i));
				}
			} else {
				// every other block explodes like normal
				replacedExplosion.add(event.getAffectedBlocks().get(i));
			}
		}
		// replace the explosion with the same explosion, but without the blocks I excluded
		event.getExplosion().clearAffectedBlockPositions();
		event.getAffectedBlocks().addAll(replacedExplosion);
	}
}
