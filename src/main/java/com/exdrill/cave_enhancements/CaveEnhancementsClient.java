package com.exdrill.cave_enhancements;

import com.exdrill.cave_enhancements.client.render.block.RoseQuartzChimesBlockEntityRenderer;
import com.exdrill.cave_enhancements.client.render.entity.*;
import com.exdrill.cave_enhancements.client.render.entity.model.CruncherEntityModel;
import com.exdrill.cave_enhancements.client.render.entity.model.DripstonePikeEntityModel;
import com.exdrill.cave_enhancements.client.render.entity.model.DripstoneTortoiseEntityModel;
import com.exdrill.cave_enhancements.client.render.entity.model.GoopEntityModel;
import com.exdrill.cave_enhancements.entity.EntitySpawnPacket;
import com.exdrill.cave_enhancements.particle.*;
import com.exdrill.cave_enhancements.registry.ModBlocks;
import com.exdrill.cave_enhancements.registry.ModEntities;
import com.exdrill.cave_enhancements.registry.ModParticles;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.BlockEntityRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.event.client.ClientSpriteRegistryCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.entity.FlyingItemEntityRenderer;
import net.minecraft.client.texture.SpriteAtlasTexture;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.registry.Registry;

import java.util.UUID;

@Environment(EnvType.CLIENT)
public class CaveEnhancementsClient implements ClientModInitializer {

    public static final Identifier PacketID = new Identifier(CaveEnhancements.MODID, "spawn_packet");

    @Override
    public void onInitializeClient() {
        ModBlocks.registerClient();
        ModParticles.register();
        ModParticles.registerClient();

        // Entity Renderers
        EntityRendererRegistry.register(ModEntities.GOOP, GoopEntityRenderer::new);
        EntityRendererRegistry.register(ModEntities.CRUNCHER, CruncherEntityRenderer::new);
        EntityRendererRegistry.register(ModEntities.DRIPSTONE_TORTOISE, DripstoneTortoiseEntityRenderer::new);
        EntityRendererRegistry.register(ModEntities.DRIPSTONE_PIKE, DripstonePikeEntityRenderer::new);
        EntityRendererRegistry.register(ModEntities.BIG_GOOP_DRIP_PROJECTILE_ENTITY, FlyingItemEntityRenderer::new);
        EntityRendererRegistry.register(ModEntities.HARMONIC_ARROW, JingleArrowEntityRenderer::new);


        // Render Layers
        EntityModelLayerRegistry.registerModelLayer(CruncherEntityModel.ENTITY_MODEL_LAYER, CruncherEntityModel::getTexturedModelData);
        EntityModelLayerRegistry.registerModelLayer(GoopEntityModel.ENTITY_MODEL_LAYER, GoopEntityModel::getTexturedModelData);
        EntityModelLayerRegistry.registerModelLayer(DripstoneTortoiseEntityModel.LAYER_LOCATION, DripstoneTortoiseEntityModel::texturedModelData);
        EntityModelLayerRegistry.registerModelLayer(DripstonePikeEntityModel.LAYER_LOCATION, DripstonePikeEntityModel::getTexturedModelData);
        EntityModelLayerRegistry.registerModelLayer(RoseQuartzChimesBlockEntityRenderer.LAYER_LOCATION, RoseQuartzChimesBlockEntityRenderer::getTexturedModelData);

        // Block Entity Renderers
        BlockEntityRendererRegistry.register(ModBlocks.ROSE_QUARTZ_CHIMES_BLOCK_ENTITY, RoseQuartzChimesBlockEntityRenderer::new);
        ClientSpriteRegistryCallback.event(SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE).register((atlasTexture, registry) ->
                registry.register(new Identifier(CaveEnhancements.MODID, "entity/rose_quartz_chimes/chime")));

        // Particle Factory
        ParticleFactoryRegistry.getInstance().register(ModParticles.SHOCKWAVE, ShockwaveParticle.Factory::new);
        ParticleFactoryRegistry.getInstance().register(ModParticles.SOOTHING_NOTE, SoothingNoteParticle.SoothingNoteFactory::new);
        ParticleFactoryRegistry.getInstance().register(ModParticles.AMETHYST_BLAST, AmethystBlastParticle.Factory::new);
        ParticleFactoryRegistry.getInstance().register(ModParticles.HOVERING_NOTE, HoveringNoteParticle.Factory::new);
        ParticleFactoryRegistry.getInstance().register(ModParticles.ROSE_CHIMES, RoseChimesParticle.RoseChimesFactory::new);
        ParticleFactoryRegistry.getInstance().register(ModParticles.SMALL_GOOP_DRIP, SmallGoopDripParticle.SmallGoopDripFactory::new);

        receiveEntityPacket();
    }

    //For spawning projectiles
    public void receiveEntityPacket() {
        ClientPlayNetworking.registerGlobalReceiver(PacketID, ((client, handler, buf, responseSender) -> {
            EntityType<?> entityType = Registry.ENTITY_TYPE.get(buf.readVarInt());
            UUID uuid = buf.readUuid();
            int entityId = buf.readVarInt();
            Vec3d pos = EntitySpawnPacket.PacketBufUtil.readVec3d(buf);
            float pitch = EntitySpawnPacket.PacketBufUtil.readAngle(buf);
            float yaw = EntitySpawnPacket.PacketBufUtil.readAngle(buf);
            client.executeTask(() -> {
               if (MinecraftClient.getInstance().world == null)
                   throw new IllegalStateException("Cannot spawn entity without world!");

               Entity entity = entityType.create(MinecraftClient.getInstance().world);
               if (entity == null)
                   throw new IllegalStateException("Cannot create entity of type " + entityType);

               entity.updateTrackedPosition(pos.x, pos.y, pos.z);
               entity.setPos(pos.x, pos.y, pos.z);
               entity.setYaw(yaw);
               entity.setPitch(pitch);
               entity.setId(entityId);
               entity.setUuid(uuid);
               MinecraftClient.getInstance().world.addEntity(entityId, entity);
            });
        }));
    }
}
