package com.wildcard.buddycards.client.renderer;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.wildcard.buddycards.blocks.tiles.CardStandTile;
import com.wildcard.buddycards.items.CardItem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class CardStandTileRenderer extends TileEntityRenderer<CardStandTile> {
    public CardStandTileRenderer(TileEntityRendererDispatcher rendererDispatcherIn) {
        super(rendererDispatcherIn);
    }

    @Override
    public void render(CardStandTile tileEntityIn, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int combinedLightIn, int combinedOverlayIn) {
        if(tileEntityIn.getCard().getItem() instanceof CardItem) {
            matrixStackIn.push();
            matrixStackIn.translate(0.5, 0.25, 0.5);
            matrixStackIn.scale(0.5f, 0.5f, 0.5f);
            matrixStackIn.rotate(Vector3f.YP.rotationDegrees(tileEntityIn.getDir() * -22.5f));
            ItemStack itemstack = tileEntityIn.getCard();
            IBakedModel ibakedmodel = Minecraft.getInstance().getItemRenderer().getItemModelWithOverrides(itemstack, tileEntityIn.getWorld(), null);
            Minecraft.getInstance().getItemRenderer().renderItem(itemstack, ItemCameraTransforms.TransformType.FIXED, true, matrixStackIn, bufferIn, combinedLightIn, combinedOverlayIn, ibakedmodel);
            matrixStackIn.pop();
        }
    }
}
