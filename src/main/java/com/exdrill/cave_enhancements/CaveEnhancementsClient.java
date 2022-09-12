package com.exdrill.cave_enhancements;

import com.exdrill.cave_enhancements.client.renderer.blockentity.RoseQuartzChimesBlockEntityRenderer;
import com.exdrill.cave_enhancements.client.renderer.entity.*;
import com.exdrill.cave_enhancements.client.model.CruncherModel;
import com.exdrill.cave_enhancements.client.model.DripstonePikeModel;
import com.exdrill.cave_enhancements.client.model.DripstoneTortoiseModel;
import com.exdrill.cave_enhancements.client.model.GoopModel;
import com.exdrill.cave_enhancements.particle.*;
import com.exdrill.cave_enhancements.registry.ModBlockEntities;
import com.exdrill.cave_enhancements.registry.ModBlocks;
import com.exdrill.cave_enhancements.registry.ModEntities;
import com.exdrill.cave_enhancements.registry.ModParticles;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.BlockEntityRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.event.client.ClientSpriteRegistryCallback;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.resources.ResourceLocation;

import static com.exdrill.cave_enhancements.CaveEnhancements.MODID;

@Environment(EnvType.CLIENT)
public class CaveEnhancementsClient implements ClientModInitializer {



    @Override
    public void onInitializeClient() {

        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.GOOP_SPLAT, RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.DRIPPING_GOOP, RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.GLOW_SPLOTCH, RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.SPECTACLE_CANDLE, RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.JAGGED_ROSE_QUARTZ, RenderType.cutout());

        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.REDSTONE_RECEIVER, RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.EXPOSED_REDSTONE_RECEIVER, RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.WEATHERED_REDSTONE_RECEIVER, RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.OXIDIZED_REDSTONE_RECEIVER, RenderType.cutout());

        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.WAXED_REDSTONE_RECEIVER, RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.WAXED_EXPOSED_REDSTONE_RECEIVER, RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.WAXED_WEATHERED_REDSTONE_RECEIVER, RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.WAXED_OXIDIZED_REDSTONE_RECEIVER, RenderType.cutout());


        ModParticles.registerClient();

        // Entity Renderers
        EntityRendererRegistry.register(ModEntities.GOOP, GoopRenderer::new);
        EntityRendererRegistry.register(ModEntities.CRUNCHER, CruncherRenderer::new);
        EntityRendererRegistry.register(ModEntities.DRIPSTONE_TORTOISE, DripstoneTortoiseRenderer::new);
        EntityRendererRegistry.register(ModEntities.DRIPSTONE_PIKE, DripstonePikeRenderer::new);
        EntityRendererRegistry.register(ModEntities.BIG_GOOP_DRIP_PROJECTILE_ENTITY, ThrownItemRenderer::new);
        EntityRendererRegistry.register(ModEntities.HARMONIC_ARROW, HarmonicArrowRenderer::new);


        // Render Layers
        EntityModelLayerRegistry.registerModelLayer(CruncherModel.ENTITY_MODEL_LAYER, CruncherModel::getTexturedModelData);
        EntityModelLayerRegistry.registerModelLayer(GoopModel.ENTITY_MODEL_LAYER, GoopModel::getTexturedModelData);
        EntityModelLayerRegistry.registerModelLayer(DripstoneTortoiseModel.LAYER_LOCATION, DripstoneTortoiseModel::createBodyLayer);
        EntityModelLayerRegistry.registerModelLayer(DripstonePikeModel.LAYER_LOCATION, DripstonePikeModel::getTexturedModelData);
        EntityModelLayerRegistry.registerModelLayer(RoseQuartzChimesBlockEntityRenderer.LAYER_LOCATION, RoseQuartzChimesBlockEntityRenderer::getTexturedModelData);

        // Block Entity Renderers
        BlockEntityRendererRegistry.register(ModBlockEntities.ROSE_QUARTZ_CHIMES, RoseQuartzChimesBlockEntityRenderer::new);
        ClientSpriteRegistryCallback.event(TextureAtlas.LOCATION_BLOCKS).register((atlasTexture, registry) ->
                registry.register(new ResourceLocation(MODID, "entity/rose_quartz_chimes/chime")));
    }
}
