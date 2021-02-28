package com.wildcard.buddycards.util;

import com.wildcard.buddycards.blocks.CardDisplayBlock;
import com.wildcard.buddycards.blocks.CardDisplayTile;
import com.wildcard.buddycards.blocks.CardStandBlock;
import com.wildcard.buddycards.blocks.CardStandTile;

import net.minecraft.block.BlockState;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class BlockBreakHandler
{
    @SubscribeEvent
    public void blockBreak(BlockEvent.BreakEvent event)
    {
    	BlockState targetBlock = event.getWorld().getBlockState(event.getPos());
    	
		// this makes a locked card display absolutely indestructable 
    	if (targetBlock.getBlock() instanceof CardDisplayBlock)
    	{
    		CardDisplayTile tile = (CardDisplayTile)event.getWorld().getTileEntity(event.getPos());
    		if (tile.isLocked() )
    		{
            	event.setCanceled(true);
    		}
    	}

		// this makes a locked card stand absolutely indestructable 
    	if (targetBlock.getBlock() instanceof CardStandBlock)
    	{
    		CardStandTile tile = (CardStandTile)event.getWorld().getTileEntity(event.getPos());
    		if (tile.isLocked() )
    		{
            	event.setCanceled(true);
    		}
    	}    	
    }
}
