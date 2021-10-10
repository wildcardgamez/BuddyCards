package com.wildcard.buddycards.items;

import com.wildcard.buddycards.BuddyCards;
import com.wildcard.buddycards.integration.CuriosIntegration;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.fml.ModList;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

public class MedalItem extends Item implements ICurioItem {
    public MedalItem(MedalTypes type) {
        super(new Item.Properties().tab(BuddyCards.TAB).stacksTo(1).fireResistant());
        this.type = type;
    }
    final MedalTypes type;

    @Override
    public ICapabilityProvider initCapabilities(final ItemStack stack, CompoundTag unused) {
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
