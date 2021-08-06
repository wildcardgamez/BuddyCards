package com.wildcard.buddycards.registries;

import com.wildcard.buddycards.BuddyCards;
import com.wildcard.buddycards.blocks.BoosterBoxBlock;
import com.wildcard.buddycards.blocks.BuddysteelVaultBlock;
import com.wildcard.buddycards.items.*;
import com.wildcard.buddycards.items.buddysteel.*;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.*;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fmllegacy.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.ArrayList;
import java.util.HashMap;

import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;

public class BuddycardsItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, BuddyCards.MOD_ID);
    public static HashMap<Integer, BuddycardSet> SETS = new HashMap<>();

    public static void init() {
        initSets();
        ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());
    }

    public static final ArrayList<RegistryObject<CardItem>> ALL_CARDS = new ArrayList<>();
    public static final ArrayList<RegistryObject<CardItem>> LOADED_CARDS = new ArrayList<>();

    public static void initSets() {
        BASE_SET.addCards27();
        BASE_SET.finishCards();
        NETHER_SET.addCards27();
        NETHER_SET.finishCards();
        END_SET.addCards27();
        END_SET.finishCards();
        BYG_SET.addCards27();
        BYG_SET.finishCards();
        CREATE_SET.addCards27();
        CREATE_SET.finishCards();
        AQUACULTURE_SET.addCards18();
        AQUACULTURE_SET.finishCards();
        FD_SET.addCards18();
        FD_SET.addGummyCards();
        FD_SET.finishCards();
    }

    public static final BuddycardSet BASE_SET = new BuddycardSet(1, "buddycards", MedalTypes.BASE_SET);
    public static final BuddycardSet NETHER_SET = new BuddycardSet(2, "buddycards", MedalTypes.NETHER_SET);
    public static final BuddycardSet END_SET = new BuddycardSet(3, "buddycards", MedalTypes.END_SET);
    public static final BuddycardSet BYG_SET = new BuddycardSet(4, "byg", MedalTypes.BYG_SET);
    public static final BuddycardSet CREATE_SET = new BuddycardSet(5, "create", MedalTypes.CREATE_SET);
    public static final BuddycardSet AQUACULTURE_SET = new BuddycardSet(6, "aquaculture", MedalTypes.AQUACULTURE_SET, 3);
    public static final BuddycardSet FD_SET = new BuddycardSet(7, "farmersdelight", MedalTypes.FD_SET, 3);

    static public class BuddycardSet {
        public final ArrayList<RegistryObject<CardItem>> CARDS = new ArrayList<>();
        public final RegistryObject<PackItem> PACK;
        public final RegistryObject<BinderItem> BINDER;
        public final RegistryObject<BuddysteelVaultBlock> VAULT;
        public final RegistryObject<BlockItem> VAULT_ITEM;
        public final RegistryObject<BoosterBoxBlock> BB;
        public final RegistryObject<BlockItem> BB_ITEM;
        public final RegistryObject<MedalItem> MEDAL;
        private final int SET_NUMBER;
        private final String MOD_ID;

        public BuddycardSet(int setNumber, String modId, MedalTypes medalType) {
            PACK = ITEMS.register("pack." + setNumber, () -> new PackItem(setNumber, modId, 4, 1));
            BINDER = ITEMS.register("binder." + setNumber, () -> new BinderItem(setNumber, modId));
            VAULT = BuddycardsBlocks.BLOCKS.register("buddysteel_vault." + setNumber, () -> new BuddysteelVaultBlock(setNumber, modId));
            VAULT_ITEM = ITEMS.register("buddysteel_vault." + setNumber, () -> new BlockItem(VAULT.get(), new Item.Properties().tab(BuddyCards.TAB)));
            BB = BuddycardsBlocks.BLOCKS.register("booster_box." + setNumber, () -> new BoosterBoxBlock(setNumber, modId));
            BB_ITEM = ITEMS.register("booster_box." + setNumber, () -> new BlockItem(BB.get(), new Item.Properties().tab(BuddyCards.TAB)));
            MEDAL = ITEMS.register("medal." + setNumber, () -> new MedalItem(medalType));
            SET_NUMBER = setNumber;
            MOD_ID = modId;
        }

        public BuddycardSet(int setNumber, String modId, MedalTypes medalType, int cardsPerPack) {
            PACK = ITEMS.register("pack." + setNumber, () -> new PackItem(setNumber, modId, cardsPerPack, 1));
            BINDER = ITEMS.register("binder." + setNumber, () -> new BinderItem(setNumber, modId));
            VAULT = BuddycardsBlocks.BLOCKS.register("buddysteel_vault." + setNumber, () -> new BuddysteelVaultBlock(setNumber, modId));
            VAULT_ITEM = ITEMS.register("buddysteel_vault." + setNumber, () -> new BlockItem(VAULT.get(), new Item.Properties().tab(BuddyCards.TAB)));
            BB = BuddycardsBlocks.BLOCKS.register("booster_box." + setNumber, () -> new BoosterBoxBlock(setNumber, modId));
            BB_ITEM = ITEMS.register("booster_box." + setNumber, () -> new BlockItem(BB.get(), new Item.Properties().tab(BuddyCards.TAB)));
            MEDAL = ITEMS.register("medal." + setNumber, () -> new MedalItem(medalType));
            SET_NUMBER = setNumber;
            MOD_ID = modId;
        }

        public void addCards27() {
            for(int i = 1; i <= 12; i++) {
                int finalI = i;
                CARDS.add(ITEMS.register("card." + SET_NUMBER + "." + i, () -> new CardItem(SET_NUMBER, finalI, Rarity.COMMON, MOD_ID)));
            }
            for (int i = 13; i <= 21; i++) {
                int finalI = i;
                CARDS.add(ITEMS.register("card." + SET_NUMBER + "." + i, () -> new CardItem(SET_NUMBER, finalI, Rarity.UNCOMMON, MOD_ID)));
            }
            for (int i = 22; i <= 25; i++) {
                int finalI = i;
                CARDS.add(ITEMS.register("card." + SET_NUMBER + "." + i, () -> new CardItem(SET_NUMBER, finalI, Rarity.RARE, MOD_ID)));
            }
            CARDS.add(ITEMS.register("card." + SET_NUMBER + ".26", () -> new CardItem(SET_NUMBER, 26, Rarity.EPIC, MOD_ID)));
            CARDS.add(ITEMS.register("card." + SET_NUMBER + ".27", () -> new CardItem(SET_NUMBER, 27, Rarity.EPIC, MOD_ID)));
        }

        public void addCards18() {
            for(int i = 1; i <= 7; i++) {
                int finalI = i;
                CARDS.add(ITEMS.register("card." + SET_NUMBER + "." + i, () -> new CardItem(SET_NUMBER, finalI, Rarity.COMMON, MOD_ID)));
            }
            for (int i = 8; i <= 13; i++) {
                int finalI = i;
                CARDS.add(ITEMS.register("card." + SET_NUMBER + "." + i, () -> new CardItem(SET_NUMBER, finalI, Rarity.UNCOMMON, MOD_ID)));
            }
            for (int i = 14; i <= 16; i++) {
                int finalI = i;
                CARDS.add(ITEMS.register("card." + SET_NUMBER + "." + i, () -> new CardItem(SET_NUMBER, finalI, Rarity.RARE, MOD_ID)));
            }
            CARDS.add(ITEMS.register("card." + SET_NUMBER + ".17", () -> new CardItem(SET_NUMBER, 17, Rarity.EPIC, MOD_ID)));
            CARDS.add(ITEMS.register("card." + SET_NUMBER + ".18", () -> new CardItem(SET_NUMBER, 18, Rarity.EPIC, MOD_ID)));
        }

        public void addCard(int number, Rarity rarity) {
            CARDS.add(ITEMS.register("card." + SET_NUMBER + "." + number, () -> new CardItem(SET_NUMBER, number, rarity, MOD_ID)));
        }

        public void addGummyCards() {
            for(int i = 1; i <= 6; i++) {
                int finalI = i;
                CARDS.add(ITEMS.register("card." + SET_NUMBER + ".gummy" + i, () -> new GummyCardItem(finalI)));
            }
        }

        public void finishCards() {
            boolean loaded = ModList.get().isLoaded(MOD_ID);
            for(RegistryObject<CardItem> card : CARDS) {
                if (loaded)
                    LOADED_CARDS.add(card);
                ALL_CARDS.add(card);
            }
            SETS.put(SET_NUMBER, this);
        }

        public boolean isLoaded() {
            return ModList.get().isLoaded(MOD_ID);
        }
    }

    //Non-set Items
    public static final RegistryObject<Item> CHALLENGE_BINDER = ITEMS.register("challenge_binder", ChallengeBinder::new);
    public static final RegistryObject<Item> ENDER_BINDER = ITEMS.register("ender_binder", EnderBinderItem::new);
    public static final RegistryObject<Item> MYSTERY_PACK = ITEMS.register("mystery_pack", () -> new PackItem(0, "buddycards", 9, 2));

    //Buddysteel Luminis & Zylex
    public static final RegistryObject<BlockItem> BUDDYSTEEL_BLOCK_ITEM = ITEMS.register("buddysteel_block", () -> new BlockItem(BuddycardsBlocks.BUDDYSTEEL_BLOCK.get(), new Item.Properties().tab(BuddyCards.TAB)));
    public static final RegistryObject<Item> BUDDYSTEEL_INGOT = ITEMS.register("buddysteel_ingot", () -> new Item(new Item.Properties().tab(BuddyCards.TAB)));
    public static final RegistryObject<Item> BUDDYSTEEL_BLEND = ITEMS.register("buddysteel_blend", () -> new Item(new Item.Properties().tab(BuddyCards.TAB)));
    public static final RegistryObject<Item> BUDDYSTEEL_NUGGET = ITEMS.register("buddysteel_nugget", () -> new Item(new Item.Properties().tab(BuddyCards.TAB)));
    public static final RegistryObject<Item> BUDDYSTEEL_HELMET = ITEMS.register("buddysteel_helmet", () -> new BuddysteelArmorItem(BuddysteelArmorMaterial.BUDDYSTEEL, EquipmentSlot.HEAD));
    public static final RegistryObject<Item> BUDDYSTEEL_CHESTPLATE = ITEMS.register("buddysteel_chestplate", () -> new BuddysteelArmorItem(BuddysteelArmorMaterial.BUDDYSTEEL, EquipmentSlot.CHEST));
    public static final RegistryObject<Item> BUDDYSTEEL_LEGGINGS = ITEMS.register("buddysteel_leggings", () -> new BuddysteelArmorItem(BuddysteelArmorMaterial.BUDDYSTEEL, EquipmentSlot.LEGS));
    public static final RegistryObject<Item> BUDDYSTEEL_BOOTS = ITEMS.register("buddysteel_boots", () -> new BuddysteelArmorItem(BuddysteelArmorMaterial.BUDDYSTEEL, EquipmentSlot.FEET));
    public static final RegistryObject<Item> BUDDYSTEEL_SWORD = ITEMS.register("buddysteel_sword", () -> new BuddysteelSwordItem(BuddysteelItemTier.BUDDYSTEEL, 3));
    public static final RegistryObject<Item> BUDDYSTEEL_PICKAXE = ITEMS.register("buddysteel_pickaxe", () -> new BuddysteelPickaxeItem(BuddysteelItemTier.BUDDYSTEEL, 1));
    public static final RegistryObject<Item> BUDDYSTEEL_SHOVEL = ITEMS.register("buddysteel_shovel", () -> new BuddysteelShovelItem(BuddysteelItemTier.BUDDYSTEEL, 1.5f));
    public static final RegistryObject<Item> BUDDYSTEEL_AXE = ITEMS.register("buddysteel_axe", () -> new BuddysteelAxeItem(BuddysteelItemTier.BUDDYSTEEL, 6f));
    public static final RegistryObject<Item> BUDDYSTEEL_HOE = ITEMS.register("buddysteel_hoe", () -> new BuddysteelHoeItem(BuddysteelItemTier.BUDDYSTEEL, 0));

    public static final RegistryObject<BlockItem> ZYLEX_BLOCK_ITEM = ITEMS.register("zylex_block", () -> new BlockItem(BuddycardsBlocks.ZYLEX_BLOCK.get(), new Item.Properties().tab(BuddyCards.TAB)));
    public static final RegistryObject<Item> ZYLEX = ITEMS.register("zylex", () -> new Item(new Item.Properties().tab(BuddyCards.TAB)));
    public static final RegistryObject<Item> ZYLEX_TOKEN = ITEMS.register("zylex_token", () -> new Item(new Item.Properties().tab(BuddyCards.TAB)));
    public static final RegistryObject<Item> ZYLEX_BOOTS = ITEMS.register("zylex_boots", () -> new BuddysteelArmorItem(BuddysteelArmorMaterial.ZYLEX, EquipmentSlot.FEET));
    public static final RegistryObject<Item> ZYLEX_RING = ITEMS.register("zylex_band", () -> new Item(new Item.Properties().tab(BuddyCards.TAB).stacksTo(1).fireResistant().rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<Item> ZYLEX_MEDAL = ITEMS.register("zylex_medal", () -> new MedalItem(MedalTypes.ZYLEX));

    public static final RegistryObject<BlockItem> LUMINIS_BLOCK_ITEM = ITEMS.register("luminis_block", () -> new BlockItem(BuddycardsBlocks.LUMINIS_BLOCK.get(), new Item.Properties().tab(BuddyCards.TAB)));
    public static final RegistryObject<BlockItem> LUMINIS_PANELS_ITEM = ITEMS.register("luminis_panels", () -> new BlockItem(BuddycardsBlocks.LUMINIS_PANELS.get(), new Item.Properties().tab(BuddyCards.TAB)));
    public static final RegistryObject<BlockItem> LUMINIS_PILLAR_ITEM = ITEMS.register("luminis_pillar", () -> new BlockItem(BuddycardsBlocks.LUMINIS_PILLAR.get(), new Item.Properties().tab(BuddyCards.TAB)));
    public static final RegistryObject<BlockItem> LUMINIS_ORE_ITEM = ITEMS.register("luminis_ore", () -> new BlockItem(BuddycardsBlocks.LUMINIS_ORE.get(), new Item.Properties().tab(BuddyCards.TAB)));
    public static final RegistryObject<Item> LUMINIS_CRYSTAL = ITEMS.register("luminis_crystal", () -> new Item(new Item.Properties().tab(BuddyCards.TAB)));
    public static final RegistryObject<Item> LUMINIS = ITEMS.register("luminis", () -> new Item(new Item.Properties().tab(BuddyCards.TAB)));
    public static final RegistryObject<Item> LUMINIS_PANEL = ITEMS.register("luminis_panel", () -> new Item(new Item.Properties().tab(BuddyCards.TAB)));
    public static final RegistryObject<BlockItem> DEEP_LUMINIS_BLOCK_ITEM = ITEMS.register("deep_luminis_block", () -> new BlockItem(BuddycardsBlocks.DEEP_LUMINIS_BLOCK.get(), new Item.Properties().tab(BuddyCards.TAB)));
    public static final RegistryObject<Item> DEEP_LUMINIS_CRYSTAL = ITEMS.register("deep_luminis_crystal", () -> new Item(new Item.Properties().tab(BuddyCards.TAB)));
    public static final RegistryObject<Item> DEEP_LUMINIS = ITEMS.register("deep_luminis", () -> new Item(new Item.Properties().tab(BuddyCards.TAB)));
    public static final RegistryObject<Item> LUMINIS_PICKAXE = ITEMS.register("luminis_pickaxe", () -> new BuddysteelPickaxeItem(BuddysteelItemTier.LUMINIS, 2));
    public static final RegistryObject<Item> LUMINIS_RING = ITEMS.register("luminis_band", () -> new Item(new Item.Properties().tab(BuddyCards.TAB).stacksTo(1).fireResistant().rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<Item> LUMINIS_MEDAL = ITEMS.register("luminis_medal", () -> new MedalItem(MedalTypes.LUMNIS));

    public static final RegistryObject<BlockItem> PERFECT_BUDDYSTEEL_BLOCK_ITEM = ITEMS.register("perfect_buddysteel_block", () -> new BlockItem(BuddycardsBlocks.PERFECT_BUDDYSTEEL_BLOCK.get(), new Item.Properties().tab(BuddyCards.TAB)));
    public static final RegistryObject<Item> PERFECT_BUDDYSTEEL_INGOT = ITEMS.register("perfect_buddysteel_ingot", () -> new Item(new Item.Properties().tab(BuddyCards.TAB)));
    public static final RegistryObject<Item> PERFECT_BUDDYSTEEL_BLEND = ITEMS.register("perfect_buddysteel_blend", () -> new Item(new Item.Properties().tab(BuddyCards.TAB)));
    public static final RegistryObject<Item> PERFECT_BUDDYSTEEL_NUGGET = ITEMS.register("perfect_buddysteel_nugget", () -> new Item(new Item.Properties().tab(BuddyCards.TAB)));
    public static final RegistryObject<Item> PERFECT_BUDDYSTEEL_HELMET = ITEMS.register("perfect_buddysteel_helmet", () -> new BuddysteelArmorItem(BuddysteelArmorMaterial.PERFECT_BUDDYSTEEL, EquipmentSlot.HEAD));
    public static final RegistryObject<Item> PERFECT_BUDDYSTEEL_CHESTPLATE = ITEMS.register("perfect_buddysteel_chestplate", () -> new BuddysteelArmorItem(BuddysteelArmorMaterial.PERFECT_BUDDYSTEEL, EquipmentSlot.CHEST));
    public static final RegistryObject<Item> PERFECT_BUDDYSTEEL_LEGGINGS = ITEMS.register("perfect_buddysteel_leggings", () -> new BuddysteelArmorItem(BuddysteelArmorMaterial.PERFECT_BUDDYSTEEL, EquipmentSlot.LEGS));
    public static final RegistryObject<Item> PERFECT_BUDDYSTEEL_BOOTS = ITEMS.register("perfect_buddysteel_boots", () -> new BuddysteelArmorItem(BuddysteelArmorMaterial.PERFECT_BUDDYSTEEL, EquipmentSlot.FEET));
    public static final RegistryObject<Item> PERFECT_BUDDYSTEEL_SWORD = ITEMS.register("perfect_buddysteel_sword", () -> new BuddysteelSwordItem(BuddysteelItemTier.PERFECT_BUDDYSTEEL, 4));
    public static final RegistryObject<Item> PERFECT_BUDDYSTEEL_PICKAXE = ITEMS.register("perfect_buddysteel_pickaxe", () -> new BuddysteelPickaxeItem(BuddysteelItemTier.PERFECT_BUDDYSTEEL, 2));
    public static final RegistryObject<Item> PERFECT_BUDDYSTEEL_SHOVEL = ITEMS.register("perfect_buddysteel_shovel", () -> new BuddysteelShovelItem(BuddysteelItemTier.PERFECT_BUDDYSTEEL,2.5f));
    public static final RegistryObject<Item> PERFECT_BUDDYSTEEL_AXE = ITEMS.register("perfect_buddysteel_axe", () -> new BuddysteelAxeItem(BuddysteelItemTier.PERFECT_BUDDYSTEEL, 7f));
    public static final RegistryObject<Item> PERFECT_BUDDYSTEEL_HOE = ITEMS.register("perfect_buddysteel_hoe", () -> new BuddysteelHoeItem(BuddysteelItemTier.PERFECT_BUDDYSTEEL, 1));
    public static final RegistryObject<Item> PERFECT_BUDDYSTEEL_MEDAL = ITEMS.register("perfect_buddysteel_medal", () -> new BuddysteelMedalItem(MedalTypes.PERFECT));

    //Misc
    public static final RegistryObject<Item> SHREDDED_BUDDYCARD = ITEMS.register("shredded_buddycard", () -> new Item(new Item.Properties().tab(BuddyCards.TAB)));
    public static final RegistryObject<Item> ASSORTED_GUMMIES = ITEMS.register("assorted_gummies", () -> new Item(new Item.Properties().tab(BuddyCards.TAB).food(new FoodProperties.Builder().nutrition(2).saturationMod(0.3F).fast().build())));
    public static final RegistryObject<Item> MEDAL_TOKEN = ITEMS.register("medal_token", () -> new Item(new Item.Properties().tab(BuddyCards.TAB)));
    public static final RegistryObject<Item> GRADING_SLEEVE = ITEMS.register("grading_sleeve", () -> new Item(new Item.Properties().tab(BuddyCards.TAB)));
    public static final RegistryObject<Item> BUDDYSTEEL_KEY = ITEMS.register("buddysteel_key", () -> new Item(new Item.Properties().tab(BuddyCards.TAB).stacksTo(1)));
    public static final RegistryObject<Item> BUDDYBEANS = ITEMS.register("buddybeans", () -> new Item(new Item.Properties().tab(BuddyCards.TAB).food(new FoodProperties.Builder().nutrition(3).saturationMod(0.3F).build())));

    //Card Display Items
    public static final RegistryObject<BlockItem> OAK_CARD_DISPLAY_ITEM = ITEMS.register("oak_card_display", () -> new BlockItem(BuddycardsBlocks.OAK_CARD_DISPLAY.get(), new Item.Properties().tab(BuddyCards.TAB)));
    public static final RegistryObject<BlockItem> SPRUCE_CARD_DISPLAY_ITEM = ITEMS.register("spruce_card_display", () -> new BlockItem(BuddycardsBlocks.SPRUCE_CARD_DISPLAY.get(), new Item.Properties().tab(BuddyCards.TAB)));
    public static final RegistryObject<BlockItem> BIRCH_CARD_DISPLAY_ITEM = ITEMS.register("birch_card_display", () -> new BlockItem(BuddycardsBlocks.BIRCH_CARD_DISPLAY.get(), new Item.Properties().tab(BuddyCards.TAB)));
    public static final RegistryObject<BlockItem> JUNGLE_CARD_DISPLAY_ITEM = ITEMS.register("jungle_card_display", () -> new BlockItem(BuddycardsBlocks.JUNGLE_CARD_DISPLAY.get(), new Item.Properties().tab(BuddyCards.TAB)));
    public static final RegistryObject<BlockItem> ACACIA_CARD_DISPLAY_ITEM = ITEMS.register("acacia_card_display", () -> new BlockItem(BuddycardsBlocks.ACACIA_CARD_DISPLAY.get(), new Item.Properties().tab(BuddyCards.TAB)));
    public static final RegistryObject<BlockItem> DARK_OAK_CARD_DISPLAY_ITEM = ITEMS.register("dark_oak_card_display", () -> new BlockItem(BuddycardsBlocks.DARK_OAK_CARD_DISPLAY.get(), new Item.Properties().tab(BuddyCards.TAB)));
    public static final RegistryObject<BlockItem> CRIMSON_CARD_DISPLAY_ITEM = ITEMS.register("crimson_card_display", () -> new BlockItem(BuddycardsBlocks.CRIMSON_CARD_DISPLAY.get(), new Item.Properties().tab(BuddyCards.TAB)));
    public static final RegistryObject<BlockItem> WARPED_CARD_DISPLAY_ITEM = ITEMS.register("warped_card_display", () -> new BlockItem(BuddycardsBlocks.WARPED_CARD_DISPLAY.get(), new Item.Properties().tab(BuddyCards.TAB)));
    public static final RegistryObject<BlockItem> CARD_STAND_ITEM = ITEMS.register("card_stand", () -> new BlockItem(BuddycardsBlocks.CARD_STAND.get(), new Item.Properties().tab(BuddyCards.TAB)));

    //Byg Card Display Items
    public static final RegistryObject<BlockItem> ASPEN_CARD_DISPLAY_ITEM = ITEMS.register("aspen_card_display", () -> new BlockItem(BuddycardsBlocks.ASPEN_CARD_DISPLAY.get(), new Item.Properties().tab(BuddyCards.TAB)));
    public static final RegistryObject<BlockItem> BAOBAB_CARD_DISPLAY_ITEM = ITEMS.register("baobab_card_display", () -> new BlockItem(BuddycardsBlocks.BAOBAB_CARD_DISPLAY.get(), new Item.Properties().tab(BuddyCards.TAB)));
    public static final RegistryObject<BlockItem> BLUE_ENCHANTED_CARD_DISPLAY_ITEM = ITEMS.register("blue_enchanted_card_display", () -> new BlockItem(BuddycardsBlocks.BLUE_ENCHANTED_CARD_DISPLAY.get(), new Item.Properties().tab(BuddyCards.TAB)));
    public static final RegistryObject<BlockItem> BULBIS_CARD_DISPLAY_ITEM = ITEMS.register("bulbis_card_display", () -> new BlockItem(BuddycardsBlocks.BULBIS_CARD_DISPLAY.get(), new Item.Properties().tab(BuddyCards.TAB)));
    public static final RegistryObject<BlockItem> CHERRY_CARD_DISPLAY_ITEM = ITEMS.register("cherry_card_display", () -> new BlockItem(BuddycardsBlocks.CHERRY_CARD_DISPLAY.get(), new Item.Properties().tab(BuddyCards.TAB)));
    public static final RegistryObject<BlockItem> CIKA_CARD_DISPLAY_ITEM = ITEMS.register("cika_card_display", () -> new BlockItem(BuddycardsBlocks.CIKA_CARD_DISPLAY.get(), new Item.Properties().tab(BuddyCards.TAB)));
    public static final RegistryObject<BlockItem> CYPRESS_CARD_DISPLAY_ITEM = ITEMS.register("cypress_card_display", () -> new BlockItem(BuddycardsBlocks.CYPRESS_CARD_DISPLAY.get(), new Item.Properties().tab(BuddyCards.TAB)));
    public static final RegistryObject<BlockItem> EBONY_CARD_DISPLAY_ITEM = ITEMS.register("ebony_card_display", () -> new BlockItem(BuddycardsBlocks.EBONY_CARD_DISPLAY.get(), new Item.Properties().tab(BuddyCards.TAB)));
    public static final RegistryObject<BlockItem> EMBUR_CARD_DISPLAY_ITEM = ITEMS.register("embur_card_display", () -> new BlockItem(BuddycardsBlocks.EMBUR_CARD_DISPLAY.get(), new Item.Properties().tab(BuddyCards.TAB)));
    public static final RegistryObject<BlockItem> ETHER_CARD_DISPLAY_ITEM = ITEMS.register("ether_card_display", () -> new BlockItem(BuddycardsBlocks.ETHER_CARD_DISPLAY.get(), new Item.Properties().tab(BuddyCards.TAB)));
    public static final RegistryObject<BlockItem> FIR_CARD_DISPLAY_ITEM = ITEMS.register("fir_card_display", () -> new BlockItem(BuddycardsBlocks.FIR_CARD_DISPLAY.get(), new Item.Properties().tab(BuddyCards.TAB)));
    public static final RegistryObject<BlockItem> GLACIAL_OAK_CARD_DISPLAY_ITEM = ITEMS.register("glacial_oak_card_display", () -> new BlockItem(BuddycardsBlocks.GLACIAL_OAK_CARD_DISPLAY.get(), new Item.Properties().tab(BuddyCards.TAB)));
    public static final RegistryObject<BlockItem> GREEN_ENCHANTED_CARD_DISPLAY_ITEM = ITEMS.register("green_enchanted_card_display", () -> new BlockItem(BuddycardsBlocks.GREEN_ENCHANTED_CARD_DISPLAY.get(), new Item.Properties().tab(BuddyCards.TAB)));
    public static final RegistryObject<BlockItem> HOLLY_CARD_DISPLAY_ITEM = ITEMS.register("holly_card_display", () -> new BlockItem(BuddycardsBlocks.HOLLY_CARD_DISPLAY.get(), new Item.Properties().tab(BuddyCards.TAB)));
    public static final RegistryObject<BlockItem> JACARANDA_CARD_DISPLAY_ITEM = ITEMS.register("jacaranda_card_display", () -> new BlockItem(BuddycardsBlocks.JACARANDA_CARD_DISPLAY.get(), new Item.Properties().tab(BuddyCards.TAB)));
    public static final RegistryObject<BlockItem> LAMENT_CARD_DISPLAY_ITEM = ITEMS.register("lament_card_display", () -> new BlockItem(BuddycardsBlocks.LAMENT_CARD_DISPLAY.get(), new Item.Properties().tab(BuddyCards.TAB)));
    public static final RegistryObject<BlockItem> MAHOGANY_CARD_DISPLAY_ITEM = ITEMS.register("mahogany_card_display", () -> new BlockItem(BuddycardsBlocks.MAHOGANY_CARD_DISPLAY.get(), new Item.Properties().tab(BuddyCards.TAB)));
    public static final RegistryObject<BlockItem> MANGROVE_CARD_DISPLAY_ITEM = ITEMS.register("mangrove_card_display", () -> new BlockItem(BuddycardsBlocks.MANGROVE_CARD_DISPLAY.get(), new Item.Properties().tab(BuddyCards.TAB)));
    public static final RegistryObject<BlockItem> MAPLE_CARD_DISPLAY_ITEM = ITEMS.register("maple_card_display", () -> new BlockItem(BuddycardsBlocks.MAPLE_CARD_DISPLAY.get(), new Item.Properties().tab(BuddyCards.TAB)));
    public static final RegistryObject<BlockItem> NIGHTSHADE_CARD_DISPLAY_ITEM = ITEMS.register("nightshade_card_display", () -> new BlockItem(BuddycardsBlocks.NIGHTSHADE_CARD_DISPLAY.get(), new Item.Properties().tab(BuddyCards.TAB)));
    public static final RegistryObject<BlockItem> PALM_CARD_DISPLAY_ITEM = ITEMS.register("palm_card_display", () -> new BlockItem(BuddycardsBlocks.PALM_CARD_DISPLAY.get(), new Item.Properties().tab(BuddyCards.TAB)));
    public static final RegistryObject<BlockItem> PINE_CARD_DISPLAY_ITEM = ITEMS.register("pine_card_display", () -> new BlockItem(BuddycardsBlocks.PINE_CARD_DISPLAY.get(), new Item.Properties().tab(BuddyCards.TAB)));
    public static final RegistryObject<BlockItem> RAINBOW_EUCALYPTUS_CARD_DISPLAY_ITEM = ITEMS.register("rainbow_eucalyptus_card_display", () -> new BlockItem(BuddycardsBlocks.RAINBOW_EUCALYPTUS_CARD_DISPLAY.get(), new Item.Properties().tab(BuddyCards.TAB)));
    public static final RegistryObject<BlockItem> REDWOOD_CARD_DISPLAY_ITEM = ITEMS.register("redwood_card_display", () -> new BlockItem(BuddycardsBlocks.REDWOOD_CARD_DISPLAY.get(), new Item.Properties().tab(BuddyCards.TAB)));
    public static final RegistryObject<BlockItem> SKYRIS_CARD_DISPLAY_ITEM = ITEMS.register("skyris_card_display", () -> new BlockItem(BuddycardsBlocks.SKYRIS_CARD_DISPLAY.get(), new Item.Properties().tab(BuddyCards.TAB)));
    public static final RegistryObject<BlockItem> SYTHIAN_CARD_DISPLAY_ITEM = ITEMS.register("sythian_card_display", () -> new BlockItem(BuddycardsBlocks.SYTHIAN_CARD_DISPLAY.get(), new Item.Properties().tab(BuddyCards.TAB)));
    public static final RegistryObject<BlockItem> WILLOW_CARD_DISPLAY_ITEM = ITEMS.register("willow_card_display", () -> new BlockItem(BuddycardsBlocks.WILLOW_CARD_DISPLAY.get(), new Item.Properties().tab(BuddyCards.TAB)));
    public static final RegistryObject<BlockItem> WITCH_HAZEL_CARD_DISPLAY_ITEM = ITEMS.register("witch_hazel_card_display", () -> new BlockItem(BuddycardsBlocks.WITCH_HAZEL_CARD_DISPLAY.get(), new Item.Properties().tab(BuddyCards.TAB)));
    public static final RegistryObject<BlockItem> ZELKOVA_CARD_DISPLAY_ITEM = ITEMS.register("zelkova_card_display", () -> new BlockItem(BuddycardsBlocks.ZELKOVA_CARD_DISPLAY.get(), new Item.Properties().tab(BuddyCards.TAB)));

    //Eggs
    public static final RegistryObject<ModdedSpawnEggItem> ENDERLING_SPAWN_EGG = ITEMS.register("spawn_egg_enderling", () -> new ModdedSpawnEggItem(BuddycardsEntities.ENDERLING, 0x2E2744, 0x9A72CC, new Item.Properties().tab(BuddyCards.TAB)));
}
