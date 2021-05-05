package com.wildcard.buddycards.util;

import com.wildcard.buddycards.integration.aquaculture.BuddysteelFishingRodItem;
import com.wildcard.buddycards.integration.fd.BuddysteelFoodKnifeItem;
import com.wildcard.buddycards.items.*;
import com.wildcard.buddycards.items.buddysteel.*;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class EnchantmentHandler {
    private Map<String, NonNullList<ItemStack>> items = new HashMap<String, NonNullList<ItemStack>>();

    public static final EnchantmentType BUDDY_BINDABLE = EnchantmentType.create("BUDDY_BINDABLE", i -> canBuddyBind(i));
    public static final EnchantmentType BUDDY_MEDAL = EnchantmentType.create("BUDDY_MEDAL", i -> (i instanceof SetMedalItem));
    public static final EnchantmentType BUDDY_BINDER = EnchantmentType.create("BUDDY_BINDER", i -> (i instanceof BinderItem));

    @SubscribeEvent
    public void drop(LivingDropsEvent event) {
        if (event.getEntityLiving() instanceof PlayerEntity) {
            PlayerEntity player = (PlayerEntity) event.getEntityLiving();
            boolean empty = true;
            NonNullList<ItemStack> itemsToSave = NonNullList.create();
            for (ItemEntity i: event.getDrops()) {
                switch(EnchantmentHelper.getItemEnchantmentLevel(RegistryHandler.BUDDY_BINDING.get(), i.getItem())) {
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
                items.put(player.getUUID().toString(), itemsToSave);
            if(items.containsKey(player.getUUID().toString())) {
                final List<ItemStack> list = items.get(player.getUUID().toString()).subList(0, items.get(player.getUUID().toString()).size());
                Stream<ItemEntity> stream = StreamSupport.stream(event.getDrops().spliterator(), false);
                Set<ItemEntity> remove = stream.filter(itemToFilter -> list.contains(itemToFilter.getItem())).collect(Collectors.toSet());
                event.getDrops().removeAll(remove);
            }
        }
    }

    @SubscribeEvent
    public void respawn(PlayerEvent.Clone event) {
        PlayerEntity player = event.getPlayer();
        if (event.isWasDeath() && items.containsKey(player.getUUID().toString())) {
            for (ItemStack i : items.get(player.getUUID().toString())) {
                player.addItem(i);
            }
            items.remove(player.getUUID().toString());
        }
    }

    public static boolean canBuddyBind(Item item) {
        if (item instanceof BinderItem || item instanceof SetMedalItem || item instanceof BuddysteelArmorItem || item instanceof BuddysteelAxeItem || item instanceof BuddysteelHoeItem || item instanceof BuddysteelPickaxeItem || item instanceof BuddysteelShovelItem || item instanceof BuddysteelPickaxeItem)
            return true;
        if (ModList.get().isLoaded("aquaculture") && item instanceof BuddysteelFishingRodItem)
            return true;
        if (ModList.get().isLoaded("farmersdelight") && item instanceof BuddysteelFoodKnifeItem)
            return true;
        return false;
    }
}
