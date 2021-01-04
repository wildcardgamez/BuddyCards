package com.wildcard.buddycards.items;

import com.wildcard.buddycards.BuddyCards;
import com.wildcard.buddycards.integration.CuriosIntegration;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.fml.ModList;

import java.util.List;

public class MedalItem extends Item {
    public MedalItem(int set) {
        super(new Item.Properties().group(BuddyCards.TAB).maxStackSize(1).isImmuneToFire());
        SET_NUMBER = set;
    }

    final int SET_NUMBER;

    public void addInformation(ItemStack stack, World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        //Show name of who claimed the medal
        if (stack.hasTag())
            tooltip.add(new TranslationTextComponent("item.buddycards.medal.desc").appendString(stack.getTag().getString("Collector")));
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
        if(!playerIn.getHeldItem(handIn).hasTag()) {
            CompoundNBT nbt = new CompoundNBT();
            nbt.putString("Collector", playerIn.getName().getString());
            playerIn.getHeldItem(handIn).setTag(nbt);
            return ActionResult.resultSuccess(playerIn.getHeldItem(handIn));
        }
        return super.onItemRightClick(worldIn, playerIn, handIn);
    }

    @Override
    public ICapabilityProvider initCapabilities(final ItemStack stack, CompoundNBT unused) {
        if (ModList.get().isLoaded("curios")) {
            return CuriosIntegration.initCapabilities(SET_NUMBER);
        }
        return super.initCapabilities(stack, unused);
    }
}
