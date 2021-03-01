package com.wildcard.buddycards.integration.aquaculture;

import com.teammetallurgy.aquaculture.item.AquaFishingRodItem;
import com.wildcard.buddycards.BuddyCards;
import com.wildcard.buddycards.util.BuddysteelGearHelper;
import com.wildcard.buddycards.items.buddysteel.BuddysteelItemTier;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Rarity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;

import java.util.List;

public class BuddysteelFishingRodItem extends AquaFishingRodItem {
    public BuddysteelFishingRodItem() {
        super(BuddysteelItemTier.BUDDYSTEEL, new Item.Properties().group(BuddyCards.TAB).defaultMaxDamage(512));
    }

    @Override
    public void addInformation(ItemStack stack, World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        BuddysteelGearHelper.addInformation(stack, tooltip);
        super.addInformation(stack, worldIn, tooltip, flagIn);
    }

    @Override
    public Rarity getRarity(ItemStack stack) {
        return Rarity.UNCOMMON;
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
        BuddysteelGearHelper.setTag(playerIn, handIn);
        return super.onItemRightClick(worldIn, playerIn, handIn);
    }
}
