package com.wildcard.buddycards.items;

import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import java.util.List;

public class SetMedalItem extends MedalItem{
    public SetMedalItem(MedalTypes type, String requiredMod, int set) {
        super(type, requiredMod);
        SET_NUMBER = set;
    }

    private final int SET_NUMBER;

    @Override
    public void appendHoverText(ItemStack stack, Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
        tooltip.add(new TranslatableComponent("item.buddycards.set." + SET_NUMBER));
    }
}
