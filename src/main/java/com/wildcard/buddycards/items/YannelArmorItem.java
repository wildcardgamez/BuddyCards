package com.wildcard.buddycards.items;

import com.wildcard.buddycards.BuddyCards;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Item;

public class YannelArmorItem extends ArmorItem {
    public YannelArmorItem(EquipmentSlot slot) {
        super(BuddycardsArmorMaterial.YANNEL, slot, new Item.Properties().tab(BuddyCards.TAB));
    }
}
