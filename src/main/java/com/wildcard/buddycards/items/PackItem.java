package com.wildcard.buddycards.items;

import com.wildcard.buddycards.BuddyCards;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Rarity;
import net.minecraft.loot.LootContext;
import net.minecraft.loot.LootParameterSets;
import net.minecraft.loot.LootTable;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.items.ItemHandlerHelper;

import java.util.List;

public class PackItem extends Item {
    public PackItem(int setNumber) {
        super(new Item.Properties().tab(BuddyCards.TAB).stacksTo(16));
        SET_NUMBER = setNumber;
    }

    final int SET_NUMBER;

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
        //Prematurely delete the pack item so the card items can go in the same slot
        playerIn.getItemInHand(handIn).shrink(1);
        if(worldIn instanceof ServerWorld) {
            //Generate the associated loot table with the pack and give the cards to the player on server side
            ServerWorld server = (ServerWorld) worldIn;
            LootContext.Builder builder = (new LootContext.Builder(server).withRandom(worldIn.random));
            ResourceLocation resourcelocation;
            if(SET_NUMBER == 0)
                resourcelocation = new ResourceLocation(BuddyCards.MOD_ID, "item/packs/mystery");
            else
                resourcelocation = new ResourceLocation(BuddyCards.MOD_ID, "item/packs/" + SET_NUMBER);
            LootTable loottable = server.getServer().getLootTables().get(resourcelocation);
            List<ItemStack> cards = loottable.getRandomItems(builder.create(LootParameterSets.EMPTY));
            cards.forEach((card) -> ItemHandlerHelper.giveItemToPlayer(playerIn, card));
            return ActionResult.consume(playerIn.getItemInHand(handIn));
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

}
