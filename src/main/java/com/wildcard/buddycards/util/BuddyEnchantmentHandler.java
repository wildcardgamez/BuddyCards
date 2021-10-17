package com.wildcard.buddycards.util;

import com.wildcard.buddycards.integration.aquaculture.BuddysteelFishingRodItem;
import com.wildcard.buddycards.items.BinderItem;
import com.wildcard.buddycards.items.MedalItem;
import com.wildcard.buddycards.items.YannelArmorItem;
import com.wildcard.buddycards.items.YannelElytraItem;
import com.wildcard.buddycards.items.buddysteel.*;
import com.wildcard.buddycards.registries.BuddycardsItems;
import com.wildcard.buddycards.registries.BuddycardsMisc;
import net.minecraft.core.NonNullList;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class BuddyEnchantmentHandler {
    private Map<String, NonNullList<ItemStack>> items = new HashMap<String, NonNullList<ItemStack>>();

    public static final EnchantmentCategory BUDDY_BINDABLE = EnchantmentCategory.create("BUDDY_BINDABLE", BuddyEnchantmentHandler::canBuddyBind);
    public static final EnchantmentCategory BUDDY_MEDAL = EnchantmentCategory.create("BUDDY_MEDAL", i -> (i instanceof MedalItem));
    public static final EnchantmentCategory BUDDY_BINDER = EnchantmentCategory.create("BUDDY_BINDER", i -> (i instanceof BinderItem));
    public static final EnchantmentCategory BUDDY_ARMOR = EnchantmentCategory.create("BUDDY_ARMOR", i -> (i instanceof BuddysteelArmorItem));
    public static final EnchantmentCategory BUDDY_TOOLS = EnchantmentCategory.create("BUDDY_TOOLS", i -> (i instanceof BuddysteelAxeItem || i instanceof  BuddysteelHoeItem || i instanceof BuddysteelPickaxeItem || i instanceof BuddysteelShovelItem));
    public static final EnchantmentCategory BUDDY_WEAPONS = EnchantmentCategory.create("BUDDY_WEAPONS", i -> (i instanceof BuddysteelAxeItem || i instanceof  BuddysteelSwordItem));
    public static final EnchantmentCategory YANNEL_ARMOR = EnchantmentCategory.create("YANNEL_ARMOR", i -> (i instanceof YannelArmorItem || i instanceof YannelElytraItem));

    @SubscribeEvent
    public void drop(LivingDropsEvent event) {
        if (event.getEntityLiving() instanceof Player) {
            Player player = (Player) event.getEntityLiving();
            boolean empty = true;
            NonNullList<ItemStack> itemsToSave = NonNullList.create();
            for (ItemEntity i: event.getDrops()) {
                double rand = Math.random();
                switch(EnchantmentHelper.getItemEnchantmentLevel(BuddycardsMisc.BUDDY_BINDING.get(), i.getItem())) {
                    case 1:
                        if (rand < .3)
                            break;
                    case 2:
                        if (rand < .2)
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
        //if (ModList.get().isLoaded("farmersdelight") && item instanceof BuddysteelFoodKnifeItem)
        //    return true;
        return item.equals(BuddycardsItems.ZYLEX_RING.get()) || item.equals(BuddycardsItems.LUMINIS_RING.get());
    }
}
