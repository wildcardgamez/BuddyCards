package com.wildcard.buddycards.util;

import com.wildcard.buddycards.items.CardItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.MerchantOffer;
import net.minecraft.nbt.CompoundNBT;

public class EnderlingOfferMaker {
    public static MerchantOffer createCardBuyOffer() {
        ItemStack card = new ItemStack(CardRegistry.LOADED_CARDS.get((int)(Math.random() * (CardRegistry.LOADED_CARDS.size()))).get());
        ItemStack zylex = new ItemStack(RegistryHandler.ZYLEX.get(), ((CardItem)card.getItem()).getPointValue(card)/3+1);
        return new MerchantOffer(card, zylex, 1, 2, 1);
    }

    public static MerchantOffer createBulkCardBuyOffer() {
        ItemStack card = new ItemStack(CardRegistry.LOADED_CARDS.get((int)(Math.random() * (CardRegistry.LOADED_CARDS.size()))).get(), 8);
        ItemStack zylex = new ItemStack(RegistryHandler.ZYLEX.get(), ((CardItem)card.getItem()).getPointValue(card) * 3);
        return new MerchantOffer(card, zylex, 1, 6, 1);
    }

    public static MerchantOffer createGradedCardBuyOffer() {
        ItemStack card = new ItemStack(CardRegistry.LOADED_CARDS.get((int)(Math.random() * (CardRegistry.LOADED_CARDS.size()))).get());
        CompoundNBT nbt = new CompoundNBT();
        int i = (int) (Math.random() * 200) + 1;
        int grade;
        if (i < 20)
            grade = 1;
        else if (i < 80)
            grade = 2;
        else if (i < 140)
            grade = 3;
        else
            grade = 4;
        nbt.putInt("grade", grade);
        card.setTag(nbt);
        ItemStack zylex = new ItemStack(RegistryHandler.ZYLEX.get(), ((CardItem)card.getItem()).getPointValue(card)/3+2);
        return new MerchantOffer(card, zylex, 3, grade * 2, 1);
    }

    public static MerchantOffer createGradedCardSellOffer() {
        ItemStack card = new ItemStack(CardRegistry.LOADED_CARDS.get((int)(Math.random() * (CardRegistry.LOADED_CARDS.size()))).get());
        CompoundNBT nbt = new CompoundNBT();
        int i = (int) (Math.random() * 200) + 1;
        int grade;
        if (i < 50)
            grade = 1;
        else if (i < 105)
            grade = 2;
        else if (i < 160)
            grade = 3;
        else if (i < 200)
            grade = 4;
        else
            grade = 5;
        nbt.putInt("grade", grade);
        card.setTag(nbt);
        ItemStack zylex = new ItemStack(RegistryHandler.ZYLEX.get(), ((CardItem)card.getItem()).getPointValue(card)/2+2);
        return new MerchantOffer(zylex, card, 1, grade, 1);
    }

    public static MerchantOffer createCardTradeOffer() {
        ItemStack card = new ItemStack(CardRegistry.LOADED_CARDS.get((int)(Math.random() * (CardRegistry.LOADED_CARDS.size()))).get());
        ItemStack card2 = new ItemStack(CardRegistry.LOADED_CARDS.get((int)(Math.random() * (CardRegistry.LOADED_CARDS.size()))).get());
        CompoundNBT nbt = new CompoundNBT();
        int i = (int) (Math.random() * 200) + 1;
        int grade;
        if (i < 100)
            grade = 0;
        else if (i < 125)
            grade = 1;
        else if (i < 150)
            grade = 2;
        else if (i < 175)
            grade = 3;
        else if (i < 200)
            grade = 4;
        else
            grade = 5;
        nbt.putInt("grade", grade);
        card2.setTag(nbt);
        return new MerchantOffer(card, card2, 1, grade * 2, 1);
    }

    public static MerchantOffer createPackOffer() {
        if(Math.random() > .5)
            return new MerchantOffer(new ItemStack(RegistryHandler.ZYLEX.get(), 6 + (int) (Math.random()*3)), new ItemStack(RegistryHandler.PACK_END.get()), 1, 5, 1);
        return new MerchantOffer(new ItemStack(RegistryHandler.ZYLEX.get(), 13 + (int) (Math.random()*4)), new ItemStack(RegistryHandler.PACK_MYSTERY.get()), 1, 8, 1);
    }

    public static MerchantOffer createGenericOffer() {
        int amount = (int) (Math.random() * 12) + 1;
        double random = Math.random();
        if(random > .92)
            return new MerchantOffer(new ItemStack(RegistryHandler.ZYLEX.get(), amount + 1 + (int) (Math.random()/2*amount)), new ItemStack(RegistryHandler.CARD_STAND.get(), amount/3 + 1), 3, amount, 1);
        if(random > .86)
            return new MerchantOffer(new ItemStack(RegistryHandler.ZYLEX.get(), amount + (int) (Math.random()/2*amount)), new ItemStack(RegistryHandler.GRADING_SLEEVE.get(), amount/3 + 1), 3, amount, 1);
        if(random > .8)
            return new MerchantOffer(new ItemStack(RegistryHandler.ZYLEX.get(), amount + (int) (Math.random()*2*amount)), new ItemStack(RegistryHandler.BUDDYSTEEL_NUGGET.get(), amount), 3, amount*2, 1);
        if(random > .75)
            return new MerchantOffer(new ItemStack(RegistryHandler.ZYLEX.get(), 6 + (int) (Math.random()*4)), new ItemStack(RegistryHandler.ENDER_BINDER.get()), 1, 6, 1);
        if(random > .3)
            return createBulkCardBuyOffer();
        else
            return createCardTradeOffer();
    }
}
