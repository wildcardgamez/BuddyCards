package com.wildcard.buddycards.items.buddysteel;

import com.wildcard.buddycards.BuddyCards;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.IArmorMaterial;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.LazyValue;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;

public enum BuddysteelArmorMaterial implements IArmorMaterial {
    BUDDYSTEEL(8, 40, new int[]{1, 4, 5, 2}, new LazyValue<>(() -> Ingredient.of(RegistryHandler.BUDDYSTEEL_INGOT.get())), "buddysteel"),
    PERFECT_BUDDYSTEEL(10, 45, new int[]{2, 5, 6, 2}, new LazyValue<>(() -> Ingredient.of(RegistryHandler.PERFECT_BUDDYSTEEL_INGOT.get())), "perfect_buddysteel"),
    ZYLEX(12, 43, new int[]{2, 5, 6, 2}, new LazyValue<>(() -> Ingredient.of(RegistryHandler.ZYLEX_TOKEN.get())), "zylex");

    BuddysteelArmorMaterial(int enchVal, int dura, int[] red, LazyValue<Ingredient> mat, String nameIn) {
        ench = enchVal;
        duraMult = dura;
        material = mat;
        name = nameIn;
        dam_red = red;
    }
    int ench;
    int duraMult;
    LazyValue<Ingredient> material;
    String name;
    int[] dam_red;

    private static final int[] HEALTH_PER_SLOT = new int[]{13, 15, 16, 11};

    @Override
    public int getDurabilityForSlot(EquipmentSlotType slotIn) {
        return HEALTH_PER_SLOT[slotIn.getIndex()] * duraMult;
    }

    @Override
    public int getDefenseForSlot(EquipmentSlotType slotIn) {
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

