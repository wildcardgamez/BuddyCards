package com.wildcard.buddycards.client.models;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.EquipmentSlotType;

public class PerfectBuddysteelArmorModel extends BipedModel<LivingEntity> {
    private final EquipmentSlotType slot;

    public PerfectBuddysteelArmorModel(EquipmentSlotType slot) {
        super(1);
        this.slot = slot;
        texWidth = 64;
        texHeight = 64;

        if(slot.equals(EquipmentSlotType.LEGS)) {
            body = new ModelRenderer(this);
            body.setPos(0.0F, 0.0F, 0.0F);
            body.texOffs(16, 48).addBox(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, 0.5F, false);

            leftLeg = new ModelRenderer(this);
            leftLeg.setPos(2.0F, 11.0F, 0.0F);
            leftLeg.texOffs(0, 48).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, 0.5F, true);

            ModelRenderer left_bump_r1 = new ModelRenderer(this);
            left_bump_r1.setPos(0.7012F, 2.6409F, 0.0F);
            leftLeg.addChild(left_bump_r1);
            setRotationAngle(left_bump_r1, 0.0F, 0.0F, -0.3491F);
            left_bump_r1.texOffs(36, 37).addBox(-1.5F, 0.5F, -2.0F, 3.0F, 1.0F, 4.0F, 0.7F, false);

            rightLeg = new ModelRenderer(this);
            rightLeg.setPos(-2.0F, 11.0F, 0.0F);
            rightLeg.texOffs(0, 48).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, 0.5F, false);

            ModelRenderer right_bump_r1 = new ModelRenderer(this);
            right_bump_r1.setPos(-0.7012F, 2.6409F, 0.0F);
            rightLeg.addChild(right_bump_r1);
            setRotationAngle(right_bump_r1, 0.0F, 0.0F, 0.3491F);
            right_bump_r1.texOffs(36, 32).addBox(-1.5F, 0.5F, -2.0F, 3.0F, 1.0F, 4.0F, 0.7F, false);}
        else {
            body = new ModelRenderer(this);
            body.setPos(0.0F, 0.0F, 0.0F);
            body.texOffs(16, 16).addBox(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, 1F, false);

            leftLeg = new ModelRenderer(this);
            leftLeg.setPos(2.0F, 12.0F, 0.0F);
            leftLeg.texOffs(0, 16).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, 1F, true);

            ModelRenderer bump_r1 = new ModelRenderer(this);
            bump_r1.setPos(0.0F, 6.0F, 0.0F);
            leftLeg.addChild(bump_r1);
            setRotationAngle(bump_r1, 0.0F, 0.0F, -0.2182F);
            bump_r1.texOffs(18, 42).addBox(-2.0F, -1.0F, -2.0F, 5.0F, 2.0F, 4.0F, 1.1F, false);

            rightLeg = new ModelRenderer(this);
            rightLeg.setPos(-2.0F, 12.0F, 0.0F);
            rightLeg.texOffs(0, 16).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, 1F, false);

            ModelRenderer bump_r2 = new ModelRenderer(this);
            bump_r2.setPos(0.0F, 6.0F, 0.0F);
            rightLeg.addChild(bump_r2);
            setRotationAngle(bump_r2, 0.0F, 0.0F, 0.2182F);
            bump_r2.texOffs(0, 42).addBox(-3.0F, -1.0F, -2.0F, 5.0F, 2.0F, 4.0F, 1.1F, false);

            head = new ModelRenderer(this);
            head.setPos(0.0F, 0.0F, 0.0F);
            head.texOffs(0, 0).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, 1F, false);

            ModelRenderer right_horn_r1 = new ModelRenderer(this);
            right_horn_r1.setPos(-5.0F, -4.0F, 0.0F);
            head.addChild(right_horn_r1);
            setRotationAngle(right_horn_r1, -0.4363F, 0.0F, -0.1309F);
            right_horn_r1.texOffs(0, 1).addBox(0.0F, -5.5F, -1.0F, 2.0F, 5.0F, 2.0F, 0.85F, false);

            ModelRenderer left_horn_r1 = new ModelRenderer(this);
            left_horn_r1.setPos(5.0F, -4.0F, 0.0F);
            head.addChild(left_horn_r1);
            setRotationAngle(left_horn_r1, -0.4363F, 0.0F, 0.1309F);
            left_horn_r1.texOffs(24, 1).addBox(-2.0F, -5.5F, -1.0F, 2.0F, 5.0F, 2.0F, 0.85F, false);

            rightArm = new ModelRenderer(this);
            rightArm.setPos(-4.0F, 2.0F, 0.0F);
            rightArm.texOffs(40, 16).addBox(-4.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, 1F, false);

            ModelRenderer right_shoulder_r1 = new ModelRenderer(this);
            right_shoulder_r1.setPos(2.0F, 0.0F, 0.0F);
            rightArm.addChild(right_shoulder_r1);
            setRotationAngle(right_shoulder_r1, 0.0F, 0.0F, 0.1745F);
            right_shoulder_r1.texOffs(0, 33).addBox(-7.5F, -3.0F, -2.0F, 5.0F, 5.0F, 4.0F, 1.1F, false);

            leftArm = new ModelRenderer(this);
            leftArm.setPos(4.0F, 2.0F, 0.0F);
            leftArm.texOffs(40, 16).addBox(0.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, 1F, true);

            ModelRenderer left_shoulder_r1 = new ModelRenderer(this);
            left_shoulder_r1.setPos(-2.0F, 0.0F, 0.0F);
            leftArm.addChild(left_shoulder_r1);
            setRotationAngle(left_shoulder_r1, 0.0F, 0.0F, -0.1745F);
            left_shoulder_r1.texOffs(18, 33).addBox(2.5F, -3.0F, -2.0F, 5.0F, 5.0F, 4.0F, 1.1F, false);
        }
    }

    @Override
    public void renderToBuffer(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha){
        switch(slot) {
            case FEET:
                leftLeg.render(matrixStack, buffer, packedLight, packedOverlay);
                rightLeg.render(matrixStack, buffer, packedLight, packedOverlay);
                break;
            case LEGS:
                body.render(matrixStack, buffer, packedLight, packedOverlay);
                leftLeg.render(matrixStack, buffer, packedLight, packedOverlay);
                rightLeg.render(matrixStack, buffer, packedLight, packedOverlay);
                break;
            case CHEST:
                body.render(matrixStack, buffer, packedLight, packedOverlay);
                rightArm.render(matrixStack, buffer, packedLight, packedOverlay);
                leftArm.render(matrixStack, buffer, packedLight, packedOverlay);
                break;
            case HEAD:
                head.render(matrixStack, buffer, packedLight, packedOverlay);
                break;
        }
    }

    public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.xRot = x;
        modelRenderer.yRot = y;
        modelRenderer.zRot = z;
    }
}
