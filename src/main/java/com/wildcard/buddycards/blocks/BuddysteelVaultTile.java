package com.wildcard.buddycards.blocks;

import com.wildcard.buddycards.BuddyCards;
import com.wildcard.buddycards.util.RegistryHandler;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nullable;

public class BuddysteelVaultTile extends TileEntity implements INamedContainerProvider {
    public ItemStackHandler inventory = new ItemStackHandler(120);
    private ITextComponent displayName;

    public BuddysteelVaultTile() {
        super(RegistryHandler.BUDDYSTEEL_VAULT_TILE.get());
    }

    @Override
    public CompoundNBT write(CompoundNBT compound) {
        compound.put("inventory", inventory.serializeNBT());
        return super.write(compound);
    }

    @Override
    public void read(BlockState state, CompoundNBT nbt) {
        super.read(state, nbt);
        inventory.deserializeNBT(nbt.getCompound("inventory"));
    }

    public void setDisplayName(ITextComponent displayNameIn) {
        displayName = displayNameIn;
    }

    public ITextComponent getDisplayName() {
        return displayName == null ? new TranslationTextComponent("block." + BuddyCards.MOD_ID + ".vault") : displayName;
    }

    @Nullable
    @Override
    public Container createMenu(int p_createMenu_1_, PlayerInventory p_createMenu_2_, PlayerEntity p_createMenu_3_) {
        return null;
    }

    public int getSetNumber() {
        return ((BuddysteelVaultBlock)this.getBlockState().getBlock()).getSetNumber();
    }
}
