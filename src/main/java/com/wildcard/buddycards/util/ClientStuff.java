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
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.world.entity.player.Player;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.world.InteractionHand;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fmlclient.registry.ClientRegistry;
import net.minecraftforge.fmlclient.registry.RenderingRegistry;
import net.minecraftforge.fmllegacy.RegistryObject;

public class ClientStuff {
    public static void clientSetup(FMLClientSetupEvent event) {
        event.enqueueWork(() -> MenuScreens.register(BuddycardsMisc.BINDER_CONTAINER.get(), BinderScreen::new));
        event.enqueueWork(() -> MenuScreens.register(BuddycardsMisc.VAULT_CONTAINER.get(), VaultScreen::new));
        for (RegistryObject<CardItem> card: BuddycardsItems.ALL_CARDS) {
            event.enqueueWork(() -> ItemProperties.register(card.get(), new ResourceLocation("grade"), (stack, world, entity) -> {
                if(stack.getTag() != null)
                    return stack.getTag().getInt("grade");
                return 0;
            }));
        }
        if (ModList.get().isLoaded("aquaculture")) {
            event.enqueueWork(() -> ItemProperties.register(AquacultureIntegration.BUDDYSTEEL_FISHING_ROD.get(), new ResourceLocation("cast"), (stack, world, entity) -> {
                if(entity instanceof Player && (entity.getItemInHand(InteractionHand.MAIN_HAND) == stack || entity.getItemInHand(InteractionHand.OFF_HAND) == stack) && ((Player) entity).fishing != null)
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
