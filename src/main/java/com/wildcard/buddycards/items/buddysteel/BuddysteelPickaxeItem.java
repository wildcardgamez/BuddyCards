package com.wildcard.buddycards.items.buddysteel;

import com.wildcard.buddycards.BuddyCards;
import com.wildcard.buddycards.util.BuddysteelGearHelper;
import net.minecraft.block.BlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.PickaxeItem;
import net.minecraft.item.Rarity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;
import net.minecraftforge.common.ToolType;

import java.util.List;

public class BuddysteelPickaxeItem extends PickaxeItem {
    public BuddysteelPickaxeItem(BuddysteelItemTier tier) {
        super(tier, 1, -2.8f, new Item.Properties().tab(BuddyCards.TAB));
    }

    @Override
    public void appendHoverText(ItemStack stack, World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        BuddysteelGearHelper.addInformation(stack, tooltip);
        super.appendHoverText(stack, worldIn, tooltip, flagIn);
    }

    @Override
    public Rarity getRarity(ItemStack stack) {
        return getTier().equals(BuddysteelItemTier.BUDDYSTEEL) ? Rarity.COMMON : Rarity.EPIC;
    }

    @Override
    public ActionResult<ItemStack> use(World worldIn, PlayerEntity playerIn, Hand handIn) {
        BuddysteelGearHelper.setTag(playerIn, handIn);
        return super.use(worldIn, playerIn, handIn);
    }

    @Override
    public float getDestroySpeed(ItemStack stack, BlockState state) {
        float eff = super.getDestroySpeed(stack, state);
        if (stack.hasTag() && eff == speed)
            return eff + (int) (4 * stack.getTag().getFloat("completion"));
        else
            return eff;
    }

    @Override
    public int getHarvestLevel(ItemStack stack, ToolType tool, PlayerEntity player, BlockState state) {
        if (!stack.hasTag())
            return super.getHarvestLevel(stack, tool, player, state);
        else
            return super.getHarvestLevel(stack, tool, player, state) + (int) (2 * stack.getTag().getFloat("completion"));
    }
}
