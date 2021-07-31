package com.wildcard.buddycards.items;

import com.wildcard.buddycards.BuddyCards;
import com.wildcard.buddycards.container.BinderContainer;
import com.wildcard.buddycards.util.EnderBinderSaveData;
import net.minecraft.world.entity.player.Player;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.level.Level;
import net.minecraftforge.fml.network.NetworkHooks;

public class EnderBinderItem extends Item{

    public EnderBinderItem() {
        super(new Item.Properties().tab(BuddyCards.TAB).stacksTo(1));
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level worldIn, Player playerIn, InteractionHand handIn)
    {
        if(playerIn instanceof ServerPlayer) {
            //Open the GUI on server side
            NetworkHooks.openGui((ServerPlayer) playerIn, new SimpleMenuProvider(
                    (id, playerInventory, entity) -> new BinderContainer(id, playerIn.inventory,
                            EnderBinderSaveData.get(((ServerPlayer) playerIn).getLevel()).getOrMakeEnderBinder(playerIn.getUUID()))
                    , playerIn.getItemInHand(handIn).getHoverName()));
        }
        return InteractionResultHolder.success(playerIn.getItemInHand(handIn));
    }
}
