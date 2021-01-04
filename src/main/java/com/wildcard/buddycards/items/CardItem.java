package com.wildcard.buddycards.items;

import com.wildcard.buddycards.BuddyCards;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Rarity;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

import java.util.List;

public class CardItem extends Item {
    public CardItem(int setNumber, int cardNumber, boolean isShiny) {
        super(new Item.Properties().group(BuddyCards.TAB));
        SET_NUMBER = setNumber;
        CARD_NUMBER = cardNumber;
        SHINY = isShiny;
    }

    final int SET_NUMBER;
    final int CARD_NUMBER;
    final boolean SHINY;

    @Override
    public void addInformation(ItemStack stack, World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        //Add the card description
        tooltip.add(new TranslationTextComponent("item.buddycards.card." + SET_NUMBER + "." + CARD_NUMBER + ".tooltip"));
        TranslationTextComponent cn = new TranslationTextComponent(" - " + CARD_NUMBER);
        //Add the star to the prefix when it's a shiny variant
        if (SHINY)
            cn.append(new TranslationTextComponent("item.buddycards.shiny_symbol"));
        //Add the card info (SetName - Card# Shiny symbol)
        tooltip.add(new TranslationTextComponent("item.buddycards.set." + SET_NUMBER).append(cn));
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

}
