package com.wildcard.buddycards.util;

import com.wildcard.buddycards.BuddyCards;
import com.wildcard.buddycards.client.models.EnderlingModel;
import com.wildcard.buddycards.client.renderer.CardDisplayBlockRenderer;
import com.wildcard.buddycards.client.renderer.CardStandBlockRenderer;
import com.wildcard.buddycards.client.renderer.EnderlingRenderer;
import com.wildcard.buddycards.integration.CuriosIntegration;
import com.wildcard.buddycards.integration.aquaculture.AquacultureIntegration;
import com.wildcard.buddycards.items.CardItem;
import com.wildcard.buddycards.registries.BuddycardsBlocks;
import com.wildcard.buddycards.registries.BuddycardsEntities;
import com.wildcard.buddycards.registries.BuddycardsItems;
import com.wildcard.buddycards.registries.BuddycardsMisc;
import com.wildcard.buddycards.screen.BinderScreen;
import com.wildcard.buddycards.screen.VaultScreen;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fmllegacy.RegistryObject;

@Mod.EventBusSubscriber(value = Dist.CLIENT, modid = BuddyCards.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ClientStuff {
    @SubscribeEvent
    public static void clientSetup(FMLClientSetupEvent event) {
        event.enqueueWork(() -> MenuScreens.register(BuddycardsMisc.BINDER_CONTAINER.get(), BinderScreen::new));
        event.enqueueWork(() -> MenuScreens.register(BuddycardsMisc.VAULT_CONTAINER.get(), VaultScreen::new));
        for (RegistryObject<CardItem> card: BuddycardsItems.ALL_CARDS) {
            event.enqueueWork(() -> ItemProperties.register(card.get(), new ResourceLocation("grade"), (stack, world, entity, idk) -> {
                if(stack.getTag() != null)
                    return stack.getTag().getInt("grade");
                return 0;
            }));
        }
        if (ModList.get().isLoaded("aquaculture")) {
            event.enqueueWork(() -> ItemProperties.register(AquacultureIntegration.BUDDYSTEEL_FISHING_ROD.get(), new ResourceLocation("cast"), (stack, world, entity, idk) -> {
                if(entity instanceof Player && (entity.getItemInHand(InteractionHand.MAIN_HAND) == stack || entity.getItemInHand(InteractionHand.OFF_HAND) == stack) && ((Player) entity).fishing != null)
                    return 1;
                else
                    return 0;
            }));
        }
        event.enqueueWork(() -> ItemBlockRenderTypes.setRenderLayer(BuddycardsBlocks.YANNEL.get(), RenderType.cutout()));
        if(ModList.get().isLoaded("curios")) {
            CuriosIntegration.setupRenderers();
        }
    }

    public static ModelLayerLocation ENDERLING_LAYER = new ModelLayerLocation(new ResourceLocation(BuddyCards.MOD_ID, "enderling"), "enderling");

    @SubscribeEvent
    public static void registerEntityRenders(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(BuddycardsEntities.ENDERLING.get(), EnderlingRenderer::new);
        event.registerBlockEntityRenderer(BuddycardsEntities.CARD_DISPLAY_TILE.get(), CardDisplayBlockRenderer::new);
        event.registerBlockEntityRenderer(BuddycardsEntities.CARD_STAND_TILE.get(), CardStandBlockRenderer::new);
    }

    @SubscribeEvent
    public static void registerLayerDefinition(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(ENDERLING_LAYER, EnderlingModel::createBodyLayer);
    }
}
