package com.wildcard.buddycards.container;

import com.wildcard.buddycards.blocks.tiles.BuddysteelVaultTile;
import com.wildcard.buddycards.items.CardItem;
import com.wildcard.buddycards.util.RegistryHandler;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.SlotItemHandler;

public class VaultContainer extends Container {
    IItemHandler handler;
    BuddysteelVaultTile tile;

    public VaultContainer(int id, PlayerInventory playerInv) {
        this(id, playerInv, new ItemStackHandler(120), new BuddysteelVaultTile());
    }
    
    public VaultContainer(int id, PlayerInventory playerInv, IItemHandler handlerIn, BuddysteelVaultTile tileIn) {
        super(RegistryHandler.VAULT_CONTAINER.get(), id);
        tile = tileIn;
        handler = handlerIn;
        //Set up vault card slots
        for (int y = 0; y < 9; y++) {
            for (int x = 0; x < 12; x++) {
                this.addSlot(new CardSlot(handler, x + (y * 12), 8 + x * 18, 18 + y * 18));
            }
        }
        //Set up vault item slots
        for (int x = 0; x < 12; x++) {
            this.addSlot(new SlotItemHandler(handler, x + 108, 8 + x * 18, 180));
        }
        //Set up slots for inventory
        for (int y = 0; y < 3; y++) {
            for (int x = 0; x < 9; x++) {
                this.addSlot(new Slot(playerInv, x + (y * 9) + 9, 35 + x * 18, 212 + y * 18));
            }
        }
        //Set up slots for hotbar
        for (int x = 0; x < 9; x++) {
            this.addSlot(new Slot(playerInv, x, 35 + x * 18, 270));
        }
    }

    @Override
    public boolean stillValid(PlayerEntity playerIn) {
        return true;
    }

    public class CardSlot extends SlotItemHandler {
        public CardSlot(IItemHandler inventoryIn, int index, int xPosition, int yPosition) {
            super(inventoryIn, index, xPosition, yPosition);
        }
        //Only let cards go into card slots
        @Override
        public boolean mayPlace(ItemStack stack) {
            return stack.getItem() instanceof CardItem && !tile.isLocked();
        }

        //Only let unlocked vaults be manipulated
        @Override
        public boolean mayPickup(PlayerEntity playerIn) {
            return !tile.isLocked() && super.mayPickup(playerIn);
        }
    }

    @Override
    public ItemStack quickMoveStack(PlayerEntity playerIn, int index) {
        ItemStack stack = ItemStack.EMPTY;
        Slot slot = slots.get(index);
        if(slot != null && slot.hasItem())
        {
            stack = slot.getItem().copy();
            if (index < 120)
            {
                if(!this.moveItemStackTo(slot.getItem(), 120, slots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            }
            else if(!this.moveItemStackTo(slot.getItem(), 0, 120, false))
                return ItemStack.EMPTY;
            if(slot.getItem().isEmpty())
                slot.set(ItemStack.EMPTY);
            else
                slot.setChanged();
        }
        return stack;
    }
}
