package com.wildcard.buddycards.util;

import com.wildcard.buddycards.integration.aquaculture.BuddysteelFishingRodItem;
import com.wildcard.buddycards.integration.fd.BuddysteelFoodKnifeItem;
import com.wildcard.buddycards.items.*;
import com.wildcard.buddycards.items.buddysteel.*;
import com.wildcard.buddycards.registries.BuddycardsItems;
import com.wildcard.buddycards.registries.BuddycardsMisc;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.core.NonNullList;
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

    public static final EnchantmentCategory BUDDY_BINDABLE = EnchantmentCategory.create("BUDDY_BINDABLE", EnchantmentHandler::canBuddyBind);
    public static final EnchantmentCategory BUDDY_MEDAL = EnchantmentCategory.create("BUDDY_MEDAL", i -> (i instanceof MedalItem));
    public static final EnchantmentCategory BUDDY_BINDER = EnchantmentCategory.create("BUDDY_BINDER", i -> (i instanceof BinderItem));

    @SubscribeEvent
    public void drop(LivingDropsEvent event) {
        if (event.getEntityLiving() instanceof Player) {
            Player player = (Player) event.getEntityLiving();
            boolean empty = true;
            NonNullList<ItemStack> itemsToSave = NonNullList.create();
            for (ItemEntity i: event.getDrops()) {
                switch(EnchantmentHelper.getItemEnchantmentLevel(BuddycardsMisc.BUDDY_BINDING.get(), i.getItem())) {
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
        Player player = event.getPlayer();
        if (event.isWasDeath() && items.containsKey(player.getUUID().toString())) {
            for (ItemStack i : items.get(player.getUUID().toString())) {
                player.addItem(i);
            }
            items.remove(player.getUUID().toString());
        }
    }

    public static boolean canBuddyBind(Item item) {
        if (item instanceof BinderItem || item instanceof MedalItem || item instanceof BuddysteelArmorItem || item instanceof BuddysteelAxeItem || item instanceof BuddysteelHoeItem || item instanceof BuddysteelPickaxeItem || item instanceof BuddysteelShovelItem || item instanceof BuddysteelPickaxeItem)
            return true;
        if (ModList.get().isLoaded("aquaculture") && item instanceof BuddysteelFishingRodItem)
            return true;
        if (ModList.get().isLoaded("farmersdelight") && item instanceof BuddysteelFoodKnifeItem)
            return true;
        return item.equals(BuddycardsItems.ZYLEX_RING.get()) || item.equals(BuddycardsItems.LUMINIS_RING.get());
    }
}
