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
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.NonNullList;
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
        if(playerIn instanceof ServerPlayerEntity) {
            //Open the GUI on server side
            int slots = 54;
            switch (EnchantmentHelper.getItemEnchantmentLevel(RegistryHandler.EXTRA_PAGE.get(), playerIn.getItemInHand(handIn))) {
                case 3:
                    slots += 24;
                case 2:
                    slots += 24;
                case 1:
                    slots += 18;
            }
            int finalSlots = slots;
            NetworkHooks.openGui((ServerPlayerEntity) playerIn, new SimpleNamedContainerProvider(
                    (id, playerInventory, entity) -> new BinderContainer(id, playerIn.inventory, new BinderInventory(finalSlots, playerIn.getItemInHand(handIn)))
                    , playerIn.getItemInHand(handIn).getDisplayName()));
        }
        return ActionResult.success(playerIn.getItemInHand(handIn));
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

    @Override
    public boolean isEnchantable(ItemStack stack) {
        return stack.getCount() == 1;
    }

    @Override
    public int getEnchantmentValue() {
        return 1;
    }
}
