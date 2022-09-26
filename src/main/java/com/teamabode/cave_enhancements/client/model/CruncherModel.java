package com.teamabode.cave_enhancements.client.model;

import com.teamabode.cave_enhancements.CaveEnhancements;
import com.teamabode.cave_enhancements.entity.cruncher.Cruncher;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.*;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Mob;

public class CruncherModel<E extends Mob> extends EntityModel<Cruncher> {
    public static final ModelLayerLocation ENTITY_MODEL_LAYER = new ModelLayerLocation(new ResourceLocation(CaveEnhancements.MODID, "cruncher"), "main");

    private final ModelPart root;
    private final ModelPart body;
    private final ModelPart leg0;
    private final ModelPart leg1;
    private final ModelPart leg2;
    private final ModelPart leg3;
    public final ModelPart head;
    public final ModelPart upperJaw;
    private final ModelPart lowerJaw;
    private final ModelPart mossCap;


    public CruncherModel(ModelPart modelPart) {
        this.root = modelPart.getChild("root");
        this.body = root.getChild("body");
        this.leg0 = root.getChild("leg0");
        this.leg1 = root.getChild("leg1");
        this.leg2 = body.getChild("leg2");
        this.leg3 = body.getChild("leg3");
        this.head = body.getChild("head");
        this.upperJaw = head.getChild("upper_jaw");
        this.lowerJaw = head.getChild("lower_jaw");
        this.mossCap = upperJaw.getChild("moss_cap");

    }

    public static LayerDefinition getTexturedModelData() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition ModelPartData = meshdefinition.getRoot();
        PartDefinition root = ModelPartData.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));
        PartDefinition body = root.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 26).addBox(-3.0F, -2.5F, -3.0F, 6.0F, 2.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -2.0F, 0.0F));
        PartDefinition leg3 = body.addOrReplaceChild("leg3", CubeListBuilder.create().texOffs(0, 18).addBox(-1.0F, -0.5F, -2.0F, 2.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(2.25F, -0.5F, -1.25F));
        PartDefinition leg2 = body.addOrReplaceChild("leg2", CubeListBuilder.create().texOffs(0, 18).mirror().addBox(-1.0F, -0.5F, -2.0F, 2.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-2.25F, -0.5F, -1.25F));
        PartDefinition head = body.addOrReplaceChild("head", CubeListBuilder.create(), PartPose.offset(0.0F, -1.0F, 0.0F));
        PartDefinition upperJaw = head.addOrReplaceChild("upper_jaw", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -4.0F, -10.0F, 8.0F, 3.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -3.0F, 4.0F));
        PartDefinition mossCap = upperJaw.addOrReplaceChild("moss_cap", CubeListBuilder.create().texOffs(0, 51).addBox(-4.0F, -4.0F, -10.0F, 8.0F, 3.0F, 10.0F, new CubeDeformation(0.25F)), PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition lowerJaw = head.addOrReplaceChild("lower_jaw", CubeListBuilder.create().texOffs(0, 13).addBox(-4.0F, -1.0F, -10.0F, 8.0F, 3.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -3.0F, 4.0F));
        PartDefinition leg0 = root.addOrReplaceChild("leg0", CubeListBuilder.create().texOffs(0, 18).mirror().addBox(-1.0F, -0.5F, -2.0F, 2.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-2.25F, -2.5F, 3.25F));
        PartDefinition leg1 = root.addOrReplaceChild("leg1", CubeListBuilder.create().texOffs(0, 18).addBox(-1.0F, -0.5F, -2.0F, 2.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(2.25F, -2.5F, 3.25F));

        return LayerDefinition.create(meshdefinition, 64, 64);
    }

    @Override
    public void renderToBuffer(PoseStack matrices, VertexConsumer vertices, int light, int overlay, float red, float green, float blue, float alpha) {
        root.render(matrices, vertices, light, overlay, red, green, blue, alpha);
    }

    @Override
    public void setupAnim(Cruncher entity, float f, float g, float h, float i, float j) {

        this.head.yRot = i * 0.017453292F;
        this.leg0.xRot = Mth.cos(f * 1.1F) * 1.4F * g;
        this.leg1.xRot = Mth.cos(f * 1.1F + 3.1415927F) * 3F * g;
        this.leg2.xRot = Mth.cos(f * 1.1F + 3.1415927F) * 3F * g;
        this.leg3.xRot = Mth.cos(f * 1.1F) * 3F * g;

        if (entity.isEating()) {
            this.head.xRot = -5F;
            this.upperJaw.xRot = -Mth.cos(h * 0.8F) * 0.1F;
            this.lowerJaw.xRot = Mth.cos(h * 0.8F) * 0.1F;
        } else if (!entity.isEating()) {
            this.head.xRot = j * 0.017453292F;
            this.upperJaw.xRot = 0.0F;
            this.lowerJaw.xRot = -0.0F;
        }
        this.mossCap.visible = !entity.hasBeenSheared();

    }
}