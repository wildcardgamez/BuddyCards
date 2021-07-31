package com.wildcard.buddycards.items;

import com.wildcard.buddycards.BuddyCards;
import com.wildcard.buddycards.registries.BuddycardsItems;
import com.wildcard.buddycards.util.ConfigManager;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.entity.player.Player;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.InteractionHand;
import net.minecraft.core.NonNullList;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.level.Level;
import net.minecraft.server.level.ServerLevel;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.items.ItemHandlerHelper;

import java.util.ArrayList;
import java.util.List;

public class PackItem extends Item {
    public PackItem(int setNumber, String modId, int cards, int shinyCards) {
        super(new Item.Properties().tab(BuddyCards.TAB).stacksTo(16));
        SET_NUMBER = setNumber;
        SPECIFIC_MOD = modId;
        CARDS = cards;
        SHINY_CARDS = shinyCards;
    }

    final int SET_NUMBER;
    final String SPECIFIC_MOD;
    final int CARDS;
    final int SHINY_CARDS;

    @Override
    public void appendHoverText(ItemStack stack, Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
        //Set name and explanation of what the pack is
        tooltip.add(new TranslatableComponent("item.buddycards.set." + SET_NUMBER));
        tooltip.add(new TranslatableComponent("item.buddycards.contains." + SET_NUMBER));
    }

    @Override
    public Rarity getRarity(ItemStack stack) {
        //All the packs are rare items (Light blue item name)
        return Rarity.RARE;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level worldIn, Player playerIn, InteractionHand handIn) {
        if(worldIn instanceof ServerLevel) {
            //Prematurely delete the pack item so the card items can go in the same slot
            playerIn.getItemInHand(handIn).shrink(1);
            //For each card roll the rarity, and get a card from the list that matches
            NonNullList<ItemStack> cards = NonNullList.create();
            for (int i = 0; i < CARDS; i++) {
                float rand = worldIn.random.nextFloat();
                Rarity rarity;
                if (rand < .5)
                    rarity = Rarity.COMMON;
                else if (rand < .8)
                    rarity = Rarity.UNCOMMON;
                else if (rand < .95)
                    rarity = Rarity.RARE;
                else
                    rarity = Rarity.EPIC;
                ItemStack card;
                if (SET_NUMBER == 0) {
                    if (ConfigManager.loadedMysteryCardsOnly.get())
                        card = new ItemStack(getRandomCardOfRarity(BuddycardsItems.LOADED_CARDS, rarity));
                    else
                        card = new ItemStack(getRandomCardOfRarity(BuddycardsItems.ALL_CARDS, rarity));
                }
                else
                    card = new ItemStack(getRandomCardOfRarity(BuddycardsItems.SETS.get(SET_NUMBER).CARDS, rarity));
                if (i >= CARDS - SHINY_CARDS) {
                    CompoundTag nbt = new CompoundTag();
                    nbt.putBoolean("foil", true);
                    card.setTag(nbt);
                }
                cards.add(card);
            }
            //If set 7, include a gummycard
            if (SET_NUMBER == 7)
                cards.add(new ItemStack(getRandomGummyCard(BuddycardsItems.SETS.get(SET_NUMBER).CARDS)));
            cards.forEach((card) -> {
                //Give each card
                ItemHandlerHelper.giveItemToPlayer(playerIn, card);
            });
            return InteractionResultHolder.consume(playerIn.getItemInHand(handIn));
        }
        return InteractionResultHolder.success(playerIn.getItemInHand(handIn));
    }

    @Override
    public void fillItemCategory(CreativeModeTab group, NonNullList<ItemStack> items) {
        if(!ModList.get().isLoaded(SPECIFIC_MOD))
            return;
        super.fillItemCategory(group, items);
    }

    public static CardItem getRandomCardOfRarity(ArrayList<RegistryObject<CardItem>> cards, Rarity rarity) {
        CardItem card = cards.get((int)(Math.random() * cards.size())).get();
        while (card.getRarity() != rarity || card instanceof GummyCardItem || card.getRegistryName().toString().endsWith("s")) {
            card = cards.get((int)(Math.random() * cards.size())).get();
        }
        return card;
    }

    public static CardItem getRandomGummyCard(ArrayList<RegistryObject<CardItem>> cards) {
        CardItem card = cards.get((int)(Math.random() * cards.size())).get();
        while (!(card instanceof GummyCardItem)) {
            card = cards.get((int)(Math.random() * cards.size())).get();
        }
        return card;
    }

}
