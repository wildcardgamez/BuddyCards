package com.wildcard.buddycards.items.buddysteel;

import com.wildcard.buddycards.util.RegistryHandler;
import net.minecraft.item.IItemTier;
import net.minecraft.item.crafting.Ingredient;

public enum BuddysteelItemTier implements IItemTier {
    BUDDYSTEEL();

    @Override
    public int getMaxUses() {
        return 2048;
    }

    @Override
    public float getEfficiency() {
        return 6;
    }

    @Override
    public float getAttackDamage() {
        return 0;
    }

    @Override
    public int getHarvestLevel() {
        return 2;
    }

    @Override
    public int getEnchantability() {
        return 10;
    }

    @Override
    public Ingredient getRepairMaterial() {
        return Ingredient.fromItems(RegistryHandler.BUDDYSTEEL_INGOT.get());
    }
}
