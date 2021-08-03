package com.wildcard.buddycards.integration.fd;

import com.wildcard.buddycards.registries.BuddycardsItems;
import net.minecraft.world.item.Item;
import net.minecraftforge.fmllegacy.RegistryObject;
public class FarmersDelightIntegration {
    public static void init() {
        //BUDDYSTEEL_FOOD_KNIFE = BuddycardsItems.ITEMS.register("buddysteel_food_knife", BuddysteelFoodKnifeItem::new);
    }

    public static RegistryObject<Item> BUDDYSTEEL_FOOD_KNIFE;
}
