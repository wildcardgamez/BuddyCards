package com.wildcard.buddycards.items;

import com.wildcard.buddycards.BuddyCards;
import com.wildcard.buddycards.util.RegistryHandler;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.*;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.fml.ModList;

public class BuddysteelArmorItem extends ArmorItem {
    public BuddysteelArmorItem(IArmorMaterial materialIn, EquipmentSlotType slot) {
        super(materialIn, slot, new Item.Properties().group(BuddyCards.TAB));
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
        if(playerIn instanceof ServerPlayerEntity) {
            CompoundNBT nbt = playerIn.getHeldItem(handIn).getTag();
            nbt.putFloat("completion", getRatio((ServerPlayerEntity) playerIn));
        }
        return super.onItemRightClick(worldIn, playerIn, handIn);
    }

    private float getRatio(ServerPlayerEntity player) {
        int max = 3;
        if (ModList.get().isLoaded("byg"))
            max++;
        if (ModList.get().isLoaded("create"))
            max++;
        if (ModList.get().isLoaded("aquaculture"))
            max++;
        int sets = 0;
        if (player.getAdvancements().getProgress(player.server.getAdvancementManager().getAdvancement(new ResourceLocation(BuddyCards.MOD_ID, "main.complete_set1s"))).isDone())
            sets++;
        if (player.getAdvancements().getProgress(player.server.getAdvancementManager().getAdvancement(new ResourceLocation(BuddyCards.MOD_ID, "main.complete_set2s"))).isDone())
            sets++;
        if (player.getAdvancements().getProgress(player.server.getAdvancementManager().getAdvancement(new ResourceLocation(BuddyCards.MOD_ID, "main.complete_set3s"))).isDone())
            sets++;
        if (player.getAdvancements().getProgress(player.server.getAdvancementManager().getAdvancement(new ResourceLocation(BuddyCards.MOD_ID, "main.complete_set4s"))).isDone())
            sets++;
        if (player.getAdvancements().getProgress(player.server.getAdvancementManager().getAdvancement(new ResourceLocation(BuddyCards.MOD_ID, "main.complete_set5s"))).isDone())
            sets++;
        if (player.getAdvancements().getProgress(player.server.getAdvancementManager().getAdvancement(new ResourceLocation(BuddyCards.MOD_ID, "main.complete_set6s"))).isDone())
            sets++;
        if (sets > max)
            sets = max;
        return (float) sets / max;
    }

    @Override
    public int getMaxDamage(ItemStack stack) {
        return 0;
    }
}
