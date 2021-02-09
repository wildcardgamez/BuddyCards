package com.wildcard.buddycards.items;

import com.wildcard.buddycards.BuddyCards;
import com.wildcard.buddycards.util.ConfigManager;
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
    /**
     * Sets up a card number for a set with 27 cards
     * @param setNumber set number for card
     * @param cardNumber card number for card
     * @param isShiny is it a shiny card
     */
    public CardItem(int setNumber, int cardNumber, boolean isShiny) {
        super(new Item.Properties().group(BuddyCards.TAB));
        SET_NUMBER = setNumber;
        CARD_NUMBER = cardNumber;
        SHINY = isShiny;
        if(CARD_NUMBER <= 12)
            rarity = Rarity.COMMON;
        else if(CARD_NUMBER <= 21)
            rarity = Rarity.UNCOMMON;
        else if(CARD_NUMBER <= 25)
            rarity = Rarity.RARE;
        else
            rarity = Rarity.EPIC;
    }

    /**
     * Sets up a card number for a set with less or more than 27 cards
     * @param setNumber set number for card
     * @param cardNumber card number for card
     * @param isShiny is it a shiny card
     * @param raritySeperators the final common uncommon and rare card numbers to setup the rarities based on card number
     */
    public CardItem(int setNumber, int cardNumber, boolean isShiny, int[] raritySeperators) {
        super(new Item.Properties().group(BuddyCards.TAB));
        SET_NUMBER = setNumber;
        CARD_NUMBER = cardNumber;
        SHINY = isShiny;
        if(CARD_NUMBER <= raritySeperators[0])
            rarity = Rarity.COMMON;
        else if(CARD_NUMBER <= raritySeperators[1])
            rarity = Rarity.UNCOMMON;
        else if(CARD_NUMBER <= raritySeperators[2])
            rarity = Rarity.RARE;
        else
            rarity = Rarity.EPIC;
    }
    public final int SET_NUMBER;
    public final int CARD_NUMBER;
    public final boolean SHINY;
    private final Rarity rarity;

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
        if (ConfigManager.challengeMode.get())
            tooltip.add(new TranslationTextComponent("item.buddycards.points_info").appendString(
                    "" + ((CardItem)stack.getItem()).getPointValue(stack)));
    }

    @Override
    public boolean hasEffect(ItemStack stack) {
        //Make shiny cards have enchant glow, and non-shiny ones not
        return SHINY;
    }

    @Override
    public Rarity getRarity(ItemStack stack) {
        return rarity;
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
        if (handIn == Hand.MAIN_HAND) {
            if (playerIn.getHeldItem(Hand.OFF_HAND).getItem() == RegistryHandler.GRADING_SLEEVE.get()) {
                CompoundNBT nbt = playerIn.getHeldItem(handIn).getTag();
                if (nbt == null)
                    nbt = new CompoundNBT();
                if (nbt.getInt("grade") == 0) {
                    //Take the grading sleeve
                    playerIn.getHeldItem(Hand.OFF_HAND).shrink(1);
                    //Get a grade using maths for rarity
                    int i = (int) (Math.random() * 500) + 1;
                    int grade;
                    if (i < 200)
                        grade = 1;
                    else if (i < 360)
                        grade = 2;
                    else if (i < 450)
                        grade = 3;
                    else if (i < 500)
                        grade = 4;
                    else
                        grade = 5;
                    //Make new graded card and give it to the player, remove old card
                    nbt.putInt("grade", grade);
                    ItemStack card = new ItemStack(playerIn.getHeldItem(handIn).getItem(), 1);
                    card.setTag(nbt);
                    playerIn.getHeldItem(handIn).shrink(1);
                    ItemHandlerHelper.giveItemToPlayer(playerIn, card);

                    return ActionResult.resultSuccess(playerIn.getHeldItem(handIn));
                }
            }
        }
        return super.onItemRightClick(worldIn, playerIn, handIn);
    }

    public int getPointValue(ItemStack card) {
        double points = 0;
        if(CARD_NUMBER <= 12)
            points = ConfigManager.challengePointsCommon.get();
        else if(CARD_NUMBER <= 21)
            points = ConfigManager.challengePointsUncommon.get();
        else if(CARD_NUMBER <= 25)
            points = ConfigManager.challengePointsRare.get();
        else
            points = ConfigManager.challengePointsEpic.get();
        if(SET_NUMBER == 1)
            points *= ConfigManager.challengeSet1Mult.get();
        else if(SET_NUMBER == 2)
            points *= ConfigManager.challengeSet2Mult.get();
        else if(SET_NUMBER == 3)
            points *= ConfigManager.challengeSet3Mult.get();
        else if(SET_NUMBER == 4)
            points *= ConfigManager.challengeSet4Mult.get();
        else if(SET_NUMBER == 5)
            points *= ConfigManager.challengeSet5Mult.get();
        if(card.getTag() != null) {
            int grade = card.getTag().getInt("grade");
            if(grade == 1)
                points *= ConfigManager.challengeGrade1Mult.get();
            else if(grade == 2)
                points *= ConfigManager.challengeGrade2Mult.get();
            else if(grade == 3)
                points *= ConfigManager.challengeGrade3Mult.get();
            else if(grade == 4)
                points *= ConfigManager.challengeGrade4Mult.get();
            else if(grade == 5)
                points *= ConfigManager.challengeGrade5Mult.get();
        }
        if(SHINY)
            points *= ConfigManager.challengeShinyMult.get();
        return (int) (points + .5);
    }
}
