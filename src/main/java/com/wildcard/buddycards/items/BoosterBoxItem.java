package com.wildcard.buddycards.items;

import com.wildcard.buddycards.blocks.BoosterBoxBlock;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import java.util.List;

public class BoosterBoxItem extends BlockItem {
    public BoosterBoxItem(BoosterBoxBlock block, Properties properties, int setNumber) {
        super(block, properties);
        SET = setNumber;
    }

    final int SET;

    @Override
    public void appendHoverText(ItemStack stack, Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
        //Set name and explanation of what the pack is
        tooltip.add(new TranslatableComponent("item.buddycards.set." + SET));
    }
}
