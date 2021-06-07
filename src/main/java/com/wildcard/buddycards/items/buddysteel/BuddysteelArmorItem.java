package com.wildcard.buddycards.items.buddysteel;

import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.Multimap;
import com.wildcard.buddycards.BuddyCards;
import com.wildcard.buddycards.client.models.PerfectBuddysteelArmorModel;
import com.wildcard.buddycards.util.BuddysteelGearHelper;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.*;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.List;
import java.util.UUID;

public class BuddysteelArmorItem extends ArmorItem {
    private static final UUID[] ARMOR_MODIFIERS = new UUID[]{UUID.fromString("845DB27C-C624-495F-8C9F-6020A9A58B6B"), UUID.fromString("D8499B04-0E66-4726-AB29-64469D734E0D"), UUID.fromString("9F3D476D-C118-4544-8365-64846904B48E"), UUID.fromString("2AD3F246-FEE1-4E67-B886-69FD380BB150")};
    private static final int[][] DAMAGE_REDUCTION_ARRAY = new int[][] {{1, 4, 5, 2}, {2, 5, 6, 2}, {3, 6, 8, 3}, {4, 7, 9, 4}, {5, 8, 10, 5}};

    public BuddysteelArmorItem(IArmorMaterial materialIn, EquipmentSlotType slot) {
        super(materialIn, slot, new Item.Properties().tab(BuddyCards.TAB));
    }

    @Override
    public void appendHoverText(ItemStack stack, World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        BuddysteelGearHelper.addInformation(stack, tooltip);
        super.appendHoverText(stack, worldIn, tooltip, flagIn);
    }

    @Override
    public Rarity getRarity(ItemStack stack) {
        return material.equals(BuddysteelArmorMaterial.BUDDYSTEEL) ? Rarity.UNCOMMON : Rarity.EPIC;
    }

    @Override
    public ActionResult<ItemStack> use(World worldIn, PlayerEntity playerIn, Hand handIn) {
        BuddysteelGearHelper.setTag(playerIn, handIn);
        return super.use(worldIn, playerIn, handIn);
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlotType slot, ItemStack stack) {
        Multimap<Attribute, AttributeModifier> multimap = super.getAttributeModifiers(slot, stack);
        if (slot == this.getSlot() && stack.hasTag()) {
            multimap = LinkedHashMultimap.create();
            UUID uuid = ARMOR_MODIFIERS[slot.getIndex()];
            float ratio = stack.getTag().getFloat("completion");
            int perfect = material.equals(BuddysteelArmorMaterial.BUDDYSTEEL) ? 0 : 1;
            multimap.put(Attributes.ARMOR, new AttributeModifier(uuid, "Armor modifier", DAMAGE_REDUCTION_ARRAY[(int) (3 * ratio) + perfect][slot.getIndex()], AttributeModifier.Operation.ADDITION));
            multimap.put(Attributes.ARMOR_TOUGHNESS, new AttributeModifier(uuid, "Armor toughness", (int) (ratio * 2) + perfect, AttributeModifier.Operation.ADDITION));
        }
        return multimap;
    }

    @Nullable
    @Override
    public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlotType slot, String type) {
        if (material.equals(BuddysteelArmorMaterial.PERFECT_BUDDYSTEEL))
            return BuddyCards.MOD_ID + ":textures/models/armor/perfect_buddysteel.png";
        if (material.equals(BuddysteelArmorMaterial.ZYLEX))
            return BuddyCards.MOD_ID + ":textures/models/armor/zylex.png";
        return super.getArmorTexture(stack, entity, slot, type);
    }

    @Nullable
    @Override
    public <A extends BipedModel<?>> A getArmorModel(LivingEntity entityLiving, ItemStack itemStack, EquipmentSlotType armorSlot, A _default) {
        if (material.equals(BuddysteelArmorMaterial.PERFECT_BUDDYSTEEL) || material.equals(BuddysteelArmorMaterial.ZYLEX))
            return (A) new PerfectBuddysteelArmorModel(slot);
        return super.getArmorModel(entityLiving, itemStack, armorSlot, _default);
    }
}
