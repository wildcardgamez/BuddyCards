package com.wildcard.buddycards.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.wildcard.buddycards.blocks.tiles.CardStandBlockEntity;
import com.wildcard.buddycards.items.CardItem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher;
import net.minecraft.world.item.ItemStack;
import com.mojang.math.Vector3f;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class CardStandBlockRenderer<T extends CardStandBlockEntity> implements BlockEntityRenderer<CardStandBlockEntity> {
    public CardStandBlockRenderer(BlockEntityRendererProvider.Context context) {
    }

    @Override
    public void render(CardStandBlockEntity tileEntityIn, float partialTicks, PoseStack matrixStackIn, MultiBufferSource bufferIn, int combinedLightIn, int combinedOverlayIn) {
        if(tileEntityIn.getCard().getItem() instanceof CardItem) {
            matrixStackIn.pushPose();
            matrixStackIn.translate(0.5, 0.25, 0.5);
            matrixStackIn.scale(0.5f, 0.5f, 0.5f);
            matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(tileEntityIn.getDir() * -22.5f));
            ItemStack itemstack = tileEntityIn.getCard();
            BakedModel ibakedmodel = Minecraft.getInstance().getItemRenderer().getModel(itemstack, tileEntityIn.getLevel(), null, 0);
            Minecraft.getInstance().getItemRenderer().render(itemstack, ItemTransforms.TransformType.FIXED, true, matrixStackIn, bufferIn, combinedLightIn, combinedOverlayIn, ibakedmodel);
            matrixStackIn.popPose();
        }
    }
}
