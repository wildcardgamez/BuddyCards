package com.wildcard.buddycards.items.buddysteel;

import com.wildcard.buddycards.BuddyCards;
import com.wildcard.buddycards.registries.BuddycardsItems;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.util.LazyLoadedValue;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;

public enum BuddysteelArmorMaterial implements ArmorMaterial {
    BUDDYSTEEL(8, 40, new int[]{1, 4, 5, 2}, new LazyLoadedValue<>(() -> Ingredient.of(BuddycardsItems.BUDDYSTEEL_INGOT.get())), "buddysteel"),
    PERFECT_BUDDYSTEEL(10, 45, new int[]{2, 5, 6, 2}, new LazyLoadedValue<>(() -> Ingredient.of(BuddycardsItems.PERFECT_BUDDYSTEEL_INGOT.get())), "perfect_buddysteel"),
    ZYLEX(12, 50, new int[]{2, 5, 6, 2}, new LazyLoadedValue<>(() -> Ingredient.of(BuddycardsItems.ZYLEX.get())), "zylex"),
    LUMINIS(15, 50, new int[]{2, 5, 6, 2}, new LazyLoadedValue<>(() -> Ingredient.of(BuddycardsItems.LUMINIS.get())), "luminis");

    BuddysteelArmorMaterial(int enchVal, int dura, int[] red, LazyLoadedValue<Ingredient> mat, String nameIn) {
        ench = enchVal;
        duraMult = dura;
        material = mat;
        name = nameIn;
        dam_red = red;
    }
    int ench;
    int duraMult;
    LazyLoadedValue<Ingredient> material;
    String name;
    int[] dam_red;

    private static final int[] HEALTH_PER_SLOT = new int[]{13, 15, 16, 11};

    @Override
    public int getDurabilityForSlot(EquipmentSlot slotIn) {
        return HEALTH_PER_SLOT[slotIn.getIndex()] * duraMult;
    }

    @Override
    public int getDefenseForSlot(EquipmentSlot slotIn) {
        return dam_red[slotIn.getIndex()];
    }

    @Override
    public int getEnchantmentValue() {
        return ench;
    }

    @Override
    public SoundEvent getEquipSound() {
        return SoundEvents.ARMOR_EQUIP_DIAMOND;
    }

    @Override
    public Ingredient getRepairIngredient() {
        return material.get();
    }

    @Override
    public String getName() {
        return BuddyCards.MOD_ID + ":" + name;
    }

    @Override
    public float getToughness() {
        return 1;
    }

    @Override
    public float getKnockbackResistance() {
        return 0;
    }

}

