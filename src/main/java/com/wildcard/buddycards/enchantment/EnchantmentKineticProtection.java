package com.wildcard.buddycards.enchantment;

import com.wildcard.buddycards.util.BuddyEnchantmentHandler;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.ProtectionEnchantment;

public class EnchantmentKineticProtection extends Enchantment {
    public EnchantmentKineticProtection() {
        super(Rarity.UNCOMMON, BuddyEnchantmentHandler.YANNEL_ARMOR, EquipmentSlot.values());
    }

    public int getMinCost(int i) {
        return i * 5;
    }

    public int getMaxCost(int i) {
        return 8 + i * 4;
    }

    public int getMaxLevel() {
        return 4;
    }

    public int getDamageProtection(int i, DamageSource source) {
        if (!source.isBypassInvul() && source.equals(DamageSource.FLY_INTO_WALL))
            return i;
        return 0;
    }

    public boolean checkCompatibility(Enchantment enchant) {
        if (enchant instanceof ProtectionEnchantment) {
            return false;
        } else {
            return super.checkCompatibility(enchant);
        }
    }
}
