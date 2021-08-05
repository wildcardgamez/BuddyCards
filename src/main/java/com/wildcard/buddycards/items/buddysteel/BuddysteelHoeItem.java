package com.wildcard.buddycards.items.buddysteel;

import com.wildcard.buddycards.BuddyCards;
import com.wildcard.buddycards.util.BuddysteelGearHelper;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.InteractionHand;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.ToolType;

import java.util.List;

import net.minecraft.world.item.Item.Properties;

public class BuddysteelHoeItem extends HoeItem {
    public BuddysteelHoeItem(BuddysteelItemTier tier, int damage) {
        super(tier, damage, -1.0f, new Properties().tab(BuddyCards.TAB));
    }

    @Override
    public void appendHoverText(ItemStack stack, Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
        BuddysteelGearHelper.addInformation(stack, tooltip);
        super.appendHoverText(stack, worldIn, tooltip, flagIn);
    }

    @Override
    public Rarity getRarity(ItemStack stack) {
        return getTier().equals(BuddysteelItemTier.BUDDYSTEEL) ? Rarity.UNCOMMON : Rarity.EPIC;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level worldIn, Player playerIn, InteractionHand handIn) {
        BuddysteelGearHelper.setTag(playerIn, handIn);
        return super.use(worldIn, playerIn, handIn);
    }

    @Override
    public float getDestroySpeed(ItemStack stack, BlockState state) {
        float eff = super.getDestroySpeed(stack, state);
        if (stack.hasTag() && stack.getTag().contains("completion") && eff == speed)
            return eff + (int) (4 * stack.getTag().getFloat("completion"));
        else
            return eff;
    }

    @Override
    public int getHarvestLevel(ItemStack stack, ToolType tool, Player player, BlockState state) {
        if (!stack.hasTag() || !stack.getTag().contains("completion"))
            return super.getHarvestLevel(stack, tool, player, state);
        else
            return super.getHarvestLevel(stack, tool, player, state) + (int) (2 * stack.getTag().getFloat("completion"));
    }

    @Override
    public void fillItemCategory(CreativeModeTab group, NonNullList<ItemStack> items) {
        //Add an alternative with max power for creative menu
        ItemStack maxed = new ItemStack(this);
        CompoundTag nbt = new CompoundTag();
        nbt.putFloat("completion", 1);
        maxed.setTag(nbt);
        items.add(maxed);
        super.fillItemCategory(group, items);
    }
}
