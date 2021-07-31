package com.wildcard.buddycards.util;

import com.wildcard.buddycards.blocks.*;

import com.wildcard.buddycards.blocks.tiles.BuddysteelVaultBlockEntity;
import com.wildcard.buddycards.blocks.tiles.CardDisplayBlockEntity;
import com.wildcard.buddycards.blocks.tiles.CardStandBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.entity.player.Player;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class BlockBreakHandler
{
    @SubscribeEvent
    public void blockBreak(BlockEvent.BreakEvent event)
    {
    	BlockState targetBlock = event.getWorld().getBlockState(event.getPos());
    	// I think it is possible for the player to be null if something like the EnderDragon is doing this?
    	Player player = event.getPlayer();
    	if ( player == null ) {
			return;
		}
		// this makes a locked card display absolutely indestructable 
    	if (targetBlock.getBlock() instanceof CardDisplayBlock) {
			CardDisplayBlockEntity tile = (CardDisplayBlockEntity) event.getWorld().getBlockEntity(event.getPos());

			if (tile.isLocked() && !player.isCreative()) {
				player.displayClientMessage(new TranslatableComponent("block.buddycards.card_display.lock"), true);
				event.setCanceled(true);
			}
		}
		// this makes a locked card stand absolutely indestructable 
    	if (targetBlock.getBlock() instanceof CardStandBlock) {
			CardStandBlockEntity tile = (CardStandBlockEntity) event.getWorld().getBlockEntity(event.getPos());
			if (tile.isLocked() && !player.isCreative()) {
				player.displayClientMessage(new TranslatableComponent("block.buddycards.card_display.lock"), true);
				event.setCanceled(true);
			}
		}
		// this makes a locked card stand absolutely indestructable
		if (targetBlock.getBlock() instanceof BuddysteelVaultBlock) {
			BuddysteelVaultBlockEntity tile = (BuddysteelVaultBlockEntity) event.getWorld().getBlockEntity(event.getPos());
			if (tile.isLocked() && !player.isCreative()) {
				player.displayClientMessage(new TranslatableComponent("block.buddycards.vault.lock"), true);
				event.setCanceled(true);
			}
		}
	}
}
