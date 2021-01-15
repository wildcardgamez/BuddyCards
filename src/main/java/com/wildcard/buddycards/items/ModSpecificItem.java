package com.wildcard.buddycards.items;

import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.fml.ModList;

public class ModSpecificItem extends Item {
    public ModSpecificItem(Properties properties, String reqMod) {
        super(properties);
        REQ_MOD = reqMod;
    }

    final String REQ_MOD;

    @Override
    public void fillItemGroup(ItemGroup group, NonNullList<ItemStack> items) {
        if(ModList.get().isLoaded(REQ_MOD))
            super.fillItemGroup(group, items);
    }
}
