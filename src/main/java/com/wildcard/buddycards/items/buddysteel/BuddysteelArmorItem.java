package com.wildcard.buddycards.items.buddysteel;

import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.Multimap;
import com.wildcard.buddycards.BuddyCards;
import com.wildcard.buddycards.client.models.PerfectBuddysteelArmorModel;
import com.wildcard.buddycards.util.BuddysteelGearHelper;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.item.*;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.InteractionHand;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.List;
import java.util.UUID;

import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;

public class BuddysteelArmorItem extends ArmorItem {
    private static final UUID[] ARMOR_MODIFIERS = new UUID[]{UUID.fromString("845DB27C-C624-495F-8C9F-6020A9A58B6B"), UUID.fromString("D8499B04-0E66-4726-AB29-64469D734E0D"), UUID.fromString("9F3D476D-C118-4544-8365-64846904B48E"), UUID.fromString("2AD3F246-FEE1-4E67-B886-69FD380BB150")};
    private static final int[][] DAMAGE_REDUCTION_ARRAY = new int[][] {{1, 4, 5, 2}, {2, 5, 6, 2}, {3, 6, 8, 3}, {4, 7, 9, 4}, {5, 8, 10, 5}};

    public BuddysteelArmorItem(ArmorMaterial materialIn, EquipmentSlot slot) {
        super(materialIn, slot, new Item.Properties().tab(BuddyCards.TAB));
    }

    @Override
    public void appendHoverText(ItemStack stack, Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
        BuddysteelGearHelper.addInformation(stack, tooltip);
        super.appendHoverText(stack, worldIn, tooltip, flagIn);
    }

    @Override
    public Rarity getRarity(ItemStack stack) {
        return material.equals(BuddysteelArmorMaterial.BUDDYSTEEL) ? Rarity.UNCOMMON : Rarity.EPIC;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level worldIn, Player playerIn, InteractionHand handIn) {
        BuddysteelGearHelper.setTag(playerIn, handIn);
        return super.use(worldIn, playerIn, handIn);
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlot slot, ItemStack stack) {
        Multimap<Attribute, AttributeModifier> multimap = super.getAttributeModifiers(slot, stack);
        if (slot == this.getSlot() && stack.hasTag()) {
            multimap = LinkedHashMultimap.create();
            UUID uuid = ARMOR_MODIFIERS[slot.getIndex()];
            float ratio = 0;
            if(stack.getTag().contains("completion"))
                ratio = stack.getTag().getFloat("completion");
            int perfect = material.equals(BuddysteelArmorMaterial.BUDDYSTEEL) ? 0 : 1;
            multimap.put(Attributes.ARMOR, new AttributeModifier(uuid, "Armor modifier", DAMAGE_REDUCTION_ARRAY[(int) (3 * ratio) + perfect][slot.getIndex()], AttributeModifier.Operation.ADDITION));
            multimap.put(Attributes.ARMOR_TOUGHNESS, new AttributeModifier(uuid, "Armor toughness", (int) (ratio * 2) + perfect, AttributeModifier.Operation.ADDITION));
        }
        return multimap;
    }

    @Nullable
    @Override
    @OnlyIn(Dist.CLIENT)
    public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
        if (material.equals(BuddysteelArmorMaterial.PERFECT_BUDDYSTEEL))
            return BuddyCards.MOD_ID + ":textures/models/armor/perfect_buddysteel.png";
        if (material.equals(BuddysteelArmorMaterial.ZYLEX))
            return BuddyCards.MOD_ID + ":textures/models/armor/zylex.png";
        return super.getArmorTexture(stack, entity, slot, type);
    }

    @Nullable
    @Override
    @OnlyIn(Dist.CLIENT)
    public <A extends HumanoidModel<?>> A getArmorModel(LivingEntity entityLiving, ItemStack itemStack, EquipmentSlot armorSlot, A _default) {
        if (material.equals(BuddysteelArmorMaterial.PERFECT_BUDDYSTEEL) || material.equals(BuddysteelArmorMaterial.ZYLEX))
            return (A) new PerfectBuddysteelArmorModel(slot);
        return super.getArmorModel(entityLiving, itemStack, armorSlot, _default);
    }
}
