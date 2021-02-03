package com.wildcard.buddycards.util;

import com.electronwill.nightconfig.core.file.CommentedFileConfig;
import com.electronwill.nightconfig.core.io.WritingMode;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.common.Mod;

import java.io.File;

@Mod.EventBusSubscriber
public class ConfigManager {
    private static final ForgeConfigSpec.Builder builder = new ForgeConfigSpec.Builder();
    public static final ForgeConfigSpec config;

    static {
        init();
        config = builder.build();
    }

    public static void loadConfig(String path)
    {
        final CommentedFileConfig file = CommentedFileConfig.builder(new File(path)).sync().autosave().writingMode(WritingMode.REPLACE).build();
        file.load();
        config.setConfig(file);
    }

    public static ForgeConfigSpec.DoubleValue zombieChance;
    public static ForgeConfigSpec.DoubleValue villagerChance;
    public static ForgeConfigSpec.DoubleValue zombieVillagerChance;
    public static ForgeConfigSpec.DoubleValue piglinChance;
    public static ForgeConfigSpec.DoubleValue zombiePiglinChance;
    public static ForgeConfigSpec.DoubleValue shulkerChance;
    public static ForgeConfigSpec.DoubleValue dragonChance;
    public static ForgeConfigSpec.IntValue dragonMaxPacks;
    public static ForgeConfigSpec.BooleanValue piglinBartering;
    public static ForgeConfigSpec.BooleanValue lootChestPacks;

    public static void init()
    {
        builder.comment("Buddycards config");
        zombieChance = builder.comment("\nOdds of baby zombie dropping base set packs, 0 for 0%, 1 for 100%, default is 20%")
                .defineInRange("mobDrops.zombieChance", .05, 0, 1);
        villagerChance = builder.comment("\nOdds of baby villager dropping base set packs, 0 for 0%, 1 for 100%, default is 20%")
                .defineInRange("mobDrops.villagerChance", .05, 0, 1);
        zombieVillagerChance = builder.comment("\nOdds of baby zombie villager dropping base set packs, 0 for 0%, 1 for 100%, default is 40%")
                .defineInRange("mobDrops.zombieVillagerChance", .1, 0, 1);
        piglinChance = builder.comment("\nOdds of baby piglin dropping nether set packs, 0 for 0%, 1 for 100%, default is 20%")
                .defineInRange("mobDrops.piglinChance", .05, 0, 1);
        zombiePiglinChance = builder.comment("\nOdds of baby zombie piglin dropping nether set , 0 for 0%, 1 for 100%, default is 20%")
                .defineInRange("mobDrops.zombiePiglinChance", .05, 0, 1);
        shulkerChance = builder.comment("\nOdds of shulkers dropping end set packs, 0 for 0%, 1 for 100%, default is 5%")
                .defineInRange("mobDrops.shulkerChance", .05, 0, 1);
        dragonChance = builder.comment("\nOdds of ender dragons dropping end set packs, 0 for 0%, 1 for 100%, default is 100%")
                .defineInRange("mobDrops.dragonChance", 1f, 0, 1);
        dragonMaxPacks = builder.comment("\nMaximum amount of packs dropped when a dragon drops packs, default is 4")
                .defineInRange("mobDrops.dragonMaxPacks", 4, 1, 16);
        piglinBartering = builder.comment("\nAllow packs to be obtained through Piglin bartering, default is true")
                .define("lootTables.piglinBartering", true);
        lootChestPacks = builder.comment("\nAllow natural generation of packs in loot chests, default is true")
                .define("lootTables.lootChestPacks", true);
    }
}
