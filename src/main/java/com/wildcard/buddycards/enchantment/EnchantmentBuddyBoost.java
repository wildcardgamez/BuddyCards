package com.wildcard.buddycards.enchantment;

import com.wildcard.buddycards.util.EnchantmentHandler;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.inventory.EquipmentSlotType;

public class EnchantmentBuddyBoost extends Enchantment {
    public EnchantmentBuddyBoost() {
        super(Rarity.VERY_RARE, EnchantmentHandler.BUDDY_MEDAL, EquipmentSlotType.values());
    }

    public int getMinCost(int par1) {
        return 15 * par1;
    }

    public int getMaxCost(int par1) {
        return 20 + (par1 * 10);
    }

    @Override
    public int getMaxLevel() {
        return 2;
    }
}
