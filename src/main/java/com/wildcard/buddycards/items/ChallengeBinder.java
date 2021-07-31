package com.wildcard.buddycards.items;

import com.wildcard.buddycards.util.ConfigManager;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.core.NonNullList;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.level.Level;

import java.util.List;

public class ChallengeBinder extends BinderItem{
    public ChallengeBinder() {
        super(1, "buddycards");
    }

    @Override
    public void appendHoverText(ItemStack stack, Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
        if(stack.hasTag())
            tooltip.add(new TranslatableComponent("item.buddycards.points_info").append("" + stack.getTag().getInt("points")));
    }

    @Override
    public void fillItemCategory(CreativeModeTab group, NonNullList<ItemStack> items) {
        if(ConfigManager.challengeMode.get())
            super.fillItemCategory(group, items);
    }
}
