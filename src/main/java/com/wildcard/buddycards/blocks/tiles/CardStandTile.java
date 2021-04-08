package com.wildcard.buddycards.blocks.tiles;

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
        if(this.level != null) {
            this.card = stack;
            this.setChanged();
            this.level.blockUpdated(this.getBlockPos(), this.getBlockState().getBlock());
        }
    }

    public ItemStack getCard() {
        return this.card;
    }

    public void setDir(int dirIn){
        if(this.level != null) {
            this.dir = dirIn;
            this.setChanged();
            this.level.blockUpdated(this.getBlockPos(), this.getBlockState().getBlock());
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
        setChanged();
        this.level.blockUpdated(this.getBlockPos(), this.getBlockState().getBlock());
        return true;
    }

    public int getDir() {
        return dir;
    }

    @Override
    public CompoundNBT save(CompoundNBT compound) {
        super.save(compound);
        compound.put("card", this.card.serializeNBT());
        compound.putInt("dir", this.dir);
        compound.putBoolean("locked", this.locked);
        compound.putString("player", this.player);
        return compound;
    }

    @Override
    public void deserializeNBT(BlockState state, CompoundNBT nbt) {
        super.deserializeNBT(state, nbt);
        this.card = ItemStack.of((CompoundNBT) nbt.get("card"));
        this.dir = nbt.getInt("dir");
        this.locked = nbt.getBoolean("locked");
        this.player = nbt.getString("player");
    }

    @Override
    public SUpdateTileEntityPacket getUpdatePacket() {
        return new SUpdateTileEntityPacket(this.worldPosition, 0, this.getUpdateTag());
    }

    @Override
    public CompoundNBT getUpdateTag() {
        return this.save(new CompoundNBT());
    }

    @Override
    public void onDataPacket(NetworkManager net, SUpdateTileEntityPacket pkt) {
        this.deserializeNBT(this.getBlockState(), pkt.getTag());
    }

    @Override
    public void clearContent() {
        this.card = ItemStack.EMPTY;
    }
}
