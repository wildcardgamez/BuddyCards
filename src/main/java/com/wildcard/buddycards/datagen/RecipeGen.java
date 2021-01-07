package com.wildcard.buddycards.datagen;

import com.wildcard.buddycards.util.RegistryHandler;
import net.minecraft.data.*;
import net.minecraft.item.Item;
import net.minecraft.item.Items;

import java.util.function.Consumer;

public class RecipeGen extends RecipeProvider {
    public RecipeGen(DataGenerator generatorIn) {
        super(generatorIn);
    }

    @Override
    protected void registerRecipes(Consumer<IFinishedRecipe> consumer) {
        registerCardDisplayRecipe(consumer, Items.OAK_PLANKS, RegistryHandler.OAK_CARD_DISPLAY_ITEM.get());
        registerCardDisplayRecipe(consumer, Items.SPRUCE_PLANKS, RegistryHandler.SPRUCE_CARD_DISPLAY_ITEM.get());
        registerCardDisplayRecipe(consumer, Items.BIRCH_PLANKS, RegistryHandler.BIRCH_CARD_DISPLAY_ITEM.get());
        registerCardDisplayRecipe(consumer, Items.JUNGLE_PLANKS, RegistryHandler.JUNGLE_CARD_DISPLAY_ITEM.get());
        registerCardDisplayRecipe(consumer, Items.ACACIA_PLANKS, RegistryHandler.ACACIA_CARD_DISPLAY_ITEM.get());
        registerCardDisplayRecipe(consumer, Items.DARK_OAK_PLANKS, RegistryHandler.DARK_OAK_CARD_DISPLAY_ITEM.get());
        registerCardDisplayRecipe(consumer, Items.CRIMSON_PLANKS, RegistryHandler.CRIMSON_CARD_DISPLAY_ITEM.get());
        registerCardDisplayRecipe(consumer, Items.WARPED_PLANKS, RegistryHandler.WARPED_CARD_DISPLAY_ITEM.get());
        registerCardDisplayRecipe(consumer, Items.WARPED_PLANKS, RegistryHandler.WARPED_CARD_DISPLAY_ITEM.get());
    }

    private void registerCardDisplayRecipe(Consumer<IFinishedRecipe> consumer, Item planks, Item cardDisplay) {
        ShapedRecipeBuilder.shapedRecipe(cardDisplay)
                .patternLine("###")
                .patternLine(" X ")
                .patternLine("###")
                .key('#', Items.STICK)
                .key('X', planks)
                .addCriterion("has_recipe", hasItem(planks))
                .build(consumer);
    }
}
