package com.wildcard.buddycards.items.buddysteel;

import com.wildcard.buddycards.util.RegistryHandler;
import net.minecraft.item.IItemTier;
import net.minecraft.item.crafting.Ingredient;

public enum BuddysteelItemTier implements IItemTier {
    BUDDYSTEEL();

    @Override
    public int getUses() {
        return 2048;
    }

    @Override
    public float getSpeed() {
        return 6;
    }

    @Override
    public float getAttackDamageBonus() {
        return 0;
    }

    @Override
    public int getLevel() {
        return 2;
    }

    @Override
    public int getEnchantmentValue() {
        return 10;
    }

    @Override
    public Ingredient getRepairIngredient() {
        return Ingredient.of(RegistryHandler.BUDDYSTEEL_INGOT.get());
    }
}
