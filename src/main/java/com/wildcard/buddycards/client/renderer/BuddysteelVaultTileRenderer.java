package com.wildcard.buddycards.client.renderer;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.wildcard.buddycards.blocks.BuddysteelVaultBlock;
import com.wildcard.buddycards.blocks.BuddysteelVaultTile;
import com.wildcard.buddycards.items.CardItem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.vector.Vector3f;

public class BuddysteelVaultTileRenderer extends TileEntityRenderer<BuddysteelVaultTile> {

    public BuddysteelVaultTileRenderer(TileEntityRendererDispatcher rendererDispatcherIn) {
        super(rendererDispatcherIn);
    }

    @Override
    public void render(BuddysteelVaultTile tileEntityIn, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int combinedLightIn, int combinedOverlayIn) {
        if(tileEntityIn.getBlockState().get(BuddysteelVaultBlock.OPEN)) {
            ItemStack itemstack = tileEntityIn.stackInDisplaySlot(1).getStack();
            System.out.println(itemstack);
            if(itemstack.getItem() instanceof CardItem) {
                matrixStackIn.push();
                matrixStackIn.translate(0.28125, 0.6, 0.375);
                matrixStackIn.scale(0.5f, 0.5f, 0.5f);
                matrixStackIn.rotate(Vector3f.YP.rotationDegrees(180));
                IBakedModel ibakedmodel = Minecraft.getInstance().getItemRenderer().getItemModelWithOverrides(itemstack, tileEntityIn.getWorld(), null);
                Minecraft.getInstance().getItemRenderer().renderItem(itemstack, ItemCameraTransforms.TransformType.FIXED, true, matrixStackIn, bufferIn, combinedLightIn, combinedOverlayIn, ibakedmodel);
                matrixStackIn.pop();
            }
        }
    }
}
