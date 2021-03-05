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
    public static ForgeConfigSpec.DoubleValue witherChance;
    public static ForgeConfigSpec.IntValue witherMaxPacks;

    public static ForgeConfigSpec.BooleanValue piglinBartering;
    public static ForgeConfigSpec.BooleanValue lootChestPacks;

    public static ForgeConfigSpec.BooleanValue doMedalEffects;

    public static ForgeConfigSpec.BooleanValue challengeMode;
    public static ForgeConfigSpec.IntValue challengePointsCommon;
    public static ForgeConfigSpec.IntValue challengePointsUncommon;
    public static ForgeConfigSpec.IntValue challengePointsRare;
    public static ForgeConfigSpec.IntValue challengePointsEpic;
    public static ForgeConfigSpec.DoubleValue challengeShinyMult;
    public static ForgeConfigSpec.DoubleValue challengeSet1Mult;
    public static ForgeConfigSpec.DoubleValue challengeSet2Mult;
    public static ForgeConfigSpec.DoubleValue challengeSet3Mult;
    public static ForgeConfigSpec.DoubleValue challengeSet4Mult;
    public static ForgeConfigSpec.DoubleValue challengeSet5Mult;
    public static ForgeConfigSpec.DoubleValue challengeSet6Mult;
    public static ForgeConfigSpec.DoubleValue challengeSet7Mult;
    public static ForgeConfigSpec.DoubleValue challengeGrade1Mult;
    public static ForgeConfigSpec.DoubleValue challengeGrade2Mult;
    public static ForgeConfigSpec.DoubleValue challengeGrade3Mult;
    public static ForgeConfigSpec.DoubleValue challengeGrade4Mult;
    public static ForgeConfigSpec.DoubleValue challengeGrade5Mult;

    public static ForgeConfigSpec.DoubleValue aquacultureFishingChance;



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
        witherChance = builder.comment("\nOdds of withers dropping nether set packs, 0 for 0%, 1 for 100%, default is 50%")
                .defineInRange("mobDrops.witherChance", .5f, 0, 1);
        witherMaxPacks = builder.comment("\nMaximum amount of packs dropped when a wither drops packs, default is 3")
                .defineInRange("mobDrops.witherMaxPacks", 3, 1, 16);

        piglinBartering = builder.comment("\nAllow packs to be obtained through Piglin bartering, default is true")
                .define("lootTables.piglinBartering", true);
        lootChestPacks = builder.comment("\nAllow natural generation of packs in loot chests, default is true")
                .define("lootTables.lootChestPacks", true);

        doMedalEffects = builder.comment("\nEnables medal effects, default is true")
                .define("misc.doMedalEffects", true);

        aquacultureFishingChance = builder.comment("\nOdds of fishing up an Aquaculture set card or pack while using a buddycard hook, 0-1, default is .075")
                .defineInRange("integration.aquacultureFishingChance", .05, 0, 1);

        challengeMode = builder.comment("\nEnable Challenge mode, assigning cards point values, and giving each player a challenge binder, default is false")
                .define("challenge.doChallengeMode", false);
        challengePointsCommon = builder.comment("\nBase challenge points for common cards, 1-100, default is 1")
                .defineInRange("challenge.pointsCommon", 1, 1, 100);
        challengePointsUncommon = builder.comment("\nBase challenge points for uncommon cards, 1-100, default is 2")
                .defineInRange("challenge.pointsUncommon", 2, 1, 100);
        challengePointsRare = builder.comment("\nBase challenge points for rare cards, 1-100, default is 3")
                .defineInRange("challenge.pointsRare", 3, 1, 100);
        challengePointsEpic = builder.comment("\nBase challenge points for epic cards, 1-100, default is 5")
                .defineInRange("challenge.pointsEpic", 5, 1, 100);
        challengeShinyMult = builder.comment("\nMultiplier for challenge points on shiny cards, 0-10, default is 1.25")
                .defineInRange("challenge.shinyMultiplier", 1.25, 0, 10);
        challengeSet1Mult = builder.comment("\nMultiplier for challenge points from base set, 0-10, default is 1")
                .defineInRange("challenge.set1Multiplier", 1f, 0, 10);
        challengeSet2Mult = builder.comment("\nMultiplier for challenge points from nether set, 0-10, default is 1.2")
                .defineInRange("challenge.set2Multiplier", 1.2f, 0, 10);
        challengeSet3Mult = builder.comment("\nMultiplier for challenge points from end set, 0-10, default is 2")
                .defineInRange("challenge.set3Multiplier", 2f, 0, 10);
        challengeSet4Mult = builder.comment("\nMultiplier for challenge points from byg set, 0-10, default is 2")
                .defineInRange("challenge.set4Multiplier", 2f, 0, 10);
        challengeSet5Mult = builder.comment("\nMultiplier for challenge points from create set, 0-10, default is 1")
                .defineInRange("challenge.set5Multiplier", 1f, 0, 10);
        challengeSet6Mult = builder.comment("\nMultiplier for challenge points from aquaculture set, 0-10, default is 1")
                .defineInRange("challenge.set6Multiplier", 1f, 0, 10);
        challengeSet6Mult = builder.comment("\nMultiplier for challenge points from fd set, 0-10, default is 1")
                .defineInRange("challenge.set6Multiplier", 1f, 0, 10);
        challengeGrade1Mult = builder.comment("\nMultiplier for challenge points with grade D, 0-10, default is 1")
                .defineInRange("challenge.grade1Multiplier", 1f, 0, 10);
        challengeGrade2Mult = builder.comment("\nMultiplier for challenge points with grade C, 0-10, default is 1.4")
                .defineInRange("challenge.grade2Multiplier", 1.4f, 0, 10);
        challengeGrade3Mult = builder.comment("\nMultiplier for challenge points with grade B, 0-10, default is 1.8")
                .defineInRange("challenge.grade3Multiplier", 1.8f, 0, 10);
        challengeGrade4Mult = builder.comment("\nMultiplier for challenge points with grade A, 0-10, default is 2.5")
                .defineInRange("challenge.grade4Multiplier", 2.5f, 0, 10);
        challengeGrade5Mult = builder.comment("\nMultiplier for challenge points with grade S, 0-10, default is 10")
                .defineInRange("challenge.grade5Multiplier", 10f, 0, 10);
    }
}
