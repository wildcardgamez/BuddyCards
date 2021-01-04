package com.wildcard.buddycards.items;

import com.wildcard.buddycards.BuddyCards;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Rarity;
import net.minecraft.loot.LootContext;
import net.minecraft.loot.LootParameterSets;
import net.minecraft.loot.LootTable;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.items.ItemHandlerHelper;

import java.util.List;

public class PackItem extends Item {
    public PackItem(int setNumber) {
        super(new Item.Properties().group(BuddyCards.TAB).maxStackSize(16));
        SET_NUMBER = setNumber;
    }

    final int SET_NUMBER;

    @Override
    public void addInformation(ItemStack stack, World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        //Set name and explanation of what the pack is
        tooltip.add(new TranslationTextComponent("item.buddycards.set." + SET_NUMBER));
        tooltip.add(new TranslationTextComponent("item.buddycards.contains"));
    }

    @Override
    public Rarity getRarity(ItemStack stack) {
        //All the packs are rare items (Light blue item name)
        return Rarity.RARE;
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
        //Prematurely delete the pack item so the card items can go in the same slot
        playerIn.getHeldItem(handIn).shrink(1);
        if(worldIn instanceof ServerWorld) {
            //Generate the associated loot table with the pack and give the cards to the player on server side
            ServerWorld server = (ServerWorld) worldIn;
            LootContext.Builder builder = (new LootContext.Builder(server).withRandom(worldIn.rand));
            ResourceLocation resourcelocation = new ResourceLocation(BuddyCards.MOD_ID, "item/packs/" + SET_NUMBER);
            LootTable loottable = server.getServer().getLootTableManager().getLootTableFromLocation(resourcelocation);
            List<ItemStack> cards = loottable.generate(builder.build(LootParameterSets.EMPTY));
            cards.forEach((card) -> ItemHandlerHelper.giveItemToPlayer(playerIn, card));
            return ActionResult.resultConsume(playerIn.getHeldItem(handIn));
        }
        return ActionResult.resultSuccess(playerIn.getHeldItem(handIn));
    }
}
