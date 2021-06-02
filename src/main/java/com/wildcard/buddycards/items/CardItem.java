package com.wildcard.buddycards.items;

import com.wildcard.buddycards.BuddyCards;
import com.wildcard.buddycards.registries.BuddycardsItems;
import com.wildcard.buddycards.util.ConfigManager;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.items.ItemHandlerHelper;
import top.theillusivec4.curios.api.CuriosApi;

import java.util.List;

public class CardItem extends Item {
    /**
     * Sets up a card number for a set
     * @param setNumber set number for card
     * @param cardNumber card number for card
     * @param isShiny is it a shiny card
     */
    public CardItem(int setNumber, int cardNumber, boolean isShiny, Rarity rarity, String modId) {
        super(new Item.Properties().tab(BuddyCards.CARDS_TAB));
        SET_NUMBER = setNumber;
        CARD_NUMBER = cardNumber;
        SHINY = isShiny;
        RARITY = rarity;
        MOD_ID = modId;
    }

    public final int SET_NUMBER;
    public final int CARD_NUMBER;
    public final boolean SHINY;
    private final Rarity RARITY;
    private final String MOD_ID;

    @Override
    public void appendHoverText(ItemStack stack, World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        //Add the card description
        tooltip.add(new TranslationTextComponent("item.buddycards.card." + SET_NUMBER + "." + CARD_NUMBER + ".tooltip"));
        TranslationTextComponent cn = new TranslationTextComponent("item.buddycards.number_separator");
        cn.append("" + CARD_NUMBER);
        //Add the star to the prefix when it's a shiny variant
        if (SHINY)
            cn.append(new TranslationTextComponent("item.buddycards.shiny_symbol"));
        //Add the card info (SetName - Card# Shiny symbol)
        tooltip.add(new TranslationTextComponent("item.buddycards.set." + SET_NUMBER).append(cn));
        //Add the grading if the card is graded
        if (stack.getTag() != null && stack.getTag().getInt("grade") != 0)
            tooltip.add(new TranslationTextComponent("item.buddycards.grade_info").append(
                    new TranslationTextComponent("item.buddycards.grade_" + stack.getTag().getInt("grade"))));
        if (ConfigManager.challengeMode.get())
            tooltip.add(new TranslationTextComponent("item.buddycards.points_info").append(
                    "" + ((CardItem)stack.getItem()).getPointValue(stack)));
    }

    @Override
    public boolean isFoil(ItemStack stack) {
        //Make shiny cards have enchant glow, and non-shiny ones not
        return SHINY;
    }

    @Override
    public Rarity getRarity(ItemStack stack) {
        return RARITY;
    }

    @Override
    public void fillItemCategory(ItemGroup group, NonNullList<ItemStack> items) {
        //Only show cards in the creative menu when the respective mod is loaded
        if(!ModList.get().isLoaded(MOD_ID))
            return;
        super.fillItemCategory(group, items);
    }

    @Override
    public ActionResult<ItemStack> use(World worldIn, PlayerEntity playerIn, Hand handIn) {
        if (handIn == Hand.MAIN_HAND)
            return tryGrade(BuddycardsItems.GRADING_SLEEVE.get(), worldIn, playerIn, handIn);
        return super.use(worldIn, playerIn, handIn);
    }

    public ActionResult<ItemStack> tryGrade(Item gradingSleeve, World worldIn, PlayerEntity playerIn, Hand handIn) {
        if (playerIn.getItemInHand(Hand.OFF_HAND).getItem().equals(gradingSleeve)) {
            CompoundNBT nbt = playerIn.getItemInHand(handIn).getTag();
            if (nbt == null)
                nbt = new CompoundNBT();
            if (nbt.getInt("grade") == 0) {
                //Take the grading sleeve
                playerIn.getItemInHand(Hand.OFF_HAND).shrink(1);
                //Get a grade using maths for rarity
                int i = (int) (Math.random() * 500) + 1;
                int grade;
                //Count how many extra rolls the player has based on effects and ring
                int k = 0;
                if (ModList.get().isLoaded("curios") &&
                        CuriosApi.getCuriosHelper().findEquippedCurio(BuddycardsItems.ZYLEX_RING.get(), playerIn).isPresent() &&
                        CuriosApi.getCuriosHelper().findEquippedCurio(BuddycardsItems.ZYLEX_RING.get(), playerIn).get().right.getItem().equals(BuddycardsItems.ZYLEX_RING.get()))
                    k++;
                if (playerIn.hasEffect(RegistryHandler.GRADING_LUCK.get()))
                    k += playerIn.getEffect(RegistryHandler.GRADING_LUCK.get()).getAmplifier();
                //If they have extra rolls, reroll each roll under 400
                if (k > 0) {
                    for (int j = 0; j <= k && i < 400; j++) {
                        i = (int) (Math.random() * 500) + 1;
                    }
                }
                if (i < 200)
                    grade = 1;
                else if (i < 360)
                    grade = 2;
                else if (i < 450)
                    grade = 3;
                else if (i < 500)
                    grade = 4;
                else
                    grade = 5;
                //Make new graded card and give it to the player, remove old card
                nbt.putInt("grade", grade);
                ItemStack card = new ItemStack(playerIn.getItemInHand(handIn).getItem(), 1);
                card.setTag(nbt);
                playerIn.getItemInHand(handIn).shrink(1);
                ItemHandlerHelper.giveItemToPlayer(playerIn, card);

                return ActionResult.success(playerIn.getItemInHand(handIn));
            }
        }
        return super.use(worldIn, playerIn, handIn);
    }

    public int getPointValue(ItemStack card) {
        double points = 0;
        if(card.getRarity() == RARITY.COMMON)
            points = ConfigManager.challengePointsCommon.get();
        else if(card.getRarity() == RARITY.UNCOMMON)
            points = ConfigManager.challengePointsUncommon.get();
        else if(card.getRarity() == RARITY.RARE)
            points = ConfigManager.challengePointsRare.get();
        else
            points = ConfigManager.challengePointsEpic.get();
        if(SET_NUMBER == 1)
            points *= ConfigManager.challengeSet1Mult.get();
        else if(SET_NUMBER == 2)
            points *= ConfigManager.challengeSet2Mult.get();
        else if(SET_NUMBER == 3)
            points *= ConfigManager.challengeSet3Mult.get();
        else if(SET_NUMBER == 4)
            points *= ConfigManager.challengeSet4Mult.get();
        else if(SET_NUMBER == 5)
            points *= ConfigManager.challengeSet5Mult.get();
        else if(SET_NUMBER == 6)
            points *= ConfigManager.challengeSet6Mult.get();
        else if(SET_NUMBER == 7)
            points *= ConfigManager.challengeSet7Mult.get();
        if(card.getTag() != null) {
            int grade = card.getTag().getInt("grade");
            if(grade == 1)
                points *= ConfigManager.challengeGrade1Mult.get();
            else if(grade == 2)
                points *= ConfigManager.challengeGrade2Mult.get();
            else if(grade == 3)
                points *= ConfigManager.challengeGrade3Mult.get();
            else if(grade == 4)
                points *= ConfigManager.challengeGrade4Mult.get();
            else if(grade == 5)
                points *= ConfigManager.challengeGrade5Mult.get();
        }
        if(SHINY)
            points *= ConfigManager.challengeShinyMult.get();
        return (int) (points + .5);
    }
}
