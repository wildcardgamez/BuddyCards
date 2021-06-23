package com.wildcard.buddycards.util;

import java.util.List;
import java.util.Random;

import com.google.common.collect.Lists;
import com.wildcard.buddycards.blocks.CardDisplayBlock;
import com.wildcard.buddycards.blocks.tiles.CardDisplayTile;
import com.wildcard.buddycards.blocks.CardStandBlock;
import com.wildcard.buddycards.blocks.tiles.CardStandTile;

import com.wildcard.buddycards.registries.BuddycardsBlocks;
import com.wildcard.buddycards.registries.BuddycardsItems;
import net.minecraft.block.BlockState;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.item.MerchantOffer;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.event.world.ExplosionEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class BlockExplodeHandler 
{
	@SubscribeEvent
	public void explosionModify(ExplosionEvent.Detonate event) {
		// this list of blocks will replace the list of blocks broken by the explosion
		List<BlockPos> replacedExplosion = Lists.newArrayList();
		int deepLuminisBlocks = 0;
		// loop through the list of exploded blocks, but do not modify it yet
		for (int i = 0; i < event.getAffectedBlocks().size(); i++) {
			BlockState targetBlock = event.getWorld().getBlockState(event.getAffectedBlocks().get(i));

			// exclude this block from the explosion if it is a locked CardDisplayBlock
			if (targetBlock.getBlock() instanceof CardDisplayBlock) {
				CardDisplayTile tile = (CardDisplayTile) event.getWorld().getBlockEntity(event.getAffectedBlocks().get(i));
				if (!tile.isLocked()) {
					replacedExplosion.add(event.getAffectedBlocks().get(i));
				}
			}
			// exclude this block from the explosion if it is a locked CardStandBlock
			else if (targetBlock.getBlock() instanceof CardStandBlock) {
				CardStandTile tile = (CardStandTile) event.getWorld().getBlockEntity(event.getAffectedBlocks().get(i));
				if (!tile.isLocked()) {
					replacedExplosion.add(event.getAffectedBlocks().get(i));
				}
			}
			//Count the block if it's deep luminis
			else if (targetBlock.getBlock().equals(BuddycardsBlocks.DEEP_LUMINIS_BLOCK.get())) {
				deepLuminisBlocks++;
				replacedExplosion.add(event.getAffectedBlocks().get(i));
			} else {
				// every other block explodes like normal
				replacedExplosion.add(event.getAffectedBlocks().get(i));
			}
		}
		// replace the explosion with the same explosion, but without the blocks I excluded
		event.getExplosion().clearToBlow();
		event.getAffectedBlocks().addAll(replacedExplosion);
		//Check if the explosion creates a luminis item, and create it if so
		if(Math.random() * 2 < deepLuminisBlocks) {
			InventoryHelper.dropItemStack(event.getWorld(), event.getExplosion().getPosition().x, event.getExplosion().getPosition().y, event.getExplosion().getPosition().z,
					createLuminisDrop(event.getWorld().getRandom(), deepLuminisBlocks));
		}
	}

	public static ItemStack createLuminisDrop(Random random, int luminisIn) {
		double rand = Math.random() * 2;
		if(rand%1 > .92) {
			ItemStack medal = new ItemStack(BuddycardsItems.LUMINIS_MEDAL.get());
			if((int) rand != 0)
				EnchantmentHelper.enchantItem(random, medal, 10 * luminisIn, true);
			return medal;
		}
		else if(rand%1 > .8) {
			ItemStack pick = new ItemStack(BuddycardsItems.LUMINIS_PICKAXE.get());
			if((int) rand != 0)
				EnchantmentHelper.enchantItem(random, pick, 10 * luminisIn, true);
			return pick;
		}
		else if(rand%1 > .4)
			return new ItemStack(BuddycardsItems.LUMINIS_RING.get(), 1);
		else
			return new ItemStack(BuddycardsItems.LUMINIS.get(), (int) (rand * 32));
	}
}
