package com.wildcard.buddycards.integration;

import com.simibubi.create.content.contraptions.itemAssembly.SequencedAssemblyItem;
import com.wildcard.buddycards.BuddyCards;
import com.wildcard.buddycards.registries.BuddycardsItems;
import net.minecraft.item.Item;
import net.minecraftforge.fml.RegistryObject;

public class CreateIntegration {
    public static void init() {
        RECYCLED_BUDDYCARD = BuddycardsItems.ITEMS.register("recycled_buddycard", () -> new Item(new Item.Properties().tab(BuddyCards.TAB)));
        UNFINISHED_PACK = BuddycardsItems.ITEMS.register("unfinished_pack", () -> new SequencedAssemblyItem(new Item.Properties().tab(BuddyCards.TAB)));
    }

    public static RegistryObject<Item> RECYCLED_BUDDYCARD;
    public static RegistryObject<Item> UNFINISHED_PACK;
}
