package com.wildcard.buddycards;

import com.wildcard.buddycards.client.renderer.CardDisplayTileRenderer;
import com.wildcard.buddycards.client.renderer.CardStandTileRenderer;
import com.wildcard.buddycards.client.renderer.EnderlingRenderer;
import com.wildcard.buddycards.entities.EnderlingEntity;
import com.wildcard.buddycards.integration.CreateIntegration;
import com.wildcard.buddycards.integration.CuriosIntegration;
import com.wildcard.buddycards.integration.aquaculture.AquacultureIntegration;
import com.wildcard.buddycards.integration.fd.FarmersDelightIntegration;
import com.wildcard.buddycards.items.CardItem;
import com.wildcard.buddycards.registries.BuddycardsBlocks;
import com.wildcard.buddycards.registries.BuddycardsEntities;
import com.wildcard.buddycards.registries.BuddycardsItems;
import com.wildcard.buddycards.registries.BuddycardsMisc;
import com.wildcard.buddycards.screen.BinderScreen;
import com.wildcard.buddycards.screen.VaultScreen;
import com.wildcard.buddycards.util.*;
import net.minecraft.client.gui.ScreenManager;
import net.minecraft.entity.ai.attributes.GlobalEntityTypeAttributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemModelsProperties;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.DeferredWorkQueue;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
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
        if (ModList.get().isLoaded("create"))
            CreateIntegration.init();
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
        DeferredWorkQueue.runLater(() -> {
            GlobalEntityTypeAttributes.put(BuddycardsEntities.ENDERLING.get(), EnderlingEntity.setupAttributes().build());
        });
    }

    private void doClientStuff(final FMLClientSetupEvent event) {
        ClientStuff.clientSetup(event);
    }

    public static final ItemGroup TAB = new ItemGroup("buddycards") {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(BuddycardsItems.BASE_SET.PACK.get());
        }
    };

    public static final ItemGroup CARDS_TAB = new ItemGroup("buddycards_cards") {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(BuddycardsItems.LOADED_CARDS.get((int)(Math.random() * (BuddycardsItems.LOADED_CARDS.size()))).get());
        }
    };
}
