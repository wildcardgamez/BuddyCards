package com.wildcard.buddycards.enchantment;

import com.wildcard.buddycards.util.EnchantmentHandler;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.enchantment.Enchantment;

public class EnchantmentBuddyBoost extends Enchantment {
    public EnchantmentBuddyBoost() {
        super(Rarity.VERY_RARE, EnchantmentHandler.BUDDY_MEDAL, EquipmentSlot.values());
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
