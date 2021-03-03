package com.wildcard.buddycards.util;

import com.wildcard.buddycards.items.CardItem;
import net.minecraft.item.Item;
import net.minecraftforge.fml.RegistryObject;

import java.util.ArrayList;

public class CardRegistry {
    public final static ArrayList<RegistryObject<Item>> CARDS = new ArrayList<RegistryObject<Item>>();
    //Cards
    public CardRegistry() {
        //Create all base set cards
        for(int i = 1; i <= 27; i++) {
            final int num = i;
            CARDS.add(RegistryHandler.ITEMS.register("card.1." + num, () -> new CardItem(1, num, false)));
            CARDS.add(RegistryHandler.ITEMS.register("card.1." + num + "s", () -> new CardItem(1, num, true)));
        }
        //Create all nether set cards
        for(int i = 1; i <= 27; i++) {
            final int num = i;
            CARDS.add(RegistryHandler.ITEMS.register("card.2." + num, () -> new CardItem(2, num, false)));
            CARDS.add(RegistryHandler.ITEMS.register("card.2." + num + "s", () -> new CardItem(2, num, true)));
        }
        //Create all end set cards
        for(int i = 1; i <= 27; i++) {
            final int num = i;
            CARDS.add(RegistryHandler.ITEMS.register("card.3." + num, () -> new CardItem(3, num, false)));
            CARDS.add(RegistryHandler.ITEMS.register("card.3." + num + "s", () -> new CardItem(3, num, true)));
        }
        //Create all byg set cards
        for(int i = 1; i <= 27; i++) {
            final int num = i;
            CARDS.add(RegistryHandler.ITEMS.register("card.4." + num, () -> new CardItem(4, num, false)));
            CARDS.add(RegistryHandler.ITEMS.register("card.4." + num + "s", () -> new CardItem(4, num, true)));
        }
        //Create all create set cards
        for(int i = 1; i <= 27; i++) {
            final int num = i;
            CARDS.add(RegistryHandler.ITEMS.register("card.5." + num, () -> new CardItem(5, num, false)));
            CARDS.add(RegistryHandler.ITEMS.register("card.5." + num + "s", () -> new CardItem(5, num, true)));
        }
        //Create all aquaculture set cards
        for(int i = 1; i <= 18; i++) {
            final int num = i;
            CARDS.add(RegistryHandler.ITEMS.register("card.6." + num, () -> new CardItem(6, num, false, new int[]{7, 13, 16})));
            CARDS.add(RegistryHandler.ITEMS.register("card.6." + num + "s", () -> new CardItem(6, num, true, new int[]{7, 13, 16})));
        }
        //Create all fd set cards
        for(int i = 1; i <= 18; i++) {
            final int num = i;
            CARDS.add(RegistryHandler.ITEMS.register("card.7." + num, () -> new CardItem(7, num, false, new int[]{7, 13, 16})));
            CARDS.add(RegistryHandler.ITEMS.register("card.7." + num + "s", () -> new CardItem(7, num, true, new int[]{7, 13, 16})));
        }
    }
}
