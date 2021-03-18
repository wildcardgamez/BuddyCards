package com.wildcard.buddycards.blocks;

import com.wildcard.buddycards.util.RegistryHandler;
import net.minecraft.inventory.IClearable;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.NonNullList;

public class BuddysteelVaultTile extends TileEntity implements IClearable {
    private final NonNullList<ItemStack> inventory = NonNullList.withSize(108, ItemStack.EMPTY);
    public BuddysteelVaultTile() {
        super(RegistryHandler.BUDDYSTEEL_VAULT_TILE.get());
    }

    @Override
    public void clear() {
        inventory.clear();
    }
}
