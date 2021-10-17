package com.wildcard.buddycards.items;

import com.wildcard.buddycards.BuddyCards;
import com.wildcard.buddycards.registries.BuddycardsItems;
import net.minecraft.world.item.*;

public class YannelElytraItem extends ElytraItem implements Wearable {
    public YannelElytraItem() {
        super(new Item.Properties().durability(612).tab(BuddyCards.TAB).rarity(Rarity.UNCOMMON));
    }

    public boolean isValidRepairItem(ItemStack elytra, ItemStack stack) {
        return stack.is(BuddycardsItems.YANEL_FABRIC.get());
    }
}
