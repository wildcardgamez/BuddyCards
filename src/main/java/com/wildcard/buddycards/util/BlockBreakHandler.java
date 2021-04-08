package com.wildcard.buddycards.util;

import com.wildcard.buddycards.blocks.*;

import com.wildcard.buddycards.blocks.tiles.BuddysteelVaultTile;
import com.wildcard.buddycards.blocks.tiles.CardDisplayTile;
import com.wildcard.buddycards.blocks.tiles.CardStandTile;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class BlockBreakHandler
{
    @SubscribeEvent
    public void blockBreak(BlockEvent.BreakEvent event)
    {
    	BlockState targetBlock = event.getWorld().getBlockState(event.getPos());
    	// I think it is possible for the player to be null if something like the EnderDragon is doing this?
    	PlayerEntity player = event.getPlayer();
    	if ( player == null ) {
			return;
		}
		// this makes a locked card display absolutely indestructable 
    	if (targetBlock.getBlock() instanceof CardDisplayBlock) {
			CardDisplayTile tile = (CardDisplayTile) event.getWorld().getBlockEntity(event.getPos());

			if (tile.isLocked() && !player.isCreative()) {
				player.displayClientMessage(new TranslationTextComponent("block.buddycards.card_display.lock"), true);
				event.setCanceled(true);
			}
		}
		// this makes a locked card stand absolutely indestructable 
    	if (targetBlock.getBlock() instanceof CardStandBlock) {
			CardStandTile tile = (CardStandTile) event.getWorld().getBlockEntity(event.getPos());
			if (tile.isLocked() && !player.isCreative()) {
				player.displayClientMessage(new TranslationTextComponent("block.buddycards.card_display.lock"), true);
				event.setCanceled(true);
			}
		}
		// this makes a locked card stand absolutely indestructable
		if (targetBlock.getBlock() instanceof BuddysteelVaultBlock) {
			BuddysteelVaultTile tile = (BuddysteelVaultTile) event.getWorld().getBlockEntity(event.getPos());
			if (tile.isLocked() && !player.isCreative()) {
				player.displayClientMessage(new TranslationTextComponent("block.buddycards.vault.lock"), true);
				event.setCanceled(true);
			}
		}
	}
}
