package com.wildcard.buddycards.items;

import com.wildcard.buddycards.BuddyCards;
import com.wildcard.buddycards.container.BinderContainer;
import com.wildcard.buddycards.inventory.BinderInventory;
import com.wildcard.buddycards.util.RegistryHandler;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.container.SimpleNamedContainerProvider;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.network.NetworkHooks;

public class BinderItem extends Item {
    public BinderItem(int setNumber) {
        super(new Item.Properties().tab(BuddyCards.TAB).stacksTo(1));
        SET_NUMBER = setNumber;
    }

    final int SET_NUMBER;

    @Override
    public ActionResult<ItemStack> use(World worldIn, PlayerEntity playerIn, Hand handIn)
    {
        ItemStack binder = playerIn.getItemInHand(handIn);
        CompoundNBT nbt = binder.getTag();
        if(playerIn instanceof ServerPlayerEntity) {
            //If there is a key, go through and try to lock it
            if (playerIn.getItemInHand(Hand.OFF_HAND).getItem().equals(RegistryHandler.BUDDYSTEEL_KEY.get())) {
                if (nbt == null || !nbt.contains("locked") || !nbt.getBoolean("locked")) {
                    nbt.putBoolean("locked", true);
                    nbt.putString("player", playerIn.getStringUUID());
                    binder.setTag(nbt);
                    playerIn.displayClientMessage(new TranslationTextComponent("item.buddycards.binder.lock"), true);
                } else if (nbt.getString("player").equals(playerIn.getStringUUID())) {
                    nbt.putBoolean("locked", false);
                    nbt.remove("player");
                    binder.setTag(nbt);
                    playerIn.displayClientMessage(new TranslationTextComponent("item.buddycards.binder.unlock"), true);
                } else {
                    playerIn.displayClientMessage(new TranslationTextComponent("item.buddycards.binder.fail_unlock"), true);
                }
                return ActionResult.success(playerIn.getItemInHand(handIn));
            } else if (nbt == null || !nbt.contains("locked") || !nbt.getBoolean("locked")) {
                //Find the amount of slots and then open the binder GUI
                int slots = 54;
                switch (EnchantmentHelper.getItemEnchantmentLevel(RegistryHandler.EXTRA_PAGE.get(), binder)) {
                    case 3:
                        slots += 24;
                    case 2:
                        slots += 24;
                    case 1:
                        slots += 18;
                }
                int finalSlots = slots;
                NetworkHooks.openGui((ServerPlayerEntity) playerIn, new SimpleNamedContainerProvider(
                        (id, playerInventory, entity) -> new BinderContainer(id, playerIn.inventory, new BinderInventory(finalSlots, binder))
                        , playerIn.getItemInHand(handIn).getHoverName()));
            } else {
                playerIn.displayClientMessage(new TranslationTextComponent("item.buddycards.binder.lock"), true);
            }
        }
        return ActionResult.success(binder);
    }

    @Override
    public void fillItemCategory(ItemGroup group, NonNullList<ItemStack> items) {
        if(SET_NUMBER == 4 && !ModList.get().isLoaded("byg"))
            return;
        else if(SET_NUMBER == 5 && !ModList.get().isLoaded("create"))
            return;
        else if(SET_NUMBER == 6 && !ModList.get().isLoaded("aquaculture"))
            return;
        else if(SET_NUMBER == 7 && !ModList.get().isLoaded("farmersdelight"))
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
