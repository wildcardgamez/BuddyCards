package com.wildcard.buddycards.items;

import com.wildcard.buddycards.BuddyCards;
import com.wildcard.buddycards.integration.CuriosIntegration;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Rarity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.fml.ModList;

public class MedalItem extends Item {
    public MedalItem(MedalTypes type) {
        super(new Item.Properties().tab(BuddyCards.TAB).stacksTo(1).fireResistant());
        this.type = type;
    }
    final MedalTypes type;

    @Override
    public ICapabilityProvider initCapabilities(final ItemStack stack, CompoundNBT unused) {
        if (ModList.get().isLoaded("curios")) {
            return CuriosIntegration.initCapabilities(type, stack);
        }
        return super.initCapabilities(stack, unused);
    }

    @Override
    public boolean isEnchantable(ItemStack stack) {
        return true;
    }

    @Override
    public int getEnchantmentValue() {
        return 1;
    }

    @Override
    public Rarity getRarity(ItemStack stack) {
        return type.getRarity();
    }
}
