package com.wildcard.buddycards.items;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Rarity;
import net.minecraft.util.*;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import net.minecraftforge.fml.ModList;
import vazkii.patchouli.api.PatchouliAPI;

public class PatchouliGuidebookItem extends Item {
    public PatchouliGuidebookItem() {
        super(new Item.Properties().maxStackSize(1).rarity(Rarity.COMMON));
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
        if (playerIn instanceof ServerPlayerEntity)
            PatchouliAPI.instance.openBookGUI((ServerPlayerEntity)playerIn, Registry.ITEM.getKey(this));
        return new ActionResult<>(ActionResultType.SUCCESS, playerIn.getHeldItem(handIn));
    }

    @Override
    public void fillItemGroup(ItemGroup group, NonNullList<ItemStack> items) {
        if(!ModList.get().isLoaded("patchouli"))
            return;
        super.fillItemGroup(group, items);
    }
}
