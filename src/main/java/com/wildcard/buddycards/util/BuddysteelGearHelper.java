package com.wildcard.buddycards.util;

import com.wildcard.buddycards.BuddyCards;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.fml.ModList;

import java.util.List;

public class BuddysteelGearHelper {
    public static void addInformation(ItemStack stack, List<ITextComponent> tooltip) {
        if(stack.hasTag())
            tooltip.add(new TranslationTextComponent( "item." + BuddyCards.MOD_ID + ".buddysteel_gear.desc1")
                    .append(String.valueOf((int)(stack.getTag().getFloat("completion") * 100)))
                    .append(new TranslationTextComponent( "item." + BuddyCards.MOD_ID + ".buddysteel_gear.desc2")));
        tooltip.add(new TranslationTextComponent("item." + BuddyCards.MOD_ID + ".buddysteel_gear.desc3"));
    }

    public static void setTag(PlayerEntity playerIn, Hand handIn) {
        if(playerIn instanceof ServerPlayerEntity) {
            if(!playerIn.getItemInHand(handIn).hasTag())
                playerIn.getItemInHand(handIn).setTag(new CompoundNBT());
            CompoundNBT nbt = playerIn.getItemInHand(handIn).getTag();
            nbt.putFloat("completion", getRatio((ServerPlayerEntity) playerIn));
        }
    }

    public static float getRatio(ServerPlayerEntity player) {
        return BuddycardsCollectionSaveData.get((player).getLevel()).getPower(player);
    }
}
