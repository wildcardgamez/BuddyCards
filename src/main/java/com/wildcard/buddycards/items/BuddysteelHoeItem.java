package com.wildcard.buddycards.items;

import com.wildcard.buddycards.BuddyCards;
import com.wildcard.buddycards.util.RatioFinder;
import net.minecraft.block.BlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.HoeItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Rarity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.common.ToolType;

import java.util.List;

public class BuddysteelHoeItem extends HoeItem {
    public BuddysteelHoeItem() {
        super(BuddysteelItemTier.BUDDYSTEEL, -2, -1.0f, new Properties().group(BuddyCards.TAB));
    }

    @Override
    public void addInformation(ItemStack stack, World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        tooltip.add(new TranslationTextComponent("item." + BuddyCards.MOD_ID + ".buddysteel_gear.desc1"));
        if(stack.hasTag())
            tooltip.add(new TranslationTextComponent( "item." + BuddyCards.MOD_ID + ".buddysteel_gear.desc2")
                .appendString(String.valueOf((int)(stack.getTag().getFloat("completion") * 100)))
                .append(new TranslationTextComponent( "item." + BuddyCards.MOD_ID + ".buddysteel_gear.desc3")));
        tooltip.add(new TranslationTextComponent("item." + BuddyCards.MOD_ID + ".buddysteel_gear.desc4"));
        super.addInformation(stack, worldIn, tooltip, flagIn);
    }

    @Override
    public Rarity getRarity(ItemStack stack) {
        return Rarity.UNCOMMON;
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
        if(playerIn instanceof ServerPlayerEntity) {
            CompoundNBT nbt = playerIn.getHeldItem(handIn).getTag();
            nbt.putFloat("completion", RatioFinder.getRatio((ServerPlayerEntity) playerIn));
        }
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
