package com.wildcard.buddycards.blocks;

import com.wildcard.buddycards.BuddyCards;
import com.wildcard.buddycards.container.VaultContainer;
import com.wildcard.buddycards.items.CardItem;
import com.wildcard.buddycards.util.RegistryHandler;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import java.util.UUID;

public class BuddysteelVaultTile extends TileEntity implements INamedContainerProvider {
    private LazyOptional<ItemStackHandler> handler = LazyOptional.of(() -> new ItemStackHandler(120) {
        @Override
        public boolean isItemValid(int slot, ItemStack stack) {
            return (slot > 108 || stack.getItem() instanceof CardItem) && super.isItemValid(slot, stack);
        }
    });
    private NonNullList<ItemStack> displayCards = NonNullList.withSize(8, ItemStack.EMPTY);;
    private ITextComponent name;
    private boolean locked = false;
    private String player = "";

    public BuddysteelVaultTile() {
        super(RegistryHandler.BUDDYSTEEL_VAULT_TILE.get());
    }

    @Override
    public Container createMenu(int p_createMenu_1_, PlayerInventory p_createMenu_2_, PlayerEntity p_createMenu_3_) {
        return new VaultContainer(p_createMenu_1_, p_createMenu_2_, handler.orElse(new ItemStackHandler()), this);
    }

    public boolean isLocked() {
        return this.locked;
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

    @Override
    public CompoundNBT write(CompoundNBT tag) {
        tag.putBoolean("locked", this.locked);
        tag.putString("player", this.player);
        if(name != null)
            tag.putString("name", ITextComponent.Serializer.toJson(name));
        this.handler.ifPresent((stack) -> {
            CompoundNBT compound = (CompoundNBT)((INBTSerializable)stack).serializeNBT();
            tag.put("inv", compound);
        });
        return super.write(tag);
    }

    @Override
    public void read(BlockState state, CompoundNBT tag) {
        super.read(state, tag);
        this.locked = tag.getBoolean("locked");
        this.player = tag.getString("player");
        if(tag.contains("name"))
            this.name = ITextComponent.Serializer.getComponentFromJson(tag.getString("name"));
        CompoundNBT invTag = tag.getCompound("inv");
        this.handler.ifPresent((stack) -> {
            ((INBTSerializable)stack).deserializeNBT(invTag);
        });
    }

    @Override
    public ITextComponent getDisplayName() {
        if (name != null)
            return name;
        return new TranslationTextComponent("block." + BuddyCards.MOD_ID + ".vault");
    }

    public void setDisplayName(ITextComponent nameIn) {
        name = nameIn;
    }

    @Override
    public <T> LazyOptional<T> getCapability(Capability<T> cap, Direction side) {
        if(cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
            return handler.cast();
        return super.getCapability(cap, side);
    }
}
