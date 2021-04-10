package com.wildcard.buddycards.items;

import com.wildcard.buddycards.util.ConfigManager;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

import java.util.List;

public class ChallengeBinder extends BinderItem{
    public ChallengeBinder() {
        super(1);
    }

    @Override
    public void addInformation(ItemStack stack, World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        if(stack.hasTag())
            tooltip.add(new TranslationTextComponent("item.buddycards.points_info").appendString("" + stack.getTag().getInt("points")));
    }

    @Override
    public void fillItemGroup(ItemGroup group, NonNullList<ItemStack> items) {
        if(ConfigManager.challengeMode.get())
            super.fillItemGroup(group, items);
    }
}
