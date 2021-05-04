package com.wildcard.buddycards.enchantment;

import com.wildcard.buddycards.util.EnchantmentHandler;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;

import net.minecraft.enchantment.Enchantment.Rarity;

public class EnchantmentBuddyBinding extends Enchantment {
    public EnchantmentBuddyBinding() {
        super(Rarity.UNCOMMON, EnchantmentHandler.BUDDY_BINDABLE, EquipmentSlotType.values());
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
