package com.wildcard.buddycards.datagen;

import com.wildcard.buddycards.BuddyCards;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.client.model.generators.ModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

public class CardModelGen extends ItemModelProvider {

    public CardModelGen(DataGenerator generator, String modid, ExistingFileHelper existingFileHelper) {
        super(generator, modid, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        for (int i = 1; i <= 5; i++) {
            for (int j = 1; j <= 27; j++) {
                genCardModels(i, j);
            }
        }
        for (int i = 6; i <= 7; i++) {
            for (int j = 1; j <= 18; j++) {
                genCardModels(i, j);
            }
        }
        for (int i = 1; i<= 6; i++) {
            genGummyCardModels(i);
        }
        for (int i = 1; i <= 3; i++) {
            genCardModels(8, i);
        }
    }

    /**
     * Makes every model for a card, including all grades for normal and shiny cards
     * @param setNum set number of card to generate models for
     * @param cardNum card number of card to generate models for
     */
    void genCardModels(int setNum, int cardNum) {
        if(setNum > 0) {
            final ResourceLocation location1 = new ResourceLocation(BuddyCards.MOD_ID, ModelProvider.ITEM_FOLDER + "/card." + setNum + "." + cardNum);
            ItemModelBuilder card = factory.apply(location1)
                    .parent(factory.apply(new ResourceLocation(BuddyCards.MOD_ID, ModelProvider.ITEM_FOLDER + "/card")))
                    .texture("layer0", new ResourceLocation(BuddyCards.MOD_ID, getSetFolder(setNum) + cardNum));
            card.override().predicate(new ResourceLocation("grade"), 1).model(genGradedCardModel(setNum, cardNum, 1));
            card.override().predicate(new ResourceLocation("grade"), 2).model(genGradedCardModel(setNum, cardNum, 2));
            card.override().predicate(new ResourceLocation("grade"), 3).model(genGradedCardModel(setNum, cardNum, 3));
            card.override().predicate(new ResourceLocation("grade"), 4).model(genGradedCardModel(setNum, cardNum, 4));
            card.override().predicate(new ResourceLocation("grade"), 5).model(genGradedCardModel(setNum, cardNum, 5));
            generatedModels.put(location1, card);
        }
    }

    /**
     * Finds the location of cards in a given set
     * @param set set number in
     * @return location of cards from the set
     */
    String getSetFolder(int set) {
        switch(set) {
            case 1: return "items/base_set/";
            case 2: return "items/nether_set/";
            case 3: return "items/end_set/";
            case 4: return "items/byg_set/";
            case 5: return "items/create_set/";
            case 6: return "items/aquaculture_set/";
            case 7: return "items/fd_set/";
            case 8: return "items/promo_set/";
            default: return "error";
        }
    }

    /**
     * Creates a single graded card of specific card and grade
     * @param setNum set number in
     * @param cardNum card number in
     * @param grade grade number in
     * @return The created model
     */
    ModelFile genGradedCardModel(int setNum, int cardNum, int grade) {
        final ResourceLocation location;
        location = new ResourceLocation(BuddyCards.MOD_ID, ModelProvider.ITEM_FOLDER + "/card." + setNum + "." + cardNum + "g" + grade);
        ItemModelBuilder card = factory.apply(location)
                .parent(factory.apply(new ResourceLocation(BuddyCards.MOD_ID, ModelProvider.ITEM_FOLDER + "/card")))
                .texture("layer0", new ResourceLocation(BuddyCards.MOD_ID, getSetFolder(setNum) + cardNum));
        generatedModels.put(location, card.texture("layer1", new ResourceLocation(BuddyCards.MOD_ID,"items/grade/" + grade)));
        return card;
    }

    /**
     * Makes every model for a gummy card, including all grades
     * @param cardNum card number of card to generate models for
     */
    void genGummyCardModels(int cardNum) {
        final ResourceLocation location = new ResourceLocation(BuddyCards.MOD_ID, ModelProvider.ITEM_FOLDER + "/card.7.gummy" + cardNum);
        ItemModelBuilder card = factory.apply(location)
                .parent(factory.apply(new ResourceLocation(BuddyCards.MOD_ID, ModelProvider.ITEM_FOLDER + "/card")))
                .texture("layer0", new ResourceLocation(BuddyCards.MOD_ID, "items/fd_set/gummy" + cardNum));
        generatedModels.put(location, card);
    }
}
