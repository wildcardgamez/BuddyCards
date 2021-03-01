package com.wildcard.buddycards.items.buddysteel;

import com.wildcard.buddycards.BuddyCards;
import com.wildcard.buddycards.util.RegistryHandler;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.IArmorMaterial;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;

public enum BuddysteelArmorMaterial implements IArmorMaterial {
    BUDDYSTEEL;

    private final int[] MAX_DAMAGE_ARRAY = new int[] {11, 16, 15, 13};
    private final int[] DAMAGE_REDUCTION_ARRAY = new int[] {1, 4, 5, 2};

    @Override
    public int getDurability(EquipmentSlotType slotIn) {
        return MAX_DAMAGE_ARRAY[slotIn.getIndex()] * 40;
    }

    @Override
    public int getDamageReductionAmount(EquipmentSlotType slotIn) {
        return DAMAGE_REDUCTION_ARRAY[slotIn.getIndex()];
    }

    @Override
    public int getEnchantability() {
        return 10;
    }

    @Override
    public SoundEvent getSoundEvent() {
        return SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND;
    }

    @Override
    public Ingredient getRepairMaterial() {
        return Ingredient.fromItems(RegistryHandler.BUDDYSTEEL_INGOT.get());
    }

    @Override
    public String getName() {
        return BuddyCards.MOD_ID + ":buddysteel";
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

