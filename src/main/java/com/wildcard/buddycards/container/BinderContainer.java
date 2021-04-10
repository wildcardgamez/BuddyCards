package com.wildcard.buddycards.container;

import com.wildcard.buddycards.inventory.BinderInventory;
import com.wildcard.buddycards.items.CardItem;
import com.wildcard.buddycards.util.RegistryHandler;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;

public class BinderContainer extends Container {

    private final BinderInventory binderInv;
    private static int[] slotsForLevel = {54, 72, 96, 120};

    public BinderContainer(int id, PlayerInventory playerInventory) {
        this(id, playerInventory, new BinderInventory(slotsForLevel[EnchantmentHelper.getEnchantmentLevel(RegistryHandler.EXTRA_PAGE.get(), playerInventory.getCurrentItem())], playerInventory.getCurrentItem()));
    }

    public BinderContainer(int id, PlayerInventory playerInv, BinderInventory binderInvIn) {
        super(RegistryHandler.BINDER_CONTAINER.get(), id);
        assertInventorySize(binderInvIn, binderInvIn.getSizeInventory());
        binderInv = binderInvIn;

        if (binderInv.getSizeInventory() == 54) {
            //Set up slots for binder
            for (int y = 0; y < 6; y++) {
                for (int x = 0; x < 9; x++) {
                    this.addSlot(new BinderSlot(binderInv, x + (y * 9), 8 + x * 18, 18 + y * 18));
                }
            }
            //Set up slots for inventory
            for (int y = 0; y < 3; y++) {
                for (int x = 0; x < 9; x++) {
                    this.addSlot(new InvSlot(playerInv, x + (y * 9) + 9, 8 + x * 18, 140 + y * 18));
                }
            }
            //Set up slots for hotbar
            for (int x = 0; x < 9; x++) {
                this.addSlot(new InvSlot(playerInv, x, 8 + x * 18, 198));
            }
        }
        else if (binderInv.getSizeInventory() == 72) {
            //Set up slots for binder
            for (int y = 0; y < 6; y++) {
                for (int x = 0; x < 12; x++) {
                    this.addSlot(new BinderSlot(binderInv, x + (y * 12), 8 + x * 18, 18 + y * 18));
                }
            }
            //Set up slots for inventory
            for (int y = 0; y < 3; y++) {
                for (int x = 0; x < 9; x++) {
                    this.addSlot(new InvSlot(playerInv, x + (y * 9) + 9, 35 + x * 18, 140 + y * 18));
                }
            }
            //Set up slots for hotbar
            for (int x = 0; x < 9; x++) {
                this.addSlot(new InvSlot(playerInv, x, 35 + x * 18, 198));
            }
        }
        else if (binderInv.getSizeInventory() == 96) {
            //Set up slots for binder
            for (int y = 0; y < 8; y++) {
                for (int x = 0; x < 12; x++) {
                    this.addSlot(new BinderSlot(binderInv, x + (y * 12), 8 + x * 18, 18 + y * 18));
                }
            }
            //Set up slots for inventory
            for (int y = 0; y < 3; y++) {
                for (int x = 0; x < 9; x++) {
                    this.addSlot(new InvSlot(playerInv, x + (y * 9) + 9, 35 + x * 18, 176 + y * 18));
                }
            }
            //Set up slots for hotbar
            for (int x = 0; x < 9; x++) {
                this.addSlot(new InvSlot(playerInv, x, 35 + x * 18, 234));
            }
        }
        else if (binderInv.getSizeInventory() == 120) {
            //Set up slots for binder
            for (int y = 0; y < 10; y++) {
                for (int x = 0; x < 12; x++) {
                    this.addSlot(new BinderSlot(binderInv, x + (y * 12), 8 + x * 18, 18 + y * 18));
                }
            }
            //Set up slots for inventory
            for (int y = 0; y < 3; y++) {
                for (int x = 0; x < 9; x++) {
                    this.addSlot(new InvSlot(playerInv, x + (y * 9) + 9, 35 + x * 18, 212 + y * 18));
                }
            }
            //Set up slots for hotbar
            for (int x = 0; x < 9; x++) {
                this.addSlot(new InvSlot(playerInv, x, 35 + x * 18, 270));
            }
        }

        if(!binderInv.ender)
            binderInv.openInventory(playerInv.player);
    }

    @Override
    public boolean canInteractWith(PlayerEntity playerIn) {
        return true;
    }

    public class BinderSlot extends Slot {
        public BinderSlot(IInventory inventoryIn, int index, int xPosition, int yPosition) {
            super(inventoryIn, index, xPosition, yPosition);
        }

        //Only let cards go into card slots
        @Override
        public boolean isItemValid(ItemStack stack) {
            return stack.getItem() instanceof CardItem;
        }
    }
    public class InvSlot extends Slot {
        public InvSlot(IInventory inventoryIn, int index, int xPosition, int yPosition) {
            super(inventoryIn, index, xPosition, yPosition);
        }

        //Only let the stack move if it isn't the open binder
        @Override
        public boolean canTakeStack(PlayerEntity playerIn) {
            return !(this.getStack().equals(((BinderInventory)binderInv).binder));
        }

        @Override
        public boolean isItemValid(ItemStack stack) {
            return !(this.getStack().equals(((BinderInventory)binderInv).binder));
        }
    }

    @Override
    public void onContainerClosed(PlayerEntity playerIn) {
        //Run the code to check the inventory and convert to nbt
        if(!binderInv.ender)
            binderInv.closeInventory(playerIn);
        super.onContainerClosed(playerIn);
    }

    @Override
    public ItemStack transferStackInSlot(PlayerEntity playerIn, int index) {
        ItemStack stack = ItemStack.EMPTY;
        Slot slot = inventorySlots.get(index);
        if(slot != null && slot.getHasStack())
        {
            stack = slot.getStack().copy();
            if (index < inventorySlots.size() - 36)
            {
                if(!this.mergeItemStack(slot.getStack(), 54, inventorySlots.size(), true))
                    return ItemStack.EMPTY;
            }
            else if(!this.mergeItemStack(slot.getStack(), 0, 54, false))
                return ItemStack.EMPTY;
            if(slot.getStack().isEmpty())
                slot.putStack(ItemStack.EMPTY);
            else
                slot.onSlotChanged();
        }
        return stack;
    }
}
