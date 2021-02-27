package com.wildcard.buddycards.util;

import com.wildcard.buddycards.enchantment.EnchantmentBuddyBinding;
import com.wildcard.buddycards.integration.aquaculture.BuddysteelFishingRodItem;
import com.wildcard.buddycards.items.*;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class EnchantmentHandler {
    private Map<String, NonNullList<ItemStack>> items = new HashMap<String, NonNullList<ItemStack>>();

    public static final EnchantmentType BUDDY_BINDABLE = EnchantmentType.create("BUDDY_BINDABLE", i -> (i instanceof MedalItem || i instanceof BinderItem || i instanceof BuddysteelArmorItem || i instanceof BuddysteelAxeItem || i instanceof BuddysteelHoeItem || i instanceof BuddysteelPickaxeItem || i instanceof BuddysteelShovelItem || i instanceof BuddysteelSwordItem || i instanceof BuddysteelFishingRodItem));
    public static final EnchantmentType BUDDY_MEDAL = EnchantmentType.create("BUDDY_MEDAL", i -> (i instanceof MedalItem));
    public static final EnchantmentType BUDDY_BINDER = EnchantmentType.create("BUDDY_BINDER", i -> (i instanceof BinderItem));

    @SubscribeEvent
    public void drop(LivingDropsEvent event) {
        if (event.getEntityLiving() instanceof PlayerEntity) {
            PlayerEntity player = (PlayerEntity) event.getEntityLiving();
            boolean empty = true;
            NonNullList<ItemStack> itemsToSave = NonNullList.create();
            for (ItemEntity i: event.getDrops()) {
                switch(EnchantmentHelper.getEnchantmentLevel(RegistryHandler.BUDDY_BINDING.get(), i.getItem())) {
                    case 1:
                        if (Math.random() < .3)
                            break;
                    case 2:
                        if (Math.random() < .2)
                            break;
                    case 3:
                        itemsToSave.add(i.getItem());
                        empty = false;
                }
            }
            if (!empty)
                items.put(player.getUniqueID().toString(), itemsToSave);
            if(items.containsKey(player.getUniqueID().toString())) {
                final List<ItemStack> list = items.get(player.getUniqueID().toString()).subList(0, items.get(player.getUniqueID().toString()).size());
                Stream<ItemEntity> stream = StreamSupport.stream(event.getDrops().spliterator(), false);
                Set<ItemEntity> remove = stream.filter(itemToFilter -> list.contains(itemToFilter.getItem())).collect(Collectors.toSet());
                event.getDrops().removeAll(remove);
            }
        }
    }

    @SubscribeEvent
    public void respawn(PlayerEvent.Clone event) {
        PlayerEntity player = event.getPlayer();
        if (event.isWasDeath() && items.containsKey(player.getUniqueID().toString())) {
            for (ItemStack i : items.get(player.getUniqueID().toString())) {
                player.addItemStackToInventory(i);
            }
            items.remove(player.getUniqueID().toString());
        }
    }
}
