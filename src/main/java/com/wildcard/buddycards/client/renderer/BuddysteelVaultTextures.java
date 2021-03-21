package com.wildcard.buddycards.client.renderer;

import com.wildcard.buddycards.BuddyCards;
import net.minecraft.client.renderer.texture.AtlasTexture;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = BuddyCards.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class BuddysteelVaultTextures {
    public static ResourceLocation getVaultTexture(int setNum) {
        return new ResourceLocation(BuddyCards.MOD_ID, "entity/vault/" + setNum);
    }

    @SubscribeEvent
    public static void onStitch(TextureStitchEvent.Pre event) {
        if(event.getMap().getTextureLocation().equals(AtlasTexture.LOCATION_BLOCKS_TEXTURE)) {
            event.addSprite(getVaultTexture(1));
        }
    }
}
