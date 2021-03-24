package com.wildcard.buddycards.blocks;

import com.teammetallurgy.aquaculture.block.tileentity.IItemHandlerTEBase;
import com.wildcard.buddycards.BuddyCards;
import com.wildcard.buddycards.container.VaultContainer;
import com.wildcard.buddycards.util.RegistryHandler;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;

public class BuddysteelVaultTile extends IItemHandlerTEBase implements INamedContainerProvider {
    private ITextComponent displayName;

    public BuddysteelVaultTile() {
        super(RegistryHandler.BUDDYSTEEL_VAULT_TILE.get());
    }

    @Override
    protected IItemHandler createItemHandler() {
        return new ItemStackHandler(120);
    }

    public void setDisplayName(ITextComponent displayNameIn) {
        displayName = displayNameIn;
    }

    public ITextComponent getDisplayName() {
        return displayName == null ? new TranslationTextComponent("block." + BuddyCards.MOD_ID + ".vault") : displayName;
    }

    @Override
    public Container createMenu(int p_createMenu_1_, PlayerInventory p_createMenu_2_, PlayerEntity p_createMenu_3_) {
        return new VaultContainer(p_createMenu_1_, p_createMenu_2_, this);
    }
}
