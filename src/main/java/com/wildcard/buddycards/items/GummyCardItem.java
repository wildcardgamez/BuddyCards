package com.wildcard.buddycards.items;

import com.wildcard.buddycards.BuddyCards;
import com.wildcard.buddycards.util.ConfigManager;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Food;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Rarity;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

import java.util.List;

public class GummyCardItem extends CardItem {
    public GummyCardItem(int cardNumber) {
        super(7, cardNumber, false, new Item.Properties().tab(BuddyCards.CARDS_TAB).food(new Food.Builder().nutrition(2).saturationMod(0.3F).fast().build()), Rarity.COMMON);
    }

    @Override
    public Rarity getRarity(ItemStack stack) {
        return Rarity.COMMON;
    }


    @Override
    public void appendHoverText(ItemStack stack, World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        //Add the card description
        tooltip.add(new TranslationTextComponent("item.buddycards.card.7.gummy"+ CARD_NUMBER +".tooltip"));
        TranslationTextComponent cn = new TranslationTextComponent("item.buddycards.number_separator");
        cn.append("Gummy " + CARD_NUMBER);
        //Add the card info (SetName - Gummy Card#)
        tooltip.add(new TranslationTextComponent("item.buddycards.set." + SET_NUMBER).append(cn));
        if (ConfigManager.challengeMode.get())
            tooltip.add(new TranslationTextComponent("item.buddycards.points_info").append(
                    "" + ((CardItem)stack.getItem()).getPointValue(stack)));
    }
}
