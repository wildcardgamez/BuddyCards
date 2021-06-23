package com.wildcard.buddycards.items;

import com.wildcard.buddycards.BuddyCards;
import com.wildcard.buddycards.registries.BuddycardsItems;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Rarity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
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
    public void appendHoverText(ItemStack stack, World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        //Set name and explanation of what the pack is
        tooltip.add(new TranslationTextComponent("item.buddycards.set." + SET_NUMBER));
        tooltip.add(new TranslationTextComponent("item.buddycards.contains." + SET_NUMBER));
    }

    @Override
    public Rarity getRarity(ItemStack stack) {
        //All the packs are rare items (Light blue item name)
        return Rarity.RARE;
    }

    @Override
    public ActionResult<ItemStack> use(World worldIn, PlayerEntity playerIn, Hand handIn) {
        if(worldIn instanceof ServerWorld) {
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
                if (SET_NUMBER == 0)
                    card = new ItemStack(getRandomCardOfRarity(BuddycardsItems.ALL_CARDS, rarity));
                else
                    card = new ItemStack(getRandomCardOfRarity(BuddycardsItems.SETS.get(SET_NUMBER).CARDS, rarity));
                if (i >= CARDS - SHINY_CARDS) {
                    CompoundNBT nbt = new CompoundNBT();
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
            return ActionResult.consume(playerIn.getItemInHand(handIn));
        }
        return ActionResult.success(playerIn.getItemInHand(handIn));
    }

    @Override
    public void fillItemCategory(ItemGroup group, NonNullList<ItemStack> items) {
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
