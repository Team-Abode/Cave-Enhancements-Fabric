package com.exdrill.cave_enhancements.registry;

import com.exdrill.cave_enhancements.CaveEnhancements;
import com.exdrill.cave_enhancements.particle.*;
import com.outercloud.scribe.Scribe;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.event.client.ClientSpriteRegistryCallback;
import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.minecraft.core.Registry;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.InventoryMenu;

public class ModParticles {

    public static SimpleParticleType SMALL_GOOP_DRIP;
    public static final SimpleParticleType SHOCKWAVE = FabricParticleTypes.simple();
    public static SimpleParticleType ROSE_QUARTZ_AURA;
    public static final SimpleParticleType SOOTHING_NOTE = FabricParticleTypes.simple();
    public static final SimpleParticleType ROSE_CHIMES = FabricParticleTypes.simple();
    public static final SimpleParticleType AMETHYST_BLAST = FabricParticleTypes.simple();
    public static final SimpleParticleType HOVERING_NOTE = FabricParticleTypes.simple();

    public static void register() {
        Scribe.RegisterParticle(new ResourceLocation(CaveEnhancements.MODID, "small_goop_drip"));

        ROSE_QUARTZ_AURA = Scribe.RegisterParticle(new ResourceLocation(CaveEnhancements.MODID, "rose_quartz_aura"));

        Registry.register(Registry.PARTICLE_TYPE, new ResourceLocation(CaveEnhancements.MODID, "shockwave"), SHOCKWAVE);
        Registry.register(Registry.PARTICLE_TYPE, new ResourceLocation(CaveEnhancements.MODID, "soothing_note"), SOOTHING_NOTE);
        Registry.register(Registry.PARTICLE_TYPE, new ResourceLocation(CaveEnhancements.MODID, "rose_chimes"), ROSE_CHIMES);
        Registry.register(Registry.PARTICLE_TYPE, new ResourceLocation(CaveEnhancements.MODID, "amethyst_blast"), AMETHYST_BLAST);
        Registry.register(Registry.PARTICLE_TYPE, new ResourceLocation(CaveEnhancements.MODID, "hovering_note"), HOVERING_NOTE);
    }

    public static void registerClient() {
        Scribe.RegisterClientParticle(
            new ResourceLocation(CaveEnhancements.MODID, "small_goop_drip"),
            SmallGoopDripParticle.SmallGoopDripFactory::new
        );

        Scribe.RegisterDataDrivenClientParticle(new ResourceLocation(CaveEnhancements.MODID, "rose_quartz_aura"));

        // Shockwave Client Particle
        ClientSpriteRegistryCallback.event(InventoryMenu.BLOCK_ATLAS).register(((atlasTexture, registry) -> {
            registry.register(new ResourceLocation(CaveEnhancements.MODID, "particle/shockwave"));
        }));
        ParticleFactoryRegistry.getInstance().register(ModParticles.SHOCKWAVE, ShockwaveParticle.Factory::new);

        // Soothing Note Client Particle
        ClientSpriteRegistryCallback.event(InventoryMenu.BLOCK_ATLAS).register(((atlasTexture, registry) -> {
            registry.register(new ResourceLocation(CaveEnhancements.MODID, "particle/soothing_note"));
        }));
        ParticleFactoryRegistry.getInstance().register(ModParticles.SOOTHING_NOTE, SoothingNoteParticle.SoothingNoteFactory::new);

        // Rose Chimes Client Particle
        ClientSpriteRegistryCallback.event(InventoryMenu.BLOCK_ATLAS).register(((atlasTexture, registry) -> {
            registry.register(new ResourceLocation(CaveEnhancements.MODID, "particle/rose_chimes"));
        }));
        ParticleFactoryRegistry.getInstance().register(ModParticles.ROSE_CHIMES, RoseChimesParticle.RoseChimesFactory::new);

        // Amethyst Blast Client Particle
        ClientSpriteRegistryCallback.event(InventoryMenu.BLOCK_ATLAS).register(((atlasTexture, registry) -> {
            registry.register(new ResourceLocation(CaveEnhancements.MODID, "particle/amethyst_blast"));
        }));
        ParticleFactoryRegistry.getInstance().register(ModParticles.AMETHYST_BLAST, AmethystBlastParticle.Factory::new);

        ClientSpriteRegistryCallback.event(InventoryMenu.BLOCK_ATLAS).register(((atlasTexture, registry) -> {
            registry.register(new ResourceLocation(CaveEnhancements.MODID, "particle/hovering_note"));
        }));
        ParticleFactoryRegistry.getInstance().register(ModParticles.HOVERING_NOTE, HoveringNoteParticle.Factory::new);

    }
}
