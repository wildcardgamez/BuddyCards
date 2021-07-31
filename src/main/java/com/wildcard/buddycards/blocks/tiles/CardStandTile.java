package com.wildcard.buddycards.blocks.tiles;

import com.wildcard.buddycards.registries.BuddycardsEntities;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.Clearable;
import net.minecraft.world.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.level.block.entity.BlockEntity;

import java.util.UUID;

public class CardStandTile extends BlockEntity implements Clearable {
    private ItemStack card = ItemStack.EMPTY;
    private int dir = 0;
    private boolean locked = false;
    private String player = "";

    public CardStandTile() {
        super(BuddycardsEntities.CARD_STAND_TILE.get());
    }

    public void setCard(ItemStack stack) {
        if(this.level != null) {
            this.card = stack;
            this.setChanged();
            this.level.sendBlockUpdated(this.getBlockPos(), this.getBlockState(), this.getBlockState(), 3);
        }
    }

    public ItemStack getCard() {
        return this.card;
    }

    public void setDir(int dirIn){
        if(this.level != null) {
            this.dir = dirIn;
            this.setChanged();
            this.level.sendBlockUpdated(this.getBlockPos(), this.getBlockState(), this.getBlockState(), 3);
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
        this.setChanged();
        this.level.sendBlockUpdated(this.getBlockPos(), this.getBlockState(), this.getBlockState(), 3);
        return true;
    }

    public int getDir() {
        return dir;
    }

    @Override
    public CompoundTag save(CompoundTag compound) {
        super.save(compound);
        compound.put("card", this.card.save(new CompoundTag()));
        compound.putInt("dir", this.dir);
        compound.putBoolean("locked", this.locked);
        compound.putString("player", this.player);
        return compound;
    }

    @Override
    public void load(BlockState state, CompoundTag nbt) {
        super.load(state, nbt);
        this.card = ItemStack.of((CompoundTag) nbt.get("card"));
        this.dir = nbt.getInt("dir");
        this.locked = nbt.getBoolean("locked");
        this.player = nbt.getString("player");
    }

    @Override
    public ClientboundBlockEntityDataPacket getUpdatePacket() {
        return new ClientboundBlockEntityDataPacket(this.worldPosition, 0, this.getUpdateTag());
    }

    @Override
    public CompoundTag getUpdateTag() {
        return this.save(new CompoundTag());
    }

    @Override
    public void onDataPacket(Connection net, ClientboundBlockEntityDataPacket pkt) {
        this.load(this.getBlockState(), pkt.getTag());
    }

    @Override
    public void clearContent() {
        this.card = ItemStack.EMPTY;
    }
}
