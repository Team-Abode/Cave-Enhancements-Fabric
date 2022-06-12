package com.exdrill.cave_enhancements.particle;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.SimpleAnimatedParticle;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.core.particles.SimpleParticleType;

public class SoothingNoteParticle extends SimpleAnimatedParticle {


    public SoothingNoteParticle(ClientLevel world, double x, double y, double z, double velocityX, double velocityY, double velocityZ, SpriteSet spriteProvider) {
        super(world, x, y, z, spriteProvider, 0.0F);
        this.xd = velocityX;
        this.yd = velocityY;
        this.zd = velocityZ;
        this.quadSize *= 3F;
        this.hasPhysics = false;
        this.gravity = 0.0F;
        this.lifetime = 60 + this.random.nextInt(12);
        this.setSpriteFromAge(spriteProvider);

    }

    @Environment(EnvType.CLIENT)
    public static class SoothingNoteFactory implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet spriteProvider;

        public SoothingNoteFactory(SpriteSet spriteProvider) {
            this.spriteProvider = spriteProvider;
        }

        public Particle createParticle(SimpleParticleType defaultParticleType, ClientLevel clientWorld, double d, double e, double f, double g, double h, double i) {
            SoothingNoteParticle glowParticle = new SoothingNoteParticle(clientWorld, d, e, f, 0.0D, 0.0D, 0.0D, this.spriteProvider);
            boolean j = true;
            boolean k = true;
            return glowParticle;
        }
    }
}
