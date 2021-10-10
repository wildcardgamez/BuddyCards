package com.wildcard.buddycards.enchantment;

import com.wildcard.buddycards.util.BuddyEnchantmentHandler;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.enchantment.Enchantment;

public class EnchantmentBuddyBinding extends Enchantment {
    public EnchantmentBuddyBinding() {
        super(Rarity.UNCOMMON, BuddyEnchantmentHandler.BUDDY_BINDABLE, EquipmentSlot.values());
    }

    public int getMinCost(int par1) {
        return par1 * 15 - 15;
    }

    public int getMaxCost(int par1) {
        return par1 * 20;
    }

    @Override
    public int getMaxLevel() {
        return 3;
    }
}
