package com.wildcard.buddycards.integration.aquaculture;

import com.teammetallurgy.aquaculture.item.AquaFishingRodItem;
import com.wildcard.buddycards.BuddyCards;
import com.wildcard.buddycards.items.BuddycardsItemTier;
import com.wildcard.buddycards.util.BuddysteelGearHelper;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

public class BuddysteelFishingRodItem extends AquaFishingRodItem {
    public BuddysteelFishingRodItem() {
        super(BuddycardsItemTier.BUDDYSTEEL, new Item.Properties().tab(BuddyCards.TAB).defaultDurability(512));
    }

    @Override
    public void appendHoverText(@Nonnull ItemStack stack, @Nullable net.minecraft.world.level.Level level, @Nonnull List<Component> tooltips, @Nonnull TooltipFlag tooltipFlag) {
        BuddysteelGearHelper.addInformation(stack, tooltips);
        super.appendHoverText(stack, level, tooltips, tooltipFlag);
    }

    @Override
    public Rarity getRarity(ItemStack stack) {
        return Rarity.UNCOMMON;
    }

    @Nonnull
    @Override
    public InteractionResultHolder<ItemStack> use(@Nonnull net.minecraft.world.level.Level level, Player player, @Nonnull InteractionHand hand) {
        BuddysteelGearHelper.setTag(player, hand);
        return super.use(level, player, hand);
    }
}
