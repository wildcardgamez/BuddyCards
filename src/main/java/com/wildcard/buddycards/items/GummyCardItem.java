package com.wildcard.buddycards.items;

import com.wildcard.buddycards.BuddyCards;
import com.wildcard.buddycards.util.ConfigManager;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.level.Level;

import java.util.List;

public class GummyCardItem extends CardItem {
    public GummyCardItem(int cardNumber) {
        super(7, cardNumber, Rarity.COMMON, "buddycards", new Item.Properties().tab(BuddyCards.CARDS_TAB).food(new FoodProperties.Builder().nutrition(2).saturationMod(0.3F).fast().build()));
    }

    @Override
    public Rarity getRarity(ItemStack stack) {
        return Rarity.COMMON;
    }


    @Override
    public void appendHoverText(ItemStack stack, Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
        //Add the card description
        tooltip.add(new TranslatableComponent("item.buddycards.card.7.gummy"+ CARD_NUMBER +".tooltip"));
        TranslatableComponent cn = new TranslatableComponent("item.buddycards.number_separator");
        cn.append("Gummy " + CARD_NUMBER);
        //Add the card info (SetName - Gummy Card#)
        tooltip.add(new TranslatableComponent("item.buddycards.set." + SET_NUMBER).append(cn));
        if (ConfigManager.challengeMode.get())
            tooltip.add(new TranslatableComponent("item.buddycards.points_info").append(
                    "" + ((CardItem)stack.getItem()).getPointValue(stack)));
    }
}
