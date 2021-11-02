package com.wildcard.buddycards.registries;

import com.wildcard.buddycards.BuddyCards;
import com.wildcard.buddycards.blocks.*;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.RotatedPillarBlock;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class BuddycardsBlocks {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, BuddyCards.MOD_ID);

    public static void init() {
        BLOCKS.register(FMLJavaModLoadingContext.get().getModEventBus());
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
    public static final RegistryObject<Block> CARD_STAND = BLOCKS.register("card_stand", CardStandBlock::new);
    //Byg Card Display Blocks
    public static final RegistryObject<Block> ASPEN_CARD_DISPLAY = BLOCKS.register("aspen_card_display", () -> new CardDisplayBlock("byg"));
    public static final RegistryObject<Block> BAOBAB_CARD_DISPLAY = BLOCKS.register("baobab_card_display", () -> new CardDisplayBlock("byg"));
    public static final RegistryObject<Block> BLUE_ENCHANTED_CARD_DISPLAY = BLOCKS.register("blue_enchanted_card_display", () -> new CardDisplayBlock("byg"));
    public static final RegistryObject<Block> BULBIS_CARD_DISPLAY = BLOCKS.register("bulbis_card_display", () -> new CardDisplayBlock("byg"));
    public static final RegistryObject<Block> CHERRY_CARD_DISPLAY = BLOCKS.register("cherry_card_display", () -> new CardDisplayBlock("byg"));
    public static final RegistryObject<Block> CIKA_CARD_DISPLAY = BLOCKS.register("cika_card_display", () -> new CardDisplayBlock("byg"));
    public static final RegistryObject<Block> CYPRESS_CARD_DISPLAY = BLOCKS.register("cypress_card_display", () -> new CardDisplayBlock("byg"));
    public static final RegistryObject<Block> EBONY_CARD_DISPLAY = BLOCKS.register("ebony_card_display", () -> new CardDisplayBlock("byg"));
    public static final RegistryObject<Block> EMBUR_CARD_DISPLAY = BLOCKS.register("embur_card_display", () -> new CardDisplayBlock("byg"));
    public static final RegistryObject<Block> ETHER_CARD_DISPLAY = BLOCKS.register("ether_card_display", () -> new CardDisplayBlock("byg"));
    public static final RegistryObject<Block> FIR_CARD_DISPLAY = BLOCKS.register("fir_card_display", () -> new CardDisplayBlock("byg"));
    public static final RegistryObject<Block> GLACIAL_OAK_CARD_DISPLAY = BLOCKS.register("glacial_oak_card_display", () -> new CardDisplayBlock("byg"));
    public static final RegistryObject<Block> GREEN_ENCHANTED_CARD_DISPLAY = BLOCKS.register("green_enchanted_card_display", () -> new CardDisplayBlock("byg"));
    public static final RegistryObject<Block> HOLLY_CARD_DISPLAY = BLOCKS.register("holly_card_display", () -> new CardDisplayBlock("byg"));
    public static final RegistryObject<Block> JACARANDA_CARD_DISPLAY = BLOCKS.register("jacaranda_card_display", () -> new CardDisplayBlock("byg"));
    public static final RegistryObject<Block> LAMENT_CARD_DISPLAY = BLOCKS.register("lament_card_display", () -> new CardDisplayBlock("byg"));
    public static final RegistryObject<Block> MAHOGANY_CARD_DISPLAY = BLOCKS.register("mahogany_card_display", () -> new CardDisplayBlock("byg"));
    public static final RegistryObject<Block> MANGROVE_CARD_DISPLAY = BLOCKS.register("mangrove_card_display", () -> new CardDisplayBlock("byg"));
    public static final RegistryObject<Block> MAPLE_CARD_DISPLAY = BLOCKS.register("maple_card_display", () -> new CardDisplayBlock("byg"));
    public static final RegistryObject<Block> NIGHTSHADE_CARD_DISPLAY = BLOCKS.register("nightshade_card_display", () -> new CardDisplayBlock("byg"));
    public static final RegistryObject<Block> PALM_CARD_DISPLAY = BLOCKS.register("palm_card_display", () -> new CardDisplayBlock("byg"));
    public static final RegistryObject<Block> PINE_CARD_DISPLAY = BLOCKS.register("pine_card_display", () -> new CardDisplayBlock("byg"));
    public static final RegistryObject<Block> RAINBOW_EUCALYPTUS_CARD_DISPLAY = BLOCKS.register("rainbow_eucalyptus_card_display", () -> new CardDisplayBlock("byg"));
    public static final RegistryObject<Block> REDWOOD_CARD_DISPLAY = BLOCKS.register("redwood_card_display", () -> new CardDisplayBlock("byg"));
    public static final RegistryObject<Block> SKYRIS_CARD_DISPLAY = BLOCKS.register("skyris_card_display", () -> new CardDisplayBlock("byg"));
    public static final RegistryObject<Block> SYTHIAN_CARD_DISPLAY = BLOCKS.register("sythian_card_display", () -> new CardDisplayBlock("byg"));
    public static final RegistryObject<Block> WILLOW_CARD_DISPLAY = BLOCKS.register("willow_card_display", () -> new CardDisplayBlock("byg"));
    public static final RegistryObject<Block> WITCH_HAZEL_CARD_DISPLAY = BLOCKS.register("witch_hazel_card_display", () -> new CardDisplayBlock("byg"));
    public static final RegistryObject<Block> ZELKOVA_CARD_DISPLAY = BLOCKS.register("zelkova_card_display", () -> new CardDisplayBlock("byg"));
    //Buddysteel Luminis and Zylex
    public static final RegistryObject<Block> BUDDYSTEEL_BLOCK = BLOCKS.register("buddysteel_block", () -> new Block(AbstractBlock.Properties.copy(net.minecraft.block.Blocks.IRON_BLOCK)));
    public static final RegistryObject<Block> ZYLEX_BLOCK = BLOCKS.register("zylex_block", () -> new Block(AbstractBlock.Properties.copy(net.minecraft.block.Blocks.IRON_BLOCK)));
    public static final RegistryObject<Block> LUMINIS_BLOCK = BLOCKS.register("luminis_block", () -> new Block(AbstractBlock.Properties.copy(net.minecraft.block.Blocks.GOLD_BLOCK).lightLevel((state) -> 10)));
    public static final RegistryObject<Block> LUMINIS_PANELS = BLOCKS.register("luminis_panels", () -> new Block(AbstractBlock.Properties.copy(LUMINIS_BLOCK.get())));
    public static final RegistryObject<Block> LUMINIS_PILLAR = BLOCKS.register("luminis_pillar", () -> new RotatedPillarBlock(AbstractBlock.Properties.copy(LUMINIS_BLOCK.get())));
    public static final RegistryObject<Block> LUMINIS_ORE = BLOCKS.register("luminis_ore", LuminisOreBlock::new);
    public static final RegistryObject<Block> DEEP_LUMINIS_BLOCK = BLOCKS.register("deep_luminis_block", () -> new Block(AbstractBlock.Properties.copy(net.minecraft.block.Blocks.GOLD_BLOCK).lightLevel((state) -> 8)));
    public static final RegistryObject<Block> PERFECT_BUDDYSTEEL_BLOCK = BLOCKS.register("perfect_buddysteel_block", () -> new Block(AbstractBlock.Properties.copy(net.minecraft.block.Blocks.IRON_BLOCK)));

    public static final RegistryObject<BoosterBoxBlock> BB = BLOCKS.register("booster_box_mystery", () -> new BoosterBoxBlock(0, "buddycards"));

}
