package com.wildcard.buddycards.util;

import com.wildcard.buddycards.BuddyCards;
import com.wildcard.buddycards.items.CardItem;
import com.wildcard.buddycards.registries.BuddycardsItems;
import net.minecraft.world.entity.player.Player;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraftforge.fml.RegistryObject;

import java.util.List;

public class BuddysteelGearHelper {
    public static void addInformation(ItemStack stack, List<Component> tooltip) {
        if(stack.hasTag())
            tooltip.add(new TranslatableComponent( "item." + BuddyCards.MOD_ID + ".buddysteel_gear.desc1")
                    .append(String.valueOf((int)(stack.getTag().getFloat("completion") * 100)))
                    .append(new TranslatableComponent( "item." + BuddyCards.MOD_ID + ".buddysteel_gear.desc2")));
        tooltip.add(new TranslatableComponent("item." + BuddyCards.MOD_ID + ".buddysteel_gear.desc3"));
    }

    public static void setTag(Player playerIn, InteractionHand handIn) {
        if(playerIn instanceof ServerPlayer) {
            if(!playerIn.getItemInHand(handIn).hasTag())
                playerIn.getItemInHand(handIn).setTag(new CompoundTag());
            CompoundTag nbt = playerIn.getItemInHand(handIn).getTag();
            nbt.putFloat("completion", getRatio((ServerPlayer) playerIn));
        }
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
}
