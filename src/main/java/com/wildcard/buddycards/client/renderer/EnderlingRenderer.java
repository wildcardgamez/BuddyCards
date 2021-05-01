package com.wildcard.buddycards.client.renderer;

import com.wildcard.buddycards.BuddyCards;
import com.wildcard.buddycards.client.models.EnderlingModel;
import com.wildcard.buddycards.entities.EnderlingEntity;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.AbstractEyesLayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class EnderlingRenderer extends MobRenderer<EnderlingEntity, EnderlingModel> {
    public EnderlingRenderer(EntityRendererManager renderManagerIn) {
        super(renderManagerIn, new EnderlingModel(), .6f);
        this.addLayer(new EnderlingEyesLayer(this));
    }

    @Override
    public ResourceLocation getEntityTexture(EnderlingEntity entity) {
        return new ResourceLocation(BuddyCards.MOD_ID, "textures/entity/enderling.png");
    }

    @OnlyIn(Dist.CLIENT)
    public class EnderlingEyesLayer extends AbstractEyesLayer<EnderlingEntity, EnderlingModel> {
        private final RenderType RENDER_TYPE = RenderType.getEyes(new ResourceLocation("textures/entity/enderman/enderman_eyes.png"));

        public EnderlingEyesLayer(IEntityRenderer<EnderlingEntity, EnderlingModel> rendererIn) {
            super(rendererIn);
        }

        public RenderType getRenderType() {
            return RENDER_TYPE;
        }
    }
}
