package com.wildcard.buddycards.items.buddysteel;

import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.Multimap;
import com.wildcard.buddycards.BuddyCards;
import com.wildcard.buddycards.util.BuddysteelGearHelper;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.InteractionHand;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.ToolType;

import java.util.List;

import net.minecraft.world.item.Item.Properties;

public class BuddysteelAxeItem extends AxeItem {
    public BuddysteelAxeItem(BuddysteelItemTier tier, float damage) {
        super(tier, damage, -3.1f, new Properties().tab(BuddyCards.TAB));
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
        BuddysteelGearHelper.setTag(playerIn, handIn);
        return super.use(worldIn, playerIn, handIn);
    }

    @Override
    public float getDestroySpeed(ItemStack stack, BlockState state) {
        float eff = super.getDestroySpeed(stack, state);
        if (stack.hasTag() && eff == speed)
            return eff + (int) (4 * stack.getTag().getFloat("completion"));
        else
            return eff;
    }

    @Override
    public int getHarvestLevel(ItemStack stack, ToolType tool, Player player, BlockState state) {
        if (!stack.hasTag())
            return super.getHarvestLevel(stack, tool, player, state);
        else
            return super.getHarvestLevel(stack, tool, player, state) + (int) (2 * stack.getTag().getFloat("completion"));
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlot slot, ItemStack stack) {
        Multimap<Attribute, AttributeModifier> multimap = super.getAttributeModifiers(slot, stack);
        if (stack.hasTag() && slot == EquipmentSlot.MAINHAND) {
            multimap = LinkedHashMultimap.create();
            float ratio = 0;
            if(stack.getTag().contains("completion"))
                ratio = stack.getTag().getFloat("completion");
            multimap.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_UUID, "Weapon modifier", (double)this.getAttackDamage() + 2 + (int) (ratio*8)/2, AttributeModifier.Operation.ADDITION));
            multimap.put(Attributes.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED_UUID, "Weapon modifier", -3.1, AttributeModifier.Operation.ADDITION));
        }
        return multimap;
    }
}
