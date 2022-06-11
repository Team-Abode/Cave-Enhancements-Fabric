package com.exdrill.cave_enhancements.registry;

import com.exdrill.cave_enhancements.CaveEnhancements;
import com.exdrill.cave_enhancements.particle.*;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.event.client.ClientSpriteRegistryCallback;
import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.screen.PlayerScreenHandler;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ModParticles {

    public static final DefaultParticleType SMALL_GOOP_DRIP = FabricParticleTypes.simple();
    public static final DefaultParticleType SHOCKWAVE = FabricParticleTypes.simple();
    public static final DefaultParticleType ROSE_QUARTZ_AURA = FabricParticleTypes.simple();
    public static final DefaultParticleType SOOTHING_NOTE = FabricParticleTypes.simple();
    public static final DefaultParticleType ROSE_CHIMES = FabricParticleTypes.simple();
    public static final DefaultParticleType AMETHYST_BLAST = FabricParticleTypes.simple();
    public static final DefaultParticleType HOVERING_NOTE = FabricParticleTypes.simple();

    public static void register() {
        Registry.register(Registry.PARTICLE_TYPE, new Identifier(CaveEnhancements.MODID, "small_goop_drip"), SMALL_GOOP_DRIP);
        Registry.register(Registry.PARTICLE_TYPE, new Identifier(CaveEnhancements.MODID, "shockwave"), SHOCKWAVE);
        Registry.register(Registry.PARTICLE_TYPE, new Identifier(CaveEnhancements.MODID, "rose_quartz_aura"), ROSE_QUARTZ_AURA);
        Registry.register(Registry.PARTICLE_TYPE, new Identifier(CaveEnhancements.MODID, "soothing_note"), SOOTHING_NOTE);
        Registry.register(Registry.PARTICLE_TYPE, new Identifier(CaveEnhancements.MODID, "rose_chimes"), ROSE_CHIMES);
        Registry.register(Registry.PARTICLE_TYPE, new Identifier(CaveEnhancements.MODID, "amethyst_blast"), AMETHYST_BLAST);
        Registry.register(Registry.PARTICLE_TYPE, new Identifier(CaveEnhancements.MODID, "hovering_note"), HOVERING_NOTE);
    }

    public static void registerClient() {
        // Small Goop Drip Client Particle
        ClientSpriteRegistryCallback.event(PlayerScreenHandler.BLOCK_ATLAS_TEXTURE).register(((atlasTexture, registry) -> {
            registry.register(new Identifier(CaveEnhancements.MODID, "particle/small_goop_drip"));
        }));
        ParticleFactoryRegistry.getInstance().register(ModParticles.SMALL_GOOP_DRIP, SmallGoopDripParticle.SmallGoopDripFactory::new);

        // Shockwave Client Particle
        ClientSpriteRegistryCallback.event(PlayerScreenHandler.BLOCK_ATLAS_TEXTURE).register(((atlasTexture, registry) -> {
            registry.register(new Identifier(CaveEnhancements.MODID, "particle/shockwave"));
        }));
        ParticleFactoryRegistry.getInstance().register(ModParticles.SHOCKWAVE, ShockwaveParticle.Factory::new);

        // Rose Quartz Aura Client Particle
        ClientSpriteRegistryCallback.event(PlayerScreenHandler.BLOCK_ATLAS_TEXTURE).register(((atlasTexture, registry) -> {
            registry.register(new Identifier(CaveEnhancements.MODID, "particle/rose_quartz_aura"));
        }));
        ParticleFactoryRegistry.getInstance().register(ModParticles.ROSE_QUARTZ_AURA, RoseQuartzAuraParticle.RoseQuartzFactory::new);

        // Soothing Note Client Particle
        ClientSpriteRegistryCallback.event(PlayerScreenHandler.BLOCK_ATLAS_TEXTURE).register(((atlasTexture, registry) -> {
            registry.register(new Identifier(CaveEnhancements.MODID, "particle/soothing_note"));
        }));
        ParticleFactoryRegistry.getInstance().register(ModParticles.SOOTHING_NOTE, SoothingNoteParticle.SoothingNoteFactory::new);

        // Rose Chimes Client Particle
        ClientSpriteRegistryCallback.event(PlayerScreenHandler.BLOCK_ATLAS_TEXTURE).register(((atlasTexture, registry) -> {
            registry.register(new Identifier(CaveEnhancements.MODID, "particle/rose_chimes"));
        }));
        ParticleFactoryRegistry.getInstance().register(ModParticles.ROSE_CHIMES, RoseChimesParticle.RoseChimesFactory::new);

        // Amethyst Blast Client Particle
        ClientSpriteRegistryCallback.event(PlayerScreenHandler.BLOCK_ATLAS_TEXTURE).register(((atlasTexture, registry) -> {
            registry.register(new Identifier(CaveEnhancements.MODID, "particle/amethyst_blast"));
        }));
        ParticleFactoryRegistry.getInstance().register(ModParticles.AMETHYST_BLAST, AmethystBlastParticle.Factory::new);

        ClientSpriteRegistryCallback.event(PlayerScreenHandler.BLOCK_ATLAS_TEXTURE).register(((atlasTexture, registry) -> {
            registry.register(new Identifier(CaveEnhancements.MODID, "particle/hovering_note"));
        }));
        ParticleFactoryRegistry.getInstance().register(ModParticles.HOVERING_NOTE, HoveringNoteParticle.Factory::new);

    }
}
