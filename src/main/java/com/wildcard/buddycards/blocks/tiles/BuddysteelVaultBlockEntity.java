package com.wildcard.buddycards.blocks.tiles;

import com.wildcard.buddycards.BuddyCards;
import com.wildcard.buddycards.container.VaultContainer;
import com.wildcard.buddycards.items.CardItem;
import com.wildcard.buddycards.registries.BuddycardsEntities;
import com.wildcard.buddycards.registries.BuddycardsItems;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;
import java.util.UUID;

public class BuddysteelVaultBlockEntity extends BlockEntity implements MenuProvider {
    private final LazyOptional<ItemStackHandler> handler = LazyOptional.of(() -> new ItemStackHandler(120) {
        @Override
        public boolean isItemValid(int slot, ItemStack stack) {
            return !locked && (slot >= 108 || stack.getItem() instanceof CardItem) && super.isItemValid(slot, stack);
        }

        @Nonnull
        @Override
        public ItemStack extractItem(int slot, int amount, boolean simulate) {
            if(locked)
                return ItemStack.EMPTY;
            return super.extractItem(slot, amount, simulate);
        }
    });
    private Component name;
    private boolean locked = false;
    private String player = "";

    public BuddysteelVaultBlockEntity(BlockPos pos, BlockState state) {
        super(BuddycardsEntities.VAULT_TILE.get(), pos, state);
    }

    @Override
    public AbstractContainerMenu createMenu(int id, Inventory inventory, Player player) {
        return new VaultContainer(id, inventory, this.getBlockPos());
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
        this.setChanged();
        this.level.sendBlockUpdated(this.getBlockPos(), this.getBlockState(), this.getBlockState(), 3);
        return true;
    }

    @Override
    public CompoundTag save(CompoundTag tag) {
        tag.putBoolean("locked", this.locked);
        tag.putString("player", this.player);
        if(name != null)
            tag.putString("name", Component.Serializer.toJson(name));
        this.handler.ifPresent((stack) -> {
            CompoundTag compound = (CompoundTag)((INBTSerializable)stack).serializeNBT();
            tag.put("inv", compound);
        });
        return super.save(tag);
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        this.locked = tag.getBoolean("locked");
        this.player = tag.getString("player");
        if(tag.contains("name"))
            this.name = Component.Serializer.fromJson(tag.getString("name"));
        CompoundTag invTag = tag.getCompound("inv");
        this.handler.ifPresent((stack) -> {
            ((INBTSerializable)stack).deserializeNBT(invTag);
        });
    }

    @Override
    public Component getDisplayName() {
        if (name != null)
            return name;
        return new TranslatableComponent("block." + BuddyCards.MOD_ID + ".buddysteel_vault");
    }

    public void setDisplayName(Component nameIn) {
        name = nameIn;
    }

    @Override
    public <T> LazyOptional<T> getCapability(Capability<T> cap, Direction side) {
        if(cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
            return handler.cast();
        return super.getCapability(cap, side);
    }

    public IItemHandler getHandler() {
        return handler.orElse(new ItemStackHandler(120));
    }
}
