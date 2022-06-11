package com.exdrill.cave_enhancements.client.render.block;

import com.exdrill.cave_enhancements.CaveEnhancements;
import com.exdrill.cave_enhancements.block.entity.RoseQuartzChimesBlockEntity;
import net.minecraft.client.model.*;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory.Context;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.texture.SpriteAtlasTexture;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;

import java.util.Objects;

public class RoseQuartzChimesBlockEntityRenderer implements BlockEntityRenderer<RoseQuartzChimesBlockEntity> {

    public static final SpriteIdentifier TEXTURE = new SpriteIdentifier(SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE, new Identifier(CaveEnhancements.MODID, "entity/rose_quartz_chimes/chime"));

    public static final EntityModelLayer LAYER_LOCATION = new EntityModelLayer(new Identifier(CaveEnhancements.MODID, "rose_quartz_chimes"), "main");
    private final ModelPart chimes;
    private final ModelPart chime0;
    private final ModelPart chime1;
    private final ModelPart chime2;
    private final ModelPart chime3;
    private final ModelPart chime4;
    private final ModelPart chime5;

    public RoseQuartzChimesBlockEntityRenderer(Context ctx) {
        ModelPart modelPart = ctx.getLayerModelPart(LAYER_LOCATION);
        this.chimes = modelPart.getChild("chimes");
        this.chime0 = this.chimes.getChild("chime0");
        this.chime1 = this.chimes.getChild("chime1");
        this.chime2 = this.chimes.getChild("chime2");
        this.chime3 = this.chimes.getChild("chime3");
        this.chime4 = this.chimes.getChild("chime4");
        this.chime5 = this.chimes.getChild("chime5");
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData meshdefinition = new ModelData();
        ModelPartData partdefinition = meshdefinition.getRoot();

        ModelPartData chimes = partdefinition.addChild("chimes", ModelPartBuilder.create(), ModelTransform.pivot(8.0F, 12.0F, 8.0F));

        ModelPartData chime0 = chimes.addChild("chime0", ModelPartBuilder.create().uv(0, 0).cuboid(-1.0F, 2.0F, -1.0F, 2.0F, 10.0F, 2.0F, new Dilation(0.0F))
                .uv(1, 13).cuboid(0.0F, 0.0F, -0.5F, 0.0F, 2.0F, 1.0F, new Dilation(0.0F))
                .uv(1, 13).cuboid(-0.5F, 0.0F, 0.0F, 1.0F, 2.0F, 0.0F, new Dilation(0.0F)), ModelTransform.pivot(-5.0F, 0.0F, -5.0F));

        ModelPartData chime1 = chimes.addChild("chime1", ModelPartBuilder.create().uv(0, 0).cuboid(-1.0F, 2.0F, -1.0F, 2.0F, 10.0F, 2.0F, new Dilation(0.0F))
                .uv(1, 13).cuboid(0.0F, 0.0F, -0.5F, 0.0F, 2.0F, 1.0F, new Dilation(0.0F))
                .uv(1, 13).cuboid(-0.5F, 0.0F, 0.0F, 1.0F, 2.0F, 0.0F, new Dilation(0.0F)), ModelTransform.pivot(-5.0F, 0.0F, 0.0F));

        ModelPartData chime2 = chimes.addChild("chime2", ModelPartBuilder.create().uv(0, 0).cuboid(-1.0F, 2.0F, -1.0F, 2.0F, 10.0F, 2.0F, new Dilation(0.0F))
                .uv(1, 13).cuboid(0.0F, 0.0F, -0.5F, 0.0F, 2.0F, 1.0F, new Dilation(0.0F))
                .uv(1, 13).cuboid(-0.5F, 0.0F, 0.0F, 1.0F, 2.0F, 0.0F, new Dilation(0.0F)), ModelTransform.pivot(-5.0F, 0.0F, 5.0F));

        ModelPartData chime3 = chimes.addChild("chime3", ModelPartBuilder.create().uv(0, 0).cuboid(-1.0F, 2.0F, -1.0F, 2.0F, 10.0F, 2.0F, new Dilation(0.0F))
                .uv(1, 13).cuboid(0.0F, 0.0F, -0.5F, 0.0F, 2.0F, 1.0F, new Dilation(0.0F))
                .uv(1, 13).cuboid(-0.5F, 0.0F, 0.0F, 1.0F, 2.0F, 0.0F, new Dilation(0.0F)), ModelTransform.pivot(5.0F, 0.0F, -5.0F));

        ModelPartData chime4 = chimes.addChild("chime4", ModelPartBuilder.create().uv(0, 0).cuboid(-1.0F, 2.0F, -1.0F, 2.0F, 10.0F, 2.0F, new Dilation(0.0F))
                .uv(1, 13).cuboid(0.0F, 0.0F, -0.5F, 0.0F, 2.0F, 1.0F, new Dilation(0.0F))
                .uv(1, 13).cuboid(-0.5F, 0.0F, 0.0F, 1.0F, 2.0F, 0.0F, new Dilation(0.0F)), ModelTransform.pivot(5.0F, 0.0F, 0.0F));

        ModelPartData chime5 = chimes.addChild("chime5", ModelPartBuilder.create().uv(0, 0).cuboid(-1.0F, 2.0F, -1.0F, 2.0F, 10.0F, 2.0F, new Dilation(0.0F))
                .uv(1, 13).cuboid(0.0F, 0.0F, -0.5F, 0.0F, 2.0F, 1.0F, new Dilation(0.0F))
                .uv(1, 13).cuboid(-0.5F, 0.0F, 0.0F, 1.0F, 2.0F, 0.0F, new Dilation(0.0F)), ModelTransform.pivot(5.0F, 0.0F, 5.0F));

        return TexturedModelData.of(meshdefinition, 16, 16);
    }

    @Override
    public void render(RoseQuartzChimesBlockEntity entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        this.chimes.pitch = 3.141592653589793F;
        float intensity;
        float g = entity.ticking + tickDelta;

        if (Objects.requireNonNull(entity.getWorld()).isRaining()) {
            intensity = 3.1415927F * 2 ;
        } else {
            intensity = 3.1415927F * 4;
        }

        float rot = MathHelper.sin(g / intensity) / 10.0F;

        this.chime0.roll = rot;
        this.chime1.roll = rot;
        this.chime2.roll = rot;
        this.chime3.roll = rot;
        this.chime4.roll = rot;
        this.chime5.roll = rot;



        VertexConsumer vertexConsumer = TEXTURE.getVertexConsumer(vertexConsumers, RenderLayer::getEntityCutout);
        this.chimes.render(matrices, vertexConsumer, light, overlay);
    }
}