package com.wildcard.buddycards.registries;

import com.wildcard.buddycards.BuddyCards;
import com.wildcard.buddycards.blocks.tiles.BuddysteelVaultTile;
import com.wildcard.buddycards.blocks.tiles.CardDisplayTile;
import com.wildcard.buddycards.blocks.tiles.CardStandTile;
import com.wildcard.buddycards.entities.EnderlingEntity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class BuddycardsEntities {
    public static final DeferredRegister<TileEntityType<?>> TILE_ENTITIES = DeferredRegister.create(ForgeRegistries.TILE_ENTITIES, BuddyCards.MOD_ID);
    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITIES, BuddyCards.MOD_ID);

    public static void init() {
        TILE_ENTITIES.register(FMLJavaModLoadingContext.get().getModEventBus());
        ENTITIES.register(FMLJavaModLoadingContext.get().getModEventBus());
    }

    public static final RegistryObject<TileEntityType<CardDisplayTile>> CARD_DISPLAY_TILE = TILE_ENTITIES.register("card_display",
            () -> TileEntityType.Builder.of(CardDisplayTile::new, BuddycardsBlocks.OAK_CARD_DISPLAY.get(), BuddycardsBlocks.SPRUCE_CARD_DISPLAY.get(),
                    BuddycardsBlocks.BIRCH_CARD_DISPLAY.get(), BuddycardsBlocks.JUNGLE_CARD_DISPLAY.get(), BuddycardsBlocks.ACACIA_CARD_DISPLAY.get(), BuddycardsBlocks.DARK_OAK_CARD_DISPLAY.get(),
                    BuddycardsBlocks.CRIMSON_CARD_DISPLAY.get(), BuddycardsBlocks.WARPED_CARD_DISPLAY.get(), BuddycardsBlocks.ASPEN_CARD_DISPLAY.get(), BuddycardsBlocks.BAOBAB_CARD_DISPLAY.get(),
                    BuddycardsBlocks.BLUE_ENCHANTED_CARD_DISPLAY.get(), BuddycardsBlocks.BULBIS_CARD_DISPLAY.get(), BuddycardsBlocks.CHERRY_CARD_DISPLAY.get(), BuddycardsBlocks.CIKA_CARD_DISPLAY.get(),
                    BuddycardsBlocks.CYPRESS_CARD_DISPLAY.get(), BuddycardsBlocks.EBONY_CARD_DISPLAY.get(), BuddycardsBlocks.EMBUR_CARD_DISPLAY.get(), BuddycardsBlocks.ETHER_CARD_DISPLAY.get(),
                    BuddycardsBlocks.FIR_CARD_DISPLAY.get(), BuddycardsBlocks.GLACIAL_OAK_CARD_DISPLAY.get(), BuddycardsBlocks.GREEN_ENCHANTED_CARD_DISPLAY.get(), BuddycardsBlocks.HOLLY_CARD_DISPLAY.get(),
                    BuddycardsBlocks.JACARANDA_CARD_DISPLAY.get(), BuddycardsBlocks.LAMENT_CARD_DISPLAY.get(), BuddycardsBlocks.MAHOGANY_CARD_DISPLAY.get(), BuddycardsBlocks.MANGROVE_CARD_DISPLAY.get(),
                    BuddycardsBlocks.MAPLE_CARD_DISPLAY.get(), BuddycardsBlocks.NIGHTSHADE_CARD_DISPLAY.get(), BuddycardsBlocks.PALM_CARD_DISPLAY.get(), BuddycardsBlocks.PINE_CARD_DISPLAY.get(),
                    BuddycardsBlocks.RAINBOW_EUCALYPTUS_CARD_DISPLAY.get(), BuddycardsBlocks.REDWOOD_CARD_DISPLAY.get(), BuddycardsBlocks.SKYRIS_CARD_DISPLAY.get(), BuddycardsBlocks.SYTHIAN_CARD_DISPLAY.get(),
                    BuddycardsBlocks.WILLOW_CARD_DISPLAY.get(), BuddycardsBlocks.WITCH_HAZEL_CARD_DISPLAY.get(), BuddycardsBlocks.ZELKOVA_CARD_DISPLAY.get()).build(null));

    public static final RegistryObject<TileEntityType<CardStandTile>> CARD_STAND_TILE = TILE_ENTITIES.register("card_stand",
            () -> TileEntityType.Builder.of(CardStandTile::new, BuddycardsBlocks.CARD_STAND.get()).build(null));

    public static final RegistryObject<TileEntityType<BuddysteelVaultTile>> VAULT_TILE = TILE_ENTITIES.register("buddysteel_vault",
            () -> TileEntityType.Builder.of(BuddysteelVaultTile::new, BuddycardsItems.BASE_SET.VAULT.get(), BuddycardsItems.NETHER_SET.VAULT.get(),
                    BuddycardsItems.END_SET.VAULT.get(), BuddycardsItems.BYG_SET.VAULT.get(), BuddycardsItems.CREATE_SET.VAULT.get(), BuddycardsItems.AQUACULTURE_SET.VAULT.get(),
                    BuddycardsItems.FD_SET.VAULT.get()).build(null));

    //Entities
    public static RegistryObject<EntityType<EnderlingEntity>> ENDERLING = ENTITIES.register("enderling",
            () -> EntityType.Builder.of(EnderlingEntity::new, EntityClassification.CREATURE).sized(.6f, 1.8f).build(new ResourceLocation(BuddyCards.MOD_ID, "enderling").toString()));

}
