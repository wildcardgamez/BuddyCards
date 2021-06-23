package com.wildcard.buddycards.util;

import com.wildcard.buddycards.client.renderer.CardDisplayTileRenderer;
import com.wildcard.buddycards.client.renderer.CardStandTileRenderer;
import com.wildcard.buddycards.client.renderer.EnderlingRenderer;
import com.wildcard.buddycards.integration.aquaculture.AquacultureIntegration;
import com.wildcard.buddycards.items.CardItem;
import com.wildcard.buddycards.registries.BuddycardsEntities;
import com.wildcard.buddycards.registries.BuddycardsItems;
import com.wildcard.buddycards.registries.BuddycardsMisc;
import com.wildcard.buddycards.screen.BinderScreen;
import com.wildcard.buddycards.screen.VaultScreen;
import net.minecraft.client.gui.ScreenManager;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemModelsProperties;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

public class ClientStuff {
    public static void clientSetup(FMLClientSetupEvent event) {
        event.enqueueWork(() -> ScreenManager.register(BuddycardsMisc.BINDER_CONTAINER.get(), BinderScreen::new));
        event.enqueueWork(() -> ScreenManager.register(BuddycardsMisc.VAULT_CONTAINER.get(), VaultScreen::new));
        for (RegistryObject<CardItem> card: BuddycardsItems.ALL_CARDS) {
            event.enqueueWork(() -> ItemModelsProperties.register(card.get(), new ResourceLocation("grade"), (stack, world, entity) -> {
                if(stack.getTag() != null)
                    return stack.getTag().getInt("grade");
                return 0;
            }));
        }
        if (ModList.get().isLoaded("aquaculture")) {
            event.enqueueWork(() -> ItemModelsProperties.register(AquacultureIntegration.BUDDYSTEEL_FISHING_ROD.get(), new ResourceLocation("cast"), (stack, world, entity) -> {
                if(entity instanceof PlayerEntity && (entity.getItemInHand(Hand.MAIN_HAND) == stack || entity.getItemInHand(Hand.OFF_HAND) == stack) && ((PlayerEntity) entity).fishing != null)
                    return 1;
                else
                    return 0;
            }));
        }
        ClientRegistry.bindTileEntityRenderer(BuddycardsEntities.CARD_DISPLAY_TILE.get(), CardDisplayTileRenderer::new);
        ClientRegistry.bindTileEntityRenderer(BuddycardsEntities.CARD_STAND_TILE.get(), CardStandTileRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(BuddycardsEntities.ENDERLING.get(), EnderlingRenderer::new);
    }
}
