package com.wildcard.buddycards.integration.fd;

import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.Multimap;
import com.wildcard.buddycards.BuddyCards;
import com.wildcard.buddycards.items.buddysteel.BuddysteelItemTier;
import com.wildcard.buddycards.util.BuddysteelGearHelper;
/*
public class BuddysteelFoodKnifeItem extends KnifeItem {
    public BuddysteelFoodKnifeItem() {
        super(BuddysteelItemTier.BUDDYSTEEL, 1, -1.8f, new Item.Properties().tab(BuddyCards.TAB));
    }

    @Override
    public void appendHoverText(ItemStack stack, World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        BuddysteelGearHelper.addInformation(stack, tooltip);
        super.appendHoverText(stack, worldIn, tooltip, flagIn);
    }

    @Override
    public Rarity getRarity(ItemStack stack) {
        return Rarity.UNCOMMON;
    }

    @Override
    public ActionResult<ItemStack> use(World worldIn, PlayerEntity playerIn, Hand handIn) {
        BuddysteelGearHelper.setTag(playerIn, handIn);
        return super.use(worldIn, playerIn, handIn);
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlotType slot, ItemStack stack) {
        Multimap<Attribute, AttributeModifier> multimap = super.getAttributeModifiers(slot, stack);
        if (stack.hasTag() && slot == EquipmentSlotType.MAINHAND) {
            multimap = LinkedHashMultimap.create();
            float ratio = stack.getTag().getFloat("completion");
            multimap.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_UUID, "Weapon modifier", (double)this.getAttackDamage() + 2 + (ratio * 3), AttributeModifier.Operation.ADDITION));
            multimap.put(Attributes.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED_UUID, "Weapon modifier", -1.8, AttributeModifier.Operation.ADDITION));
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
*/