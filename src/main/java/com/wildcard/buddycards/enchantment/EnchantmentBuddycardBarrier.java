package com.wildcard.buddycards.enchantment;

import com.wildcard.buddycards.util.BuddyEnchantmentHandler;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;

public class EnchantmentBuddycardBarrier extends Enchantment {
    public EnchantmentBuddycardBarrier() {
        super(Rarity.VERY_RARE, BuddyEnchantmentHandler.BUDDY_ARMOR, EquipmentSlot.values());
    }

    @Override
    public int getMaxCost(int i) {
        return 0;
    }

    @Override
    public int getMinCost(int i) {
        return 0;
    }

    @Override
    public int getMaxLevel() {
        return 0;
    }

    @Override
    public boolean canEnchant(ItemStack stack) {
        return false;
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack) {
        return false;
    }

    @Override
    public boolean isTradeable() {
        return false;
    }

    @Override
    public boolean isAllowedOnBooks() {
        return false;
    }

    @Override
    public int getDamageProtection(int i, DamageSource source) {
        return i;
    }
}
