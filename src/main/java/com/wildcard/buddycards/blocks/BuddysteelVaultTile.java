package com.wildcard.buddycards.blocks;

import com.wildcard.buddycards.BuddyCards;
import com.wildcard.buddycards.container.VaultContainer;
import com.wildcard.buddycards.util.RegistryHandler;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IClearable;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.LockableTileEntity;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public class BuddysteelVaultTile extends LockableTileEntity implements IInventory {
    private NonNullList<ItemStack> inventory = NonNullList.withSize(120, ItemStack.EMPTY);
    public BuddysteelVaultTile() {
        super(RegistryHandler.BUDDYSTEEL_VAULT_TILE.get());
    }

    @Override
    public void clear() {
        inventory.clear();
    }

    public void read(BlockState state, CompoundNBT nbt) {
        super.read(state, nbt);
        this.inventory = NonNullList.withSize(120, ItemStack.EMPTY);
        ItemStackHelper.loadAllItems(nbt, this.inventory);
    }

    public CompoundNBT write(CompoundNBT compound) {
        super.write(compound);
        ItemStackHelper.saveAllItems(compound, this.inventory);
        return compound;
    }

    @Override
    protected ITextComponent getDefaultName() {
        return new TranslationTextComponent("container." + BuddyCards.MOD_ID + ".vault");
    }

    @Override
    protected Container createMenu(int id, PlayerInventory player) {
        return new VaultContainer(id, player, this);
    }

    public NonNullList<ItemStack> getItems() {
        return this.inventory;
    }

    public void setItems(NonNullList<ItemStack> itemsIn) {
        this.inventory = itemsIn;
        markDirty();
    }

    @Override
    public int getSizeInventory() {
        return 120;
    }

    @Override
    public boolean isEmpty() {
        return inventory.isEmpty();
    }

    @Override
    public ItemStack getStackInSlot(int index) {
        return inventory.get(index);
    }

    @Override
    public ItemStack decrStackSize(int index, int count) {
        inventory.get(index).shrink(count);
        markDirty();
        return inventory.get(index);
    }

    @Override
    public ItemStack removeStackFromSlot(int index) {
        markDirty();
        return inventory.remove(index);
    }

    @Override
    public void setInventorySlotContents(int index, ItemStack stack) {
        inventory.set(index, stack);
        markDirty();
    }

    @Override
    public int getInventoryStackLimit() {
        return 64;
    }

    @Override
    public boolean isUsableByPlayer(PlayerEntity player) {
        return true;
    }
}
