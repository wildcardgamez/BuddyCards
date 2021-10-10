package com.wildcard.buddycards.client.models;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.world.entity.LivingEntity;

public class MedalModel<T extends LivingEntity> extends HumanoidModel<LivingEntity> {
    public MedalModel(ModelPart part) {
        super(part);
        medal = part.getChild("body").getChild("medal");
    }

    private final ModelPart medal;

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = createMesh(CubeDeformation.NONE, 0);
        PartDefinition partdefinition = meshdefinition.getRoot();

        partdefinition.getChild("body").addOrReplaceChild("medal", CubeListBuilder.create().texOffs(0, 2).addBox(1.0F, -6.0F, -3.0F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.2F))
                .texOffs(0, 0).addBox(0.0F, -8.0F, -2.5F, 4.0F, 2.0F, 0.0F, new CubeDeformation(0.2F, 0.2F, 0.4F)), PartPose.offset(0.0F, 9.0F, 0.5F));
        return LayerDefinition.create(meshdefinition, 16, 16);
    }

    @Override
    public void setupAnim(LivingEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        super.setupAnim(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        body.render(poseStack, buffer, packedLight, packedOverlay);
    }
}
