package com.wildcard.buddycards.client.renderer;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import com.wildcard.buddycards.blocks.BuddysteelVaultBlock;
import com.wildcard.buddycards.blocks.BuddysteelVaultTile;
import com.wildcard.buddycards.util.RegistryHandler;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.client.renderer.model.RenderMaterial;
import net.minecraft.client.renderer.texture.AtlasTexture;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.util.Direction;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraft.world.World;

public class BuddysteelVaultTileRenderer extends TileEntityRenderer<BuddysteelVaultTile> {

    private final ModelRenderer lid;
    private final ModelRenderer bottom;

    public BuddysteelVaultTileRenderer(TileEntityRendererDispatcher rendererDispatcherIn) {
        super(rendererDispatcherIn);
        this.bottom = new ModelRenderer(64, 64, 0, 20);
        this.bottom.addBox(1.0F, 1.0F, 1.0F, 14.0F, 8.0F, 14.0F, 0.0F);
        this.lid = new ModelRenderer(64, 64, 0, 0);
        this.lid.addBox(1.0F, 0.0F, 0.0F, 14.0F, 6.0F, 14.0F, 0.0F);
        this.lid.rotationPointY = 9.0F;
        this.lid.rotationPointZ = 1.0F;
    }

    @Override
    public void render(BuddysteelVaultTile tileEntityIn, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int combinedLightIn, int combinedOverlayIn) {
        World world = tileEntityIn.getWorld();
        boolean flag = world != null;
        BlockState blockstate = flag ? tileEntityIn.getBlockState() : RegistryHandler.BUDDYSTEEL_VAULT.get().getDefaultState().with(BuddysteelVaultBlock.DIR, Direction.NORTH);
        Block block = blockstate.getBlock();
        if (block instanceof BuddysteelVaultBlock) {
            matrixStackIn.push();
            float angle = blockstate.get(BuddysteelVaultBlock.DIR).getHorizontalAngle();
            matrixStackIn.translate(0.5D, 0.5D, 0.5D);
            matrixStackIn.rotate(Vector3f.YP.rotationDegrees(-angle));
            matrixStackIn.translate(-0.5D, -0.5D, -0.5D);
            RenderMaterial material = new RenderMaterial(AtlasTexture.LOCATION_BLOCKS_TEXTURE, BuddysteelVaultTextures.getVaultTexture(1));
            IVertexBuilder buffer = material.getBuffer(bufferIn, RenderType::getEntityCutoutNoCull);
            lid.render(matrixStackIn, buffer, combinedLightIn, combinedOverlayIn);
            bottom.render(matrixStackIn, buffer, combinedLightIn, combinedOverlayIn);
            matrixStackIn.pop();
        }
    }


}
