package com.wildcard.buddycards.items;

import com.wildcard.buddycards.BuddyCards;
import com.wildcard.buddycards.container.BinderContainer;
import com.wildcard.buddycards.inventory.BinderInventory;
import com.wildcard.buddycards.registries.BuddycardsItems;
import com.wildcard.buddycards.registries.BuddycardsMisc;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.entity.player.Player;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.InteractionHand;
import net.minecraft.core.NonNullList;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.level.Level;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.network.NetworkHooks;

public class BinderItem extends Item {
    public BinderItem(int setNumber, String modId) {
        super(new Item.Properties().tab(BuddyCards.TAB).stacksTo(1));
        SET_NUMBER = setNumber;
        SPECIFIC_MOD = modId;
    }

    final int SET_NUMBER;
    final String SPECIFIC_MOD;

    @Override
    public InteractionResultHolder<ItemStack> use(Level worldIn, Player playerIn, InteractionHand handIn)
    {
        ItemStack binder = playerIn.getItemInHand(handIn);
        CompoundTag nbt = binder.getTag();
        if(playerIn instanceof ServerPlayer) {
            //If there is a key, go through and try to lock it
            if (playerIn.getItemInHand(InteractionHand.OFF_HAND).getItem().equals(BuddycardsItems.BUDDYSTEEL_KEY.get())) {
                if (nbt == null || !nbt.contains("locked") || !nbt.getBoolean("locked")) {
                    nbt.putBoolean("locked", true);
                    nbt.putString("player", playerIn.getStringUUID());
                    binder.setTag(nbt);
                    playerIn.displayClientMessage(new TranslatableComponent("item.buddycards.binder.lock"), true);
                } else if (nbt.getString("player").equals(playerIn.getStringUUID())) {
                    nbt.putBoolean("locked", false);
                    nbt.remove("player");
                    binder.setTag(nbt);
                    playerIn.displayClientMessage(new TranslatableComponent("item.buddycards.binder.unlock"), true);
                } else {
                    playerIn.displayClientMessage(new TranslatableComponent("item.buddycards.binder.fail_unlock"), true);
                }
                return InteractionResultHolder.success(playerIn.getItemInHand(handIn));
            } else if (nbt == null || !nbt.contains("locked") || !nbt.getBoolean("locked")) {
                //Find the amount of slots and then open the binder GUI
                int slots = 54;
                switch (EnchantmentHelper.getItemEnchantmentLevel(BuddycardsMisc.EXTRA_PAGE.get(), binder)) {
                    case 3:
                        slots += 24;
                    case 2:
                        slots += 24;
                    case 1:
                        slots += 18;
                }
                int finalSlots = slots;
                NetworkHooks.openGui((ServerPlayer) playerIn, new SimpleMenuProvider(
                        (id, playerInventory, entity) -> new BinderContainer(id, playerIn.inventory, new BinderInventory(finalSlots, binder))
                        , playerIn.getItemInHand(handIn).getHoverName()));
            } else {
                playerIn.displayClientMessage(new TranslatableComponent("item.buddycards.binder.lock"), true);
            }
        }
        return InteractionResultHolder.success(binder);
    }

    @Override
    public void fillItemCategory(CreativeModeTab group, NonNullList<ItemStack> items) {
        if(!ModList.get().isLoaded(SPECIFIC_MOD))
            return;
        super.fillItemCategory(group, items);
    }

    public boolean isEnchantable(ItemStack stack) {
        return stack.getCount() == 1;
    }

    public int getEnchantmentValue() {
        return 1;
    }
}
