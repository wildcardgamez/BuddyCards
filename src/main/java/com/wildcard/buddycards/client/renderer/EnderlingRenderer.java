package com.wildcard.buddycards.client.renderer;

import com.wildcard.buddycards.BuddyCards;
import com.wildcard.buddycards.client.models.EnderlingModel;
import com.wildcard.buddycards.entities.EnderlingEntity;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.EyesLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class EnderlingRenderer extends MobRenderer<EnderlingEntity, EnderlingModel> {
    public EnderlingRenderer(EntityRenderDispatcher renderManagerIn) {
        super(renderManagerIn, new EnderlingModel(), .6f);
        this.addLayer(new EnderlingEyesLayer(this));
    }

    @Override
    public ResourceLocation getTextureLocation(EnderlingEntity entity) {
        return new ResourceLocation(BuddyCards.MOD_ID, "textures/entity/enderling.png");
    }

    @OnlyIn(Dist.CLIENT)
    public class EnderlingEyesLayer extends EyesLayer<EnderlingEntity, EnderlingModel> {
        private final RenderType RENDER_TYPE = RenderType.eyes(new ResourceLocation("textures/entity/enderman/enderman_eyes.png"));

        public EnderlingEyesLayer(RenderLayerParent<EnderlingEntity, EnderlingModel> rendererIn) {
            super(rendererIn);
        }

        public RenderType renderType() {
            return RENDER_TYPE;
        }
    }
}
