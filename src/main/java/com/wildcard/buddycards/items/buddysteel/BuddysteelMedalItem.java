package com.wildcard.buddycards.items.buddysteel;

import com.wildcard.buddycards.items.MedalItem;
import com.wildcard.buddycards.items.MedalTypes;
import com.wildcard.buddycards.util.BuddysteelGearHelper;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.InteractionHand;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.Level;

import java.util.List;

public class BuddysteelMedalItem extends MedalItem {
    public BuddysteelMedalItem(MedalTypes type) {
        super(MedalTypes.PERFECT);
    }

    @Override
    public void appendHoverText(ItemStack stack, Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
        BuddysteelGearHelper.addInformation(stack, tooltip);
        super.appendHoverText(stack, worldIn, tooltip, flagIn);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level worldIn, Player playerIn, InteractionHand handIn) {
        BuddysteelGearHelper.setTag(playerIn, handIn);
        return super.use(worldIn, playerIn, handIn);
    }
}
