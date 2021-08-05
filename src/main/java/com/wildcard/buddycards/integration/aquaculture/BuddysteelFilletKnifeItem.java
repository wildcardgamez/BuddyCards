package com.wildcard.buddycards.integration.aquaculture;

import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.Multimap;
import com.wildcard.buddycards.items.buddysteel.BuddysteelItemTier;
import com.wildcard.buddycards.items.buddysteel.BuddysteelSwordItem;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;

import javax.annotation.Nonnull;

public class BuddysteelFilletKnifeItem extends BuddysteelSwordItem {
    public BuddysteelFilletKnifeItem() {
        super(BuddysteelItemTier.BUDDYSTEEL, 3);
    }

    public boolean canApplyAtEnchantingTable(@Nonnull ItemStack stack, Enchantment enchantment) {
        return super.canApplyAtEnchantingTable(stack, enchantment) && this.canApplyEnchantment(enchantment);
    }

    private boolean canApplyEnchantment(Enchantment enchantment) {
        return enchantment != Enchantments.MOB_LOOTING && enchantment != Enchantments.SWEEPING_EDGE;
    }

    @Override
    public float getDamage() {
        return BuddysteelItemTier.BUDDYSTEEL.getAttackDamageBonus() / 2;
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlot slot, ItemStack stack) {
        Multimap<Attribute, AttributeModifier> multimap = super.getAttributeModifiers(slot, stack);
        if (stack.hasTag() && slot == EquipmentSlot.MAINHAND) {
            multimap = LinkedHashMultimap.create();
            float ratio = stack.getTag().getFloat("completion");
            multimap.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_UUID, "Weapon modifier", (double)this.getDamage() + 1 + (ratio * 1.5), AttributeModifier.Operation.ADDITION));
            multimap.put(Attributes.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED_UUID, "Weapon modifier", -2.2, AttributeModifier.Operation.ADDITION));
        }
        return multimap;
    }

    @Override
    public void fillItemCategory(CreativeModeTab group, NonNullList<ItemStack> items) {
        //Add an alternative with max power for creative menu
        ItemStack maxed = new ItemStack(this);
        CompoundTag nbt = new CompoundTag();
        nbt.putFloat("completion", 1);
        maxed.setTag(nbt);
        items.add(maxed);
        super.fillItemCategory(group, items);
    }
}
