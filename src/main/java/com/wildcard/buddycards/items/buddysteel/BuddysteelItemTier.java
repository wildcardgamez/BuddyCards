package com.wildcard.buddycards.items.buddysteel;

import com.wildcard.buddycards.registries.BuddycardsItems;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.util.LazyLoadedValue;

public enum BuddysteelItemTier implements Tier {
    BUDDYSTEEL(2048, 6f, 0, 2, 8, new LazyLoadedValue<>(() -> Ingredient.of(BuddycardsItems.BUDDYSTEEL_INGOT.get()))),
    PERFECT_BUDDYSTEEL(3072, 7.5f, 0, 3, 10, new LazyLoadedValue<>(() -> Ingredient.of(BuddycardsItems.PERFECT_BUDDYSTEEL_INGOT.get()))),
    LUMINIS(1024, 8f, 0, 3, 12, new LazyLoadedValue<>(() -> Ingredient.of(BuddycardsItems.LUMINIS.get())));

    BuddysteelItemTier(int uses, float speed, float dmg, int level, int ench, LazyLoadedValue<Ingredient> mat) {
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
    LazyLoadedValue<Ingredient> mat;

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
