package com.wildcard.buddycards.items;

import com.wildcard.buddycards.BuddyCards;
import com.wildcard.buddycards.util.RegistryHandler;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.items.ItemHandlerHelper;

import java.util.List;

public class CardItem extends Item {
    public CardItem(int setNumber, int cardNumber, boolean isShiny) {
        super(new Item.Properties().group(BuddyCards.TAB));
        SET_NUMBER = setNumber;
        CARD_NUMBER = cardNumber;
        SHINY = isShiny;
    }

    public final int SET_NUMBER;
    public final int CARD_NUMBER;
    public final boolean SHINY;

    @Override
    public void addInformation(ItemStack stack, World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        //Add the card description
        tooltip.add(new TranslationTextComponent("item.buddycards.card." + SET_NUMBER + "." + CARD_NUMBER + ".tooltip"));
        TranslationTextComponent cn = new TranslationTextComponent("item.buddycards.number_separator");
        cn.appendString("" + CARD_NUMBER);
        //Add the star to the prefix when it's a shiny variant
        if (SHINY)
            cn.append(new TranslationTextComponent("item.buddycards.shiny_symbol"));
        //Add the card info (SetName - Card# Shiny symbol)
        tooltip.add(new TranslationTextComponent("item.buddycards.set." + SET_NUMBER).append(cn));
        //Add the grading if the card is graded
        if (stack.getTag() != null && stack.getTag().getInt("grade") != 0)
            tooltip.add(new TranslationTextComponent("item.buddycards.grade_info").append(
                    new TranslationTextComponent("item.buddycards.grade_" + stack.getTag().getInt("grade"))));
    }

    @Override
    public boolean hasEffect(ItemStack stack) {
        //Make shiny cards have enchant glow, and non-shiny ones not
        return SHINY;
    }

    @Override
    public Rarity getRarity(ItemStack stack) {
        //The higher the card number the rarer the card
        if(CARD_NUMBER <= 12)
            return Rarity.COMMON;
        else if(CARD_NUMBER <= 21)
            return Rarity.UNCOMMON;
        else if(CARD_NUMBER <= 25)
            return Rarity.RARE;
        return Rarity.EPIC;
    }

    @Override
    public void fillItemGroup(ItemGroup group, NonNullList<ItemStack> items) {
        if(SET_NUMBER == 4 && !ModList.get().isLoaded("byg"))
            return;
        else if(SET_NUMBER == 5 && !ModList.get().isLoaded("create"))
            return;
        super.fillItemGroup(group, items);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
        if(handIn == Hand.MAIN_HAND) {
            if (playerIn.getHeldItem(Hand.OFF_HAND).getItem() == RegistryHandler.GRADING_SLEEVES.get()) {
                CompoundNBT nbt = playerIn.getHeldItem(handIn).getTag();
                if (nbt == null)
                    nbt = new CompoundNBT();
                if (nbt.getInt("grade") == 0) {
                    //Take the grading sleeve
                    playerIn.getHeldItem(Hand.OFF_HAND).shrink(1);
                    //Get a grade using maths for rarity
                    int i = (int) (Math.random() * 200) + 1;
                    int grade;
                    if(i < 85)
                        grade = 1;
                    else if(i < 140)
                        grade = 2;
                    else if(i < 180)
                        grade = 3;
                    else if (i < 200)
                        grade = 4;
                    else
                        grade = 5;
                    //Make a new card based on the original, add the grade, take one card from the original stack, and give the newly graded card
                    ItemStack card = new ItemStack(playerIn.getHeldItem(handIn).getItem(), 1);
                    nbt.putInt("grade", grade);
                    card.setTag(nbt);
                    playerIn.getHeldItem(handIn).shrink(1);
                    ItemHandlerHelper.giveItemToPlayer(playerIn, card);
                }
            }
        }
        return super.onItemRightClick(worldIn, playerIn, handIn);
    }
}
