package com.wildcard.buddycards.items;

import com.wildcard.buddycards.BuddyCards;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.ToolType;

import javax.annotation.Nullable;

public class ZylexBandItem extends Item {
    public ZylexBandItem() {
        super(new Item.Properties().tab(BuddyCards.TAB).stacksTo(1).fireResistant());
    }

    @Override
    public int getHarvestLevel(ItemStack stack, ToolType tool, @Nullable PlayerEntity player, @Nullable BlockState blockState) {
        return 2;
    }

    @Override
    public float getDestroySpeed(ItemStack p_150893_1_, BlockState p_150893_2_) {
        return 10f;
    }
}
