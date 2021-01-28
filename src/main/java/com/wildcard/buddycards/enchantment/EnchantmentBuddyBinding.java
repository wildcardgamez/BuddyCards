package com.wildcard.buddycards.enchantment;

import com.wildcard.buddycards.util.EnchantmentHandler;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;

public class EnchantmentBuddyBinding extends Enchantment {
    public EnchantmentBuddyBinding() {
        super(Rarity.UNCOMMON, EnchantmentHandler.BUDDY_BINDABLE, EquipmentSlotType.values());
    }

    public int getMinEnchantability(int par1) {
        return par1 * 10;
    }

    public int getMaxEnchantability(int par1) {
        return par1 * 20;
    }

    @Override
    public int getMaxLevel() {
        return 3;
    }
}
