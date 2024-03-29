package com.wildcard.buddycards.items;

import com.wildcard.buddycards.registries.BuddycardsItems;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.util.LazyLoadedValue;

public enum BuddycardsItemTier implements Tier {
    BUDDYSTEEL(2048, 6f, 0, 2, 8, new LazyLoadedValue<>(() -> Ingredient.of(BuddycardsItems.BUDDYSTEEL_INGOT.get()))),
    PERFECT_BUDDYSTEEL(3072, 7.5f, 0, 4, 10, new LazyLoadedValue<>(() -> Ingredient.of(BuddycardsItems.PERFECT_BUDDYSTEEL_INGOT.get()))),
    ZYLEX(3072, 8f, 0, 3, 15, new LazyLoadedValue<>(() -> Ingredient.of(BuddycardsItems.ZYLEX.get()))),
    LUMINIS(1536, 8f, 0, 3, 12, new LazyLoadedValue<>(() -> Ingredient.of(BuddycardsItems.LUMINIS.get())));

    BuddycardsItemTier(int uses, float speed, float dmg, int level, int ench, LazyLoadedValue<Ingredient> mat) {
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
