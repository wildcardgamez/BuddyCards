package com.wildcard.buddycards.enchantment;

import com.wildcard.buddycards.util.BuddyEnchantmentHandler;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.enchantment.Enchantment;

public class EnchantmentExtraPage extends Enchantment {
    public EnchantmentExtraPage() {
        super(Rarity.RARE, BuddyEnchantmentHandler.BUDDY_BINDER, EquipmentSlot.values());
    }

    public int getMinCost(int par1) {
        return 10 * par1;
    }

    public int getMaxCost(int par1) {
        return 25 + (par1 * 10);
    }

    @Override
    public int getMaxLevel() {
        return 3;
    }
}
