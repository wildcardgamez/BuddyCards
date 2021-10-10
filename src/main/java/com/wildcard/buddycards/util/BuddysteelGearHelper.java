package com.wildcard.buddycards.util;

import com.wildcard.buddycards.BuddyCards;
import com.wildcard.buddycards.items.CardItem;
import com.wildcard.buddycards.registries.BuddycardsItems;
import com.wildcard.buddycards.registries.BuddycardsMisc;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraftforge.fmllegacy.RegistryObject;

import java.util.List;

public class BuddysteelGearHelper {
    public static void addInformation(ItemStack stack, List<Component> tooltip) {
        if(!stack.hasTag())
            tooltip.add(new TranslatableComponent( "item.buddycards.buddysteel_gear.desc"));
    }

    public static boolean setTag(Player playerIn, InteractionHand handIn) {
        if(playerIn instanceof ServerPlayer) {
            boolean altered = false;
            //Get the percentage of collectable cards collected to use for enchant levels, along with the item
            float ratio = getRatio((ServerPlayer) playerIn);
            ItemStack stack = playerIn.getItemInHand(handIn);
            //If it can have the enchant, make sure it's the right level or change it
            if(BuddyEnchantmentHandler.BUDDY_TOOLS.canEnchant(stack.getItem()))
                if(setEnchant((int) (ratio * 4), stack, BuddycardsMisc.BUDDY_EFF.get(), playerIn))
                    altered = true;
            if(BuddyEnchantmentHandler.BUDDY_ARMOR.canEnchant(stack.getItem()))
                if(setEnchant((int) (ratio * 4), stack, BuddycardsMisc.BUDDY_PROT.get(), playerIn))
                    altered = true;
            if(BuddyEnchantmentHandler.BUDDY_WEAPONS.canEnchant(stack.getItem()))
                if(setEnchant((int) (ratio * 4), stack, BuddycardsMisc.BUDDY_DMG.get(), playerIn))
                    altered = true;
            if(BuddyEnchantmentHandler.BUDDY_MEDAL.canEnchant(stack.getItem()))
                if(setEnchant((int) (ratio * 5), stack, BuddycardsMisc.BUDDY_BOOST.get(), playerIn))
                    altered = true;
            return altered;
        }
        return false;
    }

    public static float getRatio(ServerPlayer player) {
        int i = 0;
        int total = 0;
        for (RegistryObject<CardItem> card : BuddycardsItems.LOADED_CARDS) {
            if(player.getStats().getValue(Stats.ITEM_PICKED_UP.get(card.get())) > 0)
                i++;
            total++;
        }
        return (float)i/total;
    }

    public static boolean setEnchant(int level, ItemStack stack, Enchantment enchantment, Player player) {
        int initialLevel = EnchantmentHelper.getItemEnchantmentLevel(enchantment, stack);
        //Check if changes are needed
        if(level == initialLevel)
            return false;
        if(initialLevel != 0) {
            player.displayClientMessage(new TranslatableComponent("item.buddycards.buddysteel_gear.warn"), true);
            return false;
        }
        stack.enchant(enchantment, level);
        return true;
    }
}
