package com.wildcard.buddycards.datagen;

import com.wildcard.buddycards.BuddyCards;
import net.minecraft.data.DataGenerator;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.ItemModelProvider;
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
    }

    /**
     * Makes every model for a card, including all grades for normal and shiny cards
     * @param setNum set number of card to generate models for
     * @param cardNum card number of card to generate models for
     */
    void genCardModels(int setNum, int cardNum) {
        for (int grade = 0; grade <= 5; grade++) {
            final ResourceLocation location;
            if (grade != 0)
                location = new ResourceLocation(BuddyCards.MOD_ID, ModelProvider.ITEM_FOLDER + "/card." + setNum + "." + cardNum + "g" + grade);
            else
                location = new ResourceLocation(BuddyCards.MOD_ID, ModelProvider.ITEM_FOLDER + "/card." + setNum + "." + cardNum);
            ItemModelBuilder card = factory.apply(location)
                    .parent(factory.apply(new ResourceLocation(BuddyCards.MOD_ID, ModelProvider.ITEM_FOLDER + "/card")))
                    .texture("layer0", new ResourceLocation(BuddyCards.MOD_ID,getSetFolder(setNum) + cardNum));
            if (grade != 0)
                generatedModels.put(location, card.texture("layer1", new ResourceLocation(BuddyCards.MOD_ID,"items/grade/" + grade)));
            else
                generatedModels.put(location, card);
        }
        for (int grade = 0; grade <= 5; grade++) {
            final ResourceLocation location;
            if (grade != 0)
                location = new ResourceLocation(BuddyCards.MOD_ID, ModelProvider.ITEM_FOLDER + "/card." + setNum + "." + cardNum + "sg" + grade);
            else
                location = new ResourceLocation(BuddyCards.MOD_ID, ModelProvider.ITEM_FOLDER + "/card." + setNum + "." + cardNum + "s");
            ItemModelBuilder card = factory.apply(location)
                    .parent(factory.apply(new ResourceLocation(BuddyCards.MOD_ID, ModelProvider.ITEM_FOLDER + "/card")))
                    .texture("layer0", new ResourceLocation(BuddyCards.MOD_ID,getSetFolder(setNum) + cardNum));
            if (grade != 0)
                generatedModels.put(location, card.texture("layer1", new ResourceLocation(BuddyCards.MOD_ID, "items/grade/" + grade)));
            else
                generatedModels.put(location, card);
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
            default: return "error";
        }
    }
}
