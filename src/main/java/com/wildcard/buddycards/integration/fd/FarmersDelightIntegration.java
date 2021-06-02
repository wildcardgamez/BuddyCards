package com.wildcard.buddycards.integration.fd;

import net.minecraft.item.Item;
import net.minecraftforge.fml.RegistryObject;

public class FarmersDelightIntegration {
    public static void init() {
        BUDDYSTEEL_FOOD_KNIFE = RegistryHandler.ITEMS.register("buddysteel_food_knife", BuddysteelFoodKnifeItem::new);
    }

    public static RegistryObject<Item> BUDDYSTEEL_FOOD_KNIFE;
}
