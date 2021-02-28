package com.wildcard.buddycards.blocks;

import com.wildcard.buddycards.util.RegistryHandler;
import net.minecraft.block.BlockState;
import net.minecraft.inventory.IClearable;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.TileEntity;

import java.util.UUID;

public class CardStandTile extends TileEntity implements IClearable {
    private ItemStack card = ItemStack.EMPTY;
    private int dir = 0;
    private boolean locked = false;
    private String player = "";

    public CardStandTile() {
        super(RegistryHandler.CARD_STAND_TILE.get());
    }

    public void setCard(ItemStack stack) {
        if(this.world != null) {
            this.card = stack;
            this.markDirty();
            this.world.notifyBlockUpdate(this.getPos(), this.getBlockState(), this.getBlockState(), 3);
        }
    }

    public ItemStack getCard() {
        return this.card;
    }

    public void setDir(int dirIn){
        if(this.world != null) {
            this.dir = dirIn;
            this.markDirty();
            this.world.notifyBlockUpdate(this.getPos(), this.getBlockState(), this.getBlockState(), 3);
        }
    }

    public boolean isLocked() {
        return locked;
    }

    public boolean toggleLock(UUID playerUUID) {
        if(this.locked) {
            if(this.player.equals(playerUUID.toString())) {
                this.locked = false;
            }
            else
                return false;
        }
        else {
            this.player = playerUUID.toString();
            this.locked = true;
        }
        this.markDirty();
        this.world.notifyBlockUpdate(this.getPos(), this.getBlockState(), this.getBlockState(), 3);
        return true;
    }

    public int getDir() {
        return dir;
    }

    @Override
    public CompoundNBT write(CompoundNBT compound) {
        super.write(compound);
        compound.put("card", this.card.write(new CompoundNBT()));
        compound.putInt("dir", this.dir);
        compound.putBoolean("locked", this.locked);
        compound.putString("player", this.player);
        return compound;
    }

    @Override
    public void read(BlockState state, CompoundNBT nbt) {
        super.read(state, nbt);
        this.card = ItemStack.read((CompoundNBT) nbt.get("card"));
        this.dir = nbt.getInt("dir");
        this.locked = nbt.getBoolean("locked");
        this.player = nbt.getString("player");
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

    @Override
    public void clear() {
        this.card = ItemStack.EMPTY;
    }
}
