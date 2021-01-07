package com.wildcard.buddycards.util;

import com.wildcard.buddycards.BuddyCards;
import com.wildcard.buddycards.blocks.CardDisplayBlock;
import com.wildcard.buddycards.blocks.CardDisplayTile;
import com.wildcard.buddycards.client.renderer.CardDisplayTileRenderer;
import com.wildcard.buddycards.container.BinderContainer;
import com.wildcard.buddycards.integration.CuriosIntegration;
import com.wildcard.buddycards.items.BinderItem;
import com.wildcard.buddycards.items.CardItem;
import com.wildcard.buddycards.items.MedalItem;
import com.wildcard.buddycards.items.PackItem;
import com.wildcard.buddycards.screen.BinderScreen;
import net.minecraft.block.Block;
import net.minecraft.client.gui.ScreenManager;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class RegistryHandler {

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, BuddyCards.MOD_ID);
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, BuddyCards.MOD_ID);
    public static final DeferredRegister<ContainerType<?>> CONTAINERS = DeferredRegister.create(ForgeRegistries.CONTAINERS, BuddyCards.MOD_ID);
    public static final DeferredRegister<TileEntityType<?>> TILE_ENTITIES = DeferredRegister.create(ForgeRegistries.TILE_ENTITIES, BuddyCards.MOD_ID);

    public static void init() {
        ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());
        BLOCKS.register(FMLJavaModLoadingContext.get().getModEventBus());
        CONTAINERS.register(FMLJavaModLoadingContext.get().getModEventBus());
        TILE_ENTITIES.register(FMLJavaModLoadingContext.get().getModEventBus());

        if (ModList.get().isLoaded("curios"))
            CuriosIntegration.Imc();
    }

    public static void clientSetup(FMLClientSetupEvent event) {
        event.enqueueWork(() -> ScreenManager.registerFactory(BINDER_CONTAINER.get(), BinderScreen::new));
        ClientRegistry.bindTileEntityRenderer(CARD_DISPLAY_TILE.get(), CardDisplayTileRenderer::new);
    }

    //Packs
    public static final RegistryObject<Item> PACK_BASE = ITEMS.register("pack.1", () -> new PackItem(1));
    public static final RegistryObject<Item> PACK_NETHER = ITEMS.register("pack.2", () -> new PackItem(2));
    public static final RegistryObject<Item> PACK_END = ITEMS.register("pack.3", () -> new PackItem(3));
    public static final RegistryObject<Item> PACK_BYG = ITEMS.register("pack.4", () -> new PackItem(4));

    //Binder Container
    public static final RegistryObject<ContainerType<BinderContainer>> BINDER_CONTAINER = CONTAINERS.register("binder",
            () -> new ContainerType<>((BinderContainer::new)));

    //Binders
    public static final RegistryObject<Item> BINDER_BASE = ITEMS.register("binder.1", () -> new BinderItem(1));
    public static final RegistryObject<Item> BINDER_NETHER = ITEMS.register("binder.2", () -> new BinderItem(2));
    public static final RegistryObject<Item> BINDER_END = ITEMS.register("binder.3", () -> new BinderItem(3));
    public static final RegistryObject<Item> BINDER_BYG = ITEMS.register("binder.4", () -> new BinderItem(4));

    //Medals
    public static final RegistryObject<Item> MEDAL_BASE = ITEMS.register("medal.1", () -> new MedalItem(1));
    public static final RegistryObject<Item> MEDAL_NETHER = ITEMS.register("medal.2", () -> new MedalItem(2));
    public static final RegistryObject<Item> MEDAL_END = ITEMS.register("medal.3", () -> new MedalItem(3));
    public static final RegistryObject<Item> MEDAL_BYG = ITEMS.register("medal.4", () -> new MedalItem(4));

    //Cards
    public static void cardItemCreation() {
        //Create all base set cards
        for(int i = 1; i <= 27; i++) {
            final int num = i;
            ITEMS.register("card.1." + num, () -> new CardItem(1, num, false));
            ITEMS.register("card.1." + num + "s", () -> new CardItem(1, num, true));
        }
        //Create all nether set cards
        for(int i = 1; i <= 27; i++) {
            final int num = i;
            ITEMS.register("card.2." + num, () -> new CardItem(2, num, false));
            ITEMS.register("card.2." + num + "s", () -> new CardItem(2, num, true));
        }
        //Create all end set cards
        for(int i = 1; i <= 27; i++) {
            final int num = i;
            ITEMS.register("card.3." + num, () -> new CardItem(3, num, false));
            ITEMS.register("card.3." + num + "s", () -> new CardItem(3, num, true));
        }
        //Create all byg set cards
        for(int i = 1; i <= 27; i++) {
            final int num = i;
            RegistryHandler.ITEMS.register("card.4." + num, () -> new CardItem(4, num, false));
            RegistryHandler.ITEMS.register("card.4." + num + "s", () -> new CardItem(4, num, true));
        }
    }

    //Card Display Blocks
    public static final RegistryObject<Block> OAK_CARD_DISPLAY = BLOCKS.register("oak_card_display", CardDisplayBlock::new);
    public static final RegistryObject<Block> SPRUCE_CARD_DISPLAY = BLOCKS.register("spruce_card_display", CardDisplayBlock::new);
    public static final RegistryObject<Block> BIRCH_CARD_DISPLAY = BLOCKS.register("birch_card_display", CardDisplayBlock::new);
    public static final RegistryObject<Block> JUNGLE_CARD_DISPLAY = BLOCKS.register("jungle_card_display", CardDisplayBlock::new);
    public static final RegistryObject<Block> ACACIA_CARD_DISPLAY = BLOCKS.register("acacia_card_display", CardDisplayBlock::new);
    public static final RegistryObject<Block> DARK_OAK_CARD_DISPLAY = BLOCKS.register("dark_oak_card_display", CardDisplayBlock::new);
    public static final RegistryObject<Block> CRIMSON_CARD_DISPLAY = BLOCKS.register("crimson_card_display", CardDisplayBlock::new);
    public static final RegistryObject<Block> WARPED_CARD_DISPLAY = BLOCKS.register("warped_card_display", CardDisplayBlock::new);
    //Byg Card Display Blocks
    public static final RegistryObject<Block> ASPEN_CARD_DISPLAY = BLOCKS.register("aspen_card_display", () -> new CardDisplayBlock("byg"));

    //Card Display Items
    public static final RegistryObject<BlockItem> OAK_CARD_DISPLAY_ITEM = ITEMS.register("oak_card_display", () -> new BlockItem(OAK_CARD_DISPLAY.get(), new Item.Properties().group(BuddyCards.TAB)));
    public static final RegistryObject<BlockItem> SPRUCE_CARD_DISPLAY_ITEM = ITEMS.register("spruce_card_display", () -> new BlockItem(SPRUCE_CARD_DISPLAY.get(), new Item.Properties().group(BuddyCards.TAB)));
    public static final RegistryObject<BlockItem> BIRCH_CARD_DISPLAY_ITEM = ITEMS.register("birch_card_display", () -> new BlockItem(BIRCH_CARD_DISPLAY.get(), new Item.Properties().group(BuddyCards.TAB)));
    public static final RegistryObject<BlockItem> JUNGLE_CARD_DISPLAY_ITEM = ITEMS.register("jungle_card_display", () -> new BlockItem(JUNGLE_CARD_DISPLAY.get(), new Item.Properties().group(BuddyCards.TAB)));
    public static final RegistryObject<BlockItem> ACACIA_CARD_DISPLAY_ITEM = ITEMS.register("acacia_card_display", () -> new BlockItem(ACACIA_CARD_DISPLAY.get(), new Item.Properties().group(BuddyCards.TAB)));
    public static final RegistryObject<BlockItem> DARK_OAK_CARD_DISPLAY_ITEM = ITEMS.register("dark_oak_card_display", () -> new BlockItem(DARK_OAK_CARD_DISPLAY.get(), new Item.Properties().group(BuddyCards.TAB)));
    public static final RegistryObject<BlockItem> CRIMSON_CARD_DISPLAY_ITEM = ITEMS.register("crimson_card_display", () -> new BlockItem(CRIMSON_CARD_DISPLAY.get(), new Item.Properties().group(BuddyCards.TAB)));
    public static final RegistryObject<BlockItem> WARPED_CARD_DISPLAY_ITEM = ITEMS.register("warped_card_display", () -> new BlockItem(WARPED_CARD_DISPLAY.get(), new Item.Properties().group(BuddyCards.TAB)));
    //Card Display Items
    public static final RegistryObject<BlockItem> ASPEN_CARD_DISPLAY_ITEM = ITEMS.register("aspen_card_display", () -> new BlockItem(ASPEN_CARD_DISPLAY.get(), new Item.Properties().group(BuddyCards.TAB)));

    public static final RegistryObject<TileEntityType<CardDisplayTile>> CARD_DISPLAY_TILE = TILE_ENTITIES.register("card_display",
            () -> TileEntityType.Builder.create(CardDisplayTile::new, OAK_CARD_DISPLAY.get(), SPRUCE_CARD_DISPLAY.get(),
                    BIRCH_CARD_DISPLAY.get(), JUNGLE_CARD_DISPLAY.get(), ACACIA_CARD_DISPLAY.get(), DARK_OAK_CARD_DISPLAY.get(),
                    CRIMSON_CARD_DISPLAY.get(), WARPED_CARD_DISPLAY.get(), ASPEN_CARD_DISPLAY.get()).build(null));

}
