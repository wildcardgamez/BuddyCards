package com.wildcard.buddycards.blocks;

import com.wildcard.buddycards.items.CardItem;
import com.wildcard.buddycards.util.RegistryHandler;
import net.minecraft.block.BlockState;
import net.minecraft.inventory.IClearable;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
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
    public SUpdateTileEntityPacket getUpdatePacket() {
        return new SUpdateTileEntityPacket(this.pos, 0, this.getUpdateTag());
    }

    @Override
    public CompoundNBT getUpdateTag() {
        return this.write(new CompoundNBT());
    }

    @Override
    public void onDataPacket(NetworkManager net, SUpdateTileEntityPacket pkt) {
        this.read(this.getBlockState(), pkt.getNbtCompound());
    }

    public NonNullList<ItemStack> getInventory() {
        return inventory;
    }

    public int getCardsAmt() {
        int amt = 0;
        for (int i = 0; i < 6; i++) {
            if (inventory.get(i).getItem() instanceof CardItem)
                amt++;
        }
        return amt;
    }

    @Override
    public void clear() {
        this.inventory.clear();
    }
}
