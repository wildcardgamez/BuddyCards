package com.wildcard.buddycards;

import com.wildcard.buddycards.entities.EnderlingEntity;
import com.wildcard.buddycards.integration.CuriosIntegration;
import com.wildcard.buddycards.integration.aquaculture.AquacultureIntegration;
import com.wildcard.buddycards.integration.fd.FarmersDelightIntegration;
import com.wildcard.buddycards.registries.BuddycardsBlocks;
import com.wildcard.buddycards.registries.BuddycardsEntities;
import com.wildcard.buddycards.registries.BuddycardsItems;
import com.wildcard.buddycards.registries.BuddycardsMisc;
import com.wildcard.buddycards.util.*;
import net.minecraft.world.entity.ai.attributes.DefaultAttributes;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.DeferredWorkQueue;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLPaths;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod("buddycards")
public class BuddyCards
{
    // Directly reference a log4j logger.
    private static final Logger LOGGER = LogManager.getLogger();
    public static final String MOD_ID = "buddycards";

    public BuddyCards() {
        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, ConfigManager.config);

        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::doClientStuff);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(OreGeneration::setup);

        ConfigManager.loadConfig(FMLPaths.CONFIGDIR.get().resolve("buddycards-client.toml").toString());

        if (ModList.get().isLoaded("aquaculture"))
            AquacultureIntegration.init();
        if (ModList.get().isLoaded("farmersdelight"))
            FarmersDelightIntegration.init();
        if (ModList.get().isLoaded("curios"))
            CuriosIntegration.Imc();
        BuddycardsBlocks.init();
        BuddycardsItems.init();
        BuddycardsEntities.init();
        BuddycardsMisc.init();
        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);

        if (ModList.get().isLoaded("curios"))
            FMLJavaModLoadingContext.get().getModEventBus().register(CuriosIntegration.class);
    }

    private void setup(final FMLCommonSetupEvent event) {
        MinecraftForge.EVENT_BUS.register(new MobDropHandler());
        MinecraftForge.EVENT_BUS.register(new EnchantmentHandler());
        MinecraftForge.EVENT_BUS.register(new BlockBreakHandler());
        MinecraftForge.EVENT_BUS.register(new BlockExplodeHandler());
        MinecraftForge.EVENT_BUS.register(new EntitySpawning());
        MinecraftForge.EVENT_BUS.register(new OreGeneration());
        if (ModList.get().isLoaded("aquaculture"))
            MinecraftForge.EVENT_BUS.register(new AquacultureIntegration());
        event.enqueueWork(BuddycardsMisc::brewingSetup);
    }

    private void doClientStuff(final FMLClientSetupEvent event) {
        ClientStuff.clientSetup(event);
    }

    public static final CreativeModeTab TAB = new CreativeModeTab("buddycards") {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(BuddycardsItems.BASE_SET.PACK.get());
        }
    };

    public static final CreativeModeTab CARDS_TAB = new CreativeModeTab("buddycards_cards") {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(BuddycardsItems.LOADED_CARDS.get((int)(Math.random() * (BuddycardsItems.LOADED_CARDS.size()))).get());
        }
    };
}
