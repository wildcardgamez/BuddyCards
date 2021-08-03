package com.wildcard.buddycards.container;

import com.wildcard.buddycards.inventory.BinderInventory;
import com.wildcard.buddycards.items.CardItem;
import com.wildcard.buddycards.registries.BuddycardsMisc;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;

public class BinderContainer extends AbstractContainerMenu {

    private final BinderInventory binderInv;
    private static int[] slotsForLevel = {54, 72, 96, 120};

    public BinderContainer(int id, Inventory playerInventory) {
        this(id, playerInventory, new BinderInventory(slotsForLevel[EnchantmentHelper.getItemEnchantmentLevel(BuddycardsMisc.EXTRA_PAGE.get(), playerInventory.getSelected())], playerInventory.getSelected()));
    }

    public BinderContainer(int id, Inventory playerInv, BinderInventory binderInvIn) {
        super(BuddycardsMisc.BINDER_CONTAINER.get(), id);
        checkContainerSize(binderInvIn, binderInvIn.getContainerSize());
        binderInv = binderInvIn;

        if (binderInv.getContainerSize() == 54) {
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
        else if (binderInv.getContainerSize() == 72) {
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
        else if (binderInv.getContainerSize() == 96) {
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
        else if (binderInv.getContainerSize() == 120) {
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
            binderInv.startOpen(playerInv.player);
    }

    @Override
    public boolean stillValid(Player playerIn) {
        return true;
    }

    public class BinderSlot extends Slot {
        public BinderSlot(Container inventoryIn, int index, int xPosition, int yPosition) {
            super(inventoryIn, index, xPosition, yPosition);
        }

        //Only let cards go into card slots
        @Override
        public boolean mayPlace(ItemStack stack) {
            return stack.getItem() instanceof CardItem;
        }
    }
    public class InvSlot extends Slot {
        public InvSlot(Container inventoryIn, int index, int xPosition, int yPosition) {
            super(inventoryIn, index, xPosition, yPosition);
        }

        //Only let the stack move if it isn't the open binder
        @Override
        public boolean mayPickup(Player playerIn) {
            return !(this.getItem().equals(((BinderInventory)binderInv).binder));
        }

        @Override
        public boolean mayPlace(ItemStack stack) {
            return !(this.getItem().equals(((BinderInventory)binderInv).binder));
        }
    }

    @Override
    public void removed(Player playerIn) {
        //Run the code to check the inventory and convert to nbt
        if(!binderInv.ender)
            binderInv.stopOpen(playerIn);
        //else
            //EnderBinderSaveData.get(playerIn.createCommandSourceStack().getLevel()).setDirty();
        super.removed(playerIn);
    }

    @Override
    public ItemStack quickMoveStack(Player playerIn, int index) {
        ItemStack stack = ItemStack.EMPTY;
        Slot slot = slots.get(index);
        if(slot != null && slot.hasItem())
        {
            stack = slot.getItem().copy();
            if (index < slots.size() - 36)
            {
                if(!this.moveItemStackTo(slot.getItem(), 54, slots.size(), true))
                    return ItemStack.EMPTY;
            }
            else if(!this.moveItemStackTo(slot.getItem(), 0, 54, false))
                return ItemStack.EMPTY;
            if(slot.getItem().isEmpty())
                slot.set(ItemStack.EMPTY);
            else
                slot.setChanged();
        }
        return stack;
    }
}
