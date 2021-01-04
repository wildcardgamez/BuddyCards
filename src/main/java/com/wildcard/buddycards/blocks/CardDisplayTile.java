package com.wildcard.buddycards.blocks;

import com.wildcard.buddycards.util.RegistryHandler;
import net.minecraft.block.BlockState;
import net.minecraft.inventory.IClearable;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.NonNullList;

public class CardDisplayTile extends TileEntity implements IClearable {
    private final NonNullList<ItemStack> inventory = NonNullList.withSize(6, ItemStack.EMPTY);

    public CardDisplayTile() {
        super(RegistryHandler.CARD_DISPLAY_TILE.get());
    }

    public void putCardInSlot(ItemStack stack, int pos) {
        if(this.world != null) {
            this.inventory.set(pos - 1, stack);
            this.markDirty();
            this.world.notifyBlockUpdate(this.getPos(), this.getBlockState(), this.getBlockState(), 3);
        }
    }

    public ItemStack getCardInSlot(int pos) {
        return this.inventory.get(pos - 1);
    }

    @Override
    public CompoundNBT write(CompoundNBT compound) {
        super.write(compound);
        ItemStackHelper.saveAllItems(compound, this.inventory, true);
        return compound;
    }

    @Override
    public void read(BlockState state, CompoundNBT nbt) {
        super.read(state, nbt);
        this.inventory.clear();
        ItemStackHelper.loadAllItems(nbt, this.inventory);
    }

    @Override
    public void clear() {
        this.inventory.clear();
    }

    @Override
    public void handleUpdateTag(BlockState state, CompoundNBT nbt) {
        read(state, nbt);
    }

    @Override
    public CompoundNBT getUpdateTag() {
        return write(new CompoundNBT());
    }

    public NonNullList<ItemStack> getInventory() {
        return inventory;
    }
}
