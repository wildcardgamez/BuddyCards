package com.wildcard.buddycards.items;

import com.wildcard.buddycards.BuddyCards;
import com.wildcard.buddycards.integration.CuriosIntegration;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.fml.ModList;

import java.util.List;

public class
SetMedalItem extends MedalItem {
    public SetMedalItem(int setNumber, MedalTypes type) {
        super(type);
        SET_NUMBER = setNumber;
    }

    final int SET_NUMBER;

    public void appendHoverText(ItemStack stack, World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        //Show name of who claimed the medal
        if (stack.hasTag())
            tooltip.add(new TranslationTextComponent("item.buddycards.medal.desc").append(stack.getTag().getString("Collector")));
    }

    @Override
    public ActionResult<ItemStack> use(World worldIn, PlayerEntity playerIn, Hand handIn) {
        //If the medal isn't signed, let the player sign it
        if(!playerIn.getItemInHand(handIn).hasTag()) {
            CompoundNBT nbt = new CompoundNBT();
            nbt.putString("Collector", playerIn.getName().getString());
            playerIn.getItemInHand(handIn).setTag(nbt);
            return ActionResult.success(playerIn.getItemInHand(handIn));
        }
        return super.use(worldIn, playerIn, handIn);
    }

    @Override
    public void fillItemCategory(ItemGroup group, NonNullList<ItemStack> items) {
        if(SET_NUMBER == 4 && !ModList.get().isLoaded("byg"))
            return;
        else if(SET_NUMBER == 5 && !ModList.get().isLoaded("create"))
            return;
        else if(SET_NUMBER == 6 && !ModList.get().isLoaded("aquaculture"))
            return;
        else if(SET_NUMBER == 7 && !ModList.get().isLoaded("farmersdelight"))
            return;
        super.fillItemCategory(group, items);
    }

    @Override
    public boolean hasContainerItem(ItemStack stack) {
        return true;
    }

    @Override
    public ItemStack getContainerItem(ItemStack itemStack) {
        return itemStack.copy();
    }
}
