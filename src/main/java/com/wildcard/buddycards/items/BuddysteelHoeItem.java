package com.wildcard.buddycards.items;

import com.wildcard.buddycards.BuddyCards;
import com.wildcard.buddycards.util.BuddysteelGearHelper;
import net.minecraft.block.BlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.HoeItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Rarity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;
import net.minecraftforge.common.ToolType;

import java.util.List;

public class BuddysteelHoeItem extends HoeItem {
    public BuddysteelHoeItem() {
        super(BuddysteelItemTier.BUDDYSTEEL, -2, -1.0f, new Properties().group(BuddyCards.TAB));
    }

    @Override
    public void addInformation(ItemStack stack, World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        BuddysteelGearHelper.addInformation(stack, tooltip);
        super.addInformation(stack, worldIn, tooltip, flagIn);
    }

    @Override
    public Rarity getRarity(ItemStack stack) {
        return Rarity.UNCOMMON;
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
        BuddysteelGearHelper.setTag(playerIn, handIn);
        return super.onItemRightClick(worldIn, playerIn, handIn);
    }

    @Override
    public float getDestroySpeed(ItemStack stack, BlockState state) {
        if (!stack.hasTag())
            return super.getDestroySpeed(stack, state);
        else
            return super.getDestroySpeed(stack, state) + (int) (4 * stack.getTag().getFloat("completion"));
    }

    @Override
    public int getHarvestLevel(ItemStack stack, ToolType tool, PlayerEntity player, BlockState state) {
        if (!stack.hasTag())
            return super.getHarvestLevel(stack, tool, player, state);
        else
            return super.getHarvestLevel(stack, tool, player, state) + (int) (2 * stack.getTag().getFloat("completion"));
    }
}
