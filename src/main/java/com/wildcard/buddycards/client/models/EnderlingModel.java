package com.wildcard.buddycards.client.models;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.wildcard.buddycards.entities.EnderlingEntity;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.util.Mth;

public class EnderlingModel extends EntityModel<EnderlingEntity> {
    private final ModelPart torso;
    private final ModelPart head;
    private final ModelPart ll;
    private final ModelPart rl;
    private final ModelPart la;
    private final ModelPart ra;

    public EnderlingModel() {
        texWidth = 64;
        texHeight = 32;

        torso = new ModelPart(this);
        torso.setPos(0.0F, 8.0F, 0.0F);
        torso.texOffs(32, 4).addBox(-3.0F, -4.0F, -2.0F, 6.0F, 8.0F, 4.0F, 0.0F, false);
        torso.texOffs(32, 16).addBox(-4.0F, -3.0F, 2.0F, 8.0F, 6.0F, 2.0F, 0.0F, false);
        torso.texOffs(26, 16).addBox(-1.0F, -2.0F, 3.5F, 2.0F, 3.0F, 1.0F, 0.0F, false);

        head = new ModelPart(this);
        head.setPos(0.0F, 4.0F, 0.0F);
        head.texOffs(0, 0).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, 0.0F, false);

        ll = new ModelPart(this);
        ll.setPos(2.0F, 12.0F, 0.0F);
        ll.texOffs(0, 18).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 12.0F, 2.0F, 0.0F, true);

        rl = new ModelPart(this);
        rl.setPos(-2.0F, 12.0F, 0.0F);
        rl.texOffs(0, 18).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 12.0F, 2.0F, 0.0F, false);

        la = new ModelPart(this);
        la.setPos(3.0F, 5.0F, 0.0F);
        la.texOffs(8, 18).addBox(0.0F, -1.0F, -1.0F, 2.0F, 12.0F, 2.0F, 0.0F, true);

        ra = new ModelPart(this);
        ra.setPos(-3.0F, 5.0F, 0.0F);
        ra.texOffs(8, 18).addBox(-2.0F, -1.0F, -1.0F, 2.0F, 12.0F, 2.0F, 0.0F, false);
    }

    public void setupAnim(EnderlingEntity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.head.xRot = headPitch * ((float)Math.PI / 180F);
        this.head.yRot = netHeadYaw * ((float)Math.PI / 180F);
        this.torso.xRot = 0;
        this.rl.xRot = Mth.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
        this.ll.xRot = Mth.cos(limbSwing * 0.6662F + (float)Math.PI) * 1.4F * limbSwingAmount;
        this.ra.xRot = Mth.cos(limbSwing * 0.6662F + (float)Math.PI) * 1.4F * limbSwingAmount;
        this.la.xRot = Mth.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
    }

    @Override
    public void renderToBuffer(PoseStack matrixStack, VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha){
        torso.render(matrixStack, buffer, packedLight, packedOverlay);
        head.render(matrixStack, buffer, packedLight, packedOverlay);
        ll.render(matrixStack, buffer, packedLight, packedOverlay);
        rl.render(matrixStack, buffer, packedLight, packedOverlay);
        la.render(matrixStack, buffer, packedLight, packedOverlay);
        ra.render(matrixStack, buffer, packedLight, packedOverlay);
    }
}