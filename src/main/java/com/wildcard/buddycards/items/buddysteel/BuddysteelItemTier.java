package com.wildcard.buddycards.items.buddysteel;

import com.wildcard.buddycards.util.RegistryHandler;
import net.minecraft.item.IItemTier;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.LazyValue;

public enum BuddysteelItemTier implements IItemTier {
    BUDDYSTEEL(2048, 6, 0, 2, 8, new LazyValue<>(() -> Ingredient.of(RegistryHandler.BUDDYSTEEL_INGOT.get()))),
    PERFECT_BUDDYSTEEL(3072, 7.5f, 0, 3, 10, new LazyValue<>(() -> Ingredient.of(RegistryHandler.PERFECT_BUDDYSTEEL_INGOT.get()))),
    LUMINIS(1024, 8f, 0, 3, 12, new LazyValue<>(() -> Ingredient.of(RegistryHandler.LUMINIS.get())));

    BuddysteelItemTier(int uses, float speed, float dmg, int level, int ench, LazyValue<Ingredient> mat) {
        this.uses = uses;
        this.speed = speed;
        this.dmg = dmg;
        this.level = level;
        this.ench = ench;
        this.mat = mat;
    }
    int uses;
    float speed;
    float dmg;
    int level;
    int ench;
    LazyValue<Ingredient> mat;

    @Override
    public int getUses() {
        return uses;
    }

    @Override
    public float getSpeed() {
        return speed;
    }

    @Override
    public float getAttackDamageBonus() {
        return dmg;
    }

    @Override
    public int getLevel() {
        return level;
    }

    @Override
    public int getEnchantmentValue() {
        return ench;
    }

    @Override
    public Ingredient getRepairIngredient() {
        return mat.get();
    }
}
