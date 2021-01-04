package com.wildcard.buddycards;

import com.wildcard.buddycards.client.renderer.CardDisplayTileRenderer;
import com.wildcard.buddycards.integration.CuriosIntegration;
import com.wildcard.buddycards.util.ConfigManager;
import com.wildcard.buddycards.util.LootInjectionHandler;
import com.wildcard.buddycards.util.MobDropHandler;
import com.wildcard.buddycards.util.RegistryHandler;
import jdk.nashorn.internal.runtime.regexp.joni.Config;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.client.registry.ClientRegistry;
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

        ConfigManager.loadConfig(FMLPaths.CONFIGDIR.get().resolve("buddycards-client.toml").toString());

        RegistryHandler.cardItemCreation();
        RegistryHandler.init();
        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);
        MinecraftForge.EVENT_BUS.addListener(LootInjectionHandler::lootLoad);

        if (ModList.get().isLoaded("curios"))
            FMLJavaModLoadingContext.get().getModEventBus().register(CuriosIntegration.class);
    }

    private void setup(final FMLCommonSetupEvent event) {
        MinecraftForge.EVENT_BUS.register(new MobDropHandler());
        MinecraftForge.EVENT_BUS.register(new LootInjectionHandler());
    }

    private void doClientStuff(final FMLClientSetupEvent event) {
        RegistryHandler.clientSetup(event);
    }

    public static final ItemGroup TAB = new ItemGroup("buddycards") {
        @Override
        public ItemStack createIcon() {
            return new ItemStack(RegistryHandler.PACK_BASE.get());
        }
    };
}
