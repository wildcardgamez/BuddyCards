package com.wildcard.buddycards.items;

import com.wildcard.buddycards.BuddyCards;
import com.wildcard.buddycards.registries.BuddycardsItems;
import com.wildcard.buddycards.registries.BuddycardsMisc;
import com.wildcard.buddycards.util.ConfigManager;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.items.ItemHandlerHelper;
import net.minecraftforge.registries.ForgeRegistries;
import top.theillusivec4.curios.api.CuriosApi;

import java.util.Calendar;
import java.util.List;

public class CardItem extends Item {
    /**
     * Sets up a card for a set
     * @param setNumber set number for card
     * @param cardNumber card number for card
     * @param rarity is the cards rarity
     * @param modId is the id of the mod required to add it in
     */
    public CardItem(int setNumber, int cardNumber, Rarity rarity, String modId) {
        super(new Item.Properties().tab(BuddyCards.CARDS_TAB));
        SET_NUMBER = setNumber;
        CARD_NUMBER = cardNumber;
        RARITY = rarity;
        MOD_ID = modId;
    }
    /**
     * Sets up a card with specific properties for a set
     * @param setNumber set number for card
     * @param cardNumber card number for card
     * @param rarity is the cards rarity
     * @param modId is the id of the mod required to add it in
     */
    public CardItem(int setNumber, int cardNumber, Rarity rarity, String modId, Item.Properties properties) {
        super(properties);
        SET_NUMBER = setNumber;
        CARD_NUMBER = cardNumber;
        RARITY = rarity;
        MOD_ID = modId;
    }

    public final int SET_NUMBER;
    public final int CARD_NUMBER;
    private final Rarity RARITY;
    private final String MOD_ID;

    @Override
    public void appendHoverText(ItemStack stack, Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
        //Add the card description
        tooltip.add(new TranslatableComponent("item.buddycards.card." + SET_NUMBER + "." + CARD_NUMBER + ".tooltip"));
        if(SET_NUMBER == 8) {
            TranslatableComponent cn = new TranslatableComponent("item.buddycards.promo." + CARD_NUMBER);
            if (isFoil(stack))
                cn.append(new TranslatableComponent("item.buddycards.shiny_symbol"));
            tooltip.add(cn);
        }
        else {
            TranslatableComponent cn = new TranslatableComponent("item.buddycards.number_separator");
            cn.append("" + CARD_NUMBER);
            //Add the star to the prefix when it's a shiny variant
            if (isFoil(stack))
                cn.append(new TranslatableComponent("item.buddycards.shiny_symbol"));
            //Add the card info (SetName - Card# Shiny symbol) or (Promo Type - Shiny symbol)

            tooltip.add(new TranslatableComponent("item.buddycards.set." + SET_NUMBER).append(cn));
        }
        //Add the grading if the card is graded
        if (stack.getTag() != null && stack.getTag().getInt("grade") != 0)
            tooltip.add(new TranslatableComponent("item.buddycards.grade_info").append(
                    new TranslatableComponent("item.buddycards.grade_" + stack.getTag().getInt("grade"))));
        if (ConfigManager.challengeMode.get())
            tooltip.add(new TranslatableComponent("item.buddycards.points_info").append(
                    "" + ((CardItem)stack.getItem()).getPointValue(stack)));
        if (stack.getItem().getRegistryName().toString().endsWith("s"))
            tooltip.add(new TranslatableComponent("item.buddycards.foil_warning"));
    }

    @Override
    public boolean isFoil(ItemStack stack) {
        //Make shiny cards have enchant glow
        return stack.hasTag() && stack.getTag().contains("foil") && stack.getTag().getBoolean("foil");
    }

    @Override
    public Rarity getRarity(ItemStack stack) {
        return RARITY;
    }

    public Rarity getRarity() {
        return RARITY;
    }

    @Override
    public void fillItemCategory(CreativeModeTab group, NonNullList<ItemStack> items) {
        //Only show cards in the creative menu when the respective mod is loaded
        if(this.allowdedIn(group)) {
            boolean doFill = false;
            if (SET_NUMBER == 8) {
                Calendar calendar = Calendar.getInstance();
                if (CARD_NUMBER <= 3 && calendar.get(Calendar.MONTH) == Calendar.OCTOBER && calendar.get(Calendar.DATE) >= 30)
                    doFill = true;
            }
            else if (ModList.get().isLoaded(MOD_ID))
                doFill = true;
            if (doFill) {
                ItemStack foil = new ItemStack(this);
                CompoundTag nbt = new CompoundTag();
                nbt.putBoolean("foil", true);
                foil.setTag(nbt);
                items.add(new ItemStack(this));
                items.add(foil);
            }
        }
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level worldIn, Player playerIn, InteractionHand handIn) {
        //If its in the main hand try to grade it (Checks off hand)
        if (handIn == InteractionHand.MAIN_HAND)
            return tryGrade(BuddycardsItems.GRADING_SLEEVE.get(), worldIn, playerIn, handIn);
        return super.use(worldIn, playerIn, handIn);
    }

    public InteractionResultHolder<ItemStack> tryGrade(Item gradingSleeve, Level worldIn, Player playerIn, InteractionHand handIn) {
        if (playerIn.getItemInHand(InteractionHand.OFF_HAND).getItem().equals(gradingSleeve)) {
            CompoundTag nbt = playerIn.getItemInHand(handIn).getTag();
            if (nbt == null)
                nbt = new CompoundTag();
            if (nbt.getInt("grade") == 0) {
                //Take the grading sleeve
                playerIn.getItemInHand(InteractionHand.OFF_HAND).shrink(1);
                //Get a grade using maths for rarity
                int i = (int) (Math.random() * 500) + 1;
                int grade;
                //Count how many extra rolls the player has based on effects and ring
                int k = 0;
                if (ModList.get().isLoaded("curios") &&
                        CuriosApi.getCuriosHelper().findEquippedCurio(BuddycardsItems.ZYLEX_RING.get(), playerIn).isPresent() &&
                        CuriosApi.getCuriosHelper().findEquippedCurio(BuddycardsItems.ZYLEX_RING.get(), playerIn).get().right.getItem().equals(BuddycardsItems.ZYLEX_RING.get()))
                    k++;
                if (playerIn.hasEffect(BuddycardsMisc.GRADING_LUCK.get()))
                    k += playerIn.getEffect(BuddycardsMisc.GRADING_LUCK.get()).getAmplifier();
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

                return InteractionResultHolder.success(playerIn.getItemInHand(handIn));
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
        if(card.hasFoil())
            points *= ConfigManager.challengeShinyMult.get();
        return (int) (points + .5);
    }
}
