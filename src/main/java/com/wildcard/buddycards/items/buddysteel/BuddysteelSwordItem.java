package com.wildcard.buddycards.items.buddysteel;

import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.Multimap;
import com.wildcard.buddycards.BuddyCards;
import com.wildcard.buddycards.registries.BuddycardsMisc;
import com.wildcard.buddycards.util.BuddysteelGearHelper;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

import java.util.List;

public class BuddysteelSwordItem extends SwordItem {
    public BuddysteelSwordItem(BuddysteelItemTier tier, int damage) {
        super(tier, damage, -2.4f, new Item.Properties().tab(BuddyCards.TAB));
    }

    @Override
    public void appendHoverText(ItemStack stack, Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
        BuddysteelGearHelper.addInformation(stack, tooltip);
        super.appendHoverText(stack, worldIn, tooltip, flagIn);
    }

    @Override
    public Rarity getRarity(ItemStack stack) {
        return getTier().equals(BuddysteelItemTier.BUDDYSTEEL) ? Rarity.UNCOMMON : Rarity.EPIC;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level worldIn, Player playerIn, InteractionHand handIn) {
        if (!BuddysteelGearHelper.setTag(playerIn, handIn))
            return super.use(worldIn, playerIn, handIn);
        return new InteractionResultHolder<>(InteractionResult.SUCCESS, playerIn.getItemInHand(handIn));
    }
}
