package com.teamabode.cave_enhancements.registry;

import com.teamabode.cave_enhancements.CaveEnhancements;
import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.minecraft.core.Registry;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.resources.ResourceLocation;

public class ModParticles {

    public static SimpleParticleType register(String id, SimpleParticleType particleType) {
        return Registry.register(Registry.PARTICLE_TYPE, new ResourceLocation(CaveEnhancements.MODID, id), particleType);
    }

    public static final SimpleParticleType SMALL_GOOP_DRIP = register("small_goop_drip", FabricParticleTypes.simple());
    public static final SimpleParticleType GOOP_EXPLOSION = register("goop_explosion", FabricParticleTypes.simple());
    public static final SimpleParticleType SHOCKWAVE = register("shockwave", FabricParticleTypes.simple());
    public static final SimpleParticleType CHARGE = register("charge", FabricParticleTypes.simple());
    public static final SimpleParticleType SHIMMER = register("shimmer", FabricParticleTypes.simple());
    public static final SimpleParticleType STAGNANT_SHIMMER = register("stagnant_shimmer", FabricParticleTypes.simple());
    public static final SimpleParticleType SOOTHING_NOTE = register("soothing_note", FabricParticleTypes.simple());
    public static final SimpleParticleType ROSE_CHIME = register("rose_chime", FabricParticleTypes.simple());
    public static final SimpleParticleType HARMONIC_WAVE = register("harmonic_wave", FabricParticleTypes.simple());
    public static final SimpleParticleType HARMONIC_NOTE = register("harmonic_note", FabricParticleTypes.simple());


    public static void init() {

    }
}
