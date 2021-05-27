package com.wildcard.buddycards.enchantment;

import com.wildcard.buddycards.util.EnchantmentHandler;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.inventory.EquipmentSlotType;

public class EnchantmentExtraPage extends Enchantment {
    public EnchantmentExtraPage() {
        super(Rarity.RARE, EnchantmentHandler.BUDDY_BINDER, EquipmentSlotType.values());
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
