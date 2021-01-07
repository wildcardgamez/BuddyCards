package com.wildcard.buddycards.datagen;

import com.wildcard.buddycards.BuddyCards;
import com.wildcard.buddycards.util.RegistryHandler;
import corgiaoc.byg.core.BYGItems;
import net.minecraft.data.*;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.tags.ITag;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.crafting.ConditionalRecipe;
import net.minecraftforge.common.crafting.conditions.ModLoadedCondition;

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
        registerModSpecificCardDisplayRecipe(consumer, BYGItems.ASPEN_PLANKS, RegistryHandler.ASPEN_CARD_DISPLAY_ITEM.get());

    }

    public static IFinishedRecipe registerCardDisplayRecipe(Consumer<IFinishedRecipe> consumer, Item planks, Item cardDisplay) {
        ShapedRecipeBuilder recipe = ShapedRecipeBuilder.shapedRecipe(cardDisplay);
        recipe.patternLine("###")
                .patternLine(" X ")
                .patternLine("###")
                .key('#', Items.STICK)
                .key('X', planks)
                .addCriterion("has_recipe", hasItem(planks))
                .build(consumer);

        return (IFinishedRecipe) recipe;
    }

    private static void registerModSpecificCardDisplayRecipe(Consumer<IFinishedRecipe> consumer, Item planks, Item cardDisplay) {
        IFinishedRecipe recipe = RecipeGen.registerCardDisplayRecipe(consumer, planks, cardDisplay);
        new ConditionalRecipe.Builder()
                .addRecipe(recipe)
                .addCondition(new ModLoadedCondition("byg"))
                .build(consumer, new ResourceLocation(BuddyCards.MOD_ID, recipe.toString()));
    }
}
