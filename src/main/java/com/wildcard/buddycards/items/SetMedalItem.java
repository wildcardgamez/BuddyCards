package com.wildcard.buddycards.items;

import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.InteractionHand;
import net.minecraft.core.NonNullList;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.level.Level;
import net.minecraftforge.fml.ModList;

import java.util.List;

public class
SetMedalItem extends MedalItem {
    public SetMedalItem(int setNumber, MedalTypes type) {
        super(type);
        SET_NUMBER = setNumber;
    }

    final int SET_NUMBER;

    public void appendHoverText(ItemStack stack, Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
        //Show name of who claimed the medal
        if (stack.hasTag())
            tooltip.add(new TranslatableComponent("item.buddycards.medal.desc").append(stack.getTag().getString("Collector")));
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level worldIn, Player playerIn, InteractionHand handIn) {
        //If the medal isn't signed, let the player sign it
        if(!playerIn.getItemInHand(handIn).hasTag()) {
            CompoundTag nbt = new CompoundTag();
            nbt.putString("Collector", playerIn.getName().getString());
            playerIn.getItemInHand(handIn).setTag(nbt);
            return InteractionResultHolder.success(playerIn.getItemInHand(handIn));
        }
        return super.use(worldIn, playerIn, handIn);
    }

    @Override
    public void fillItemCategory(CreativeModeTab group, NonNullList<ItemStack> items) {
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
