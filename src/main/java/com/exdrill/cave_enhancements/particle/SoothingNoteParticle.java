package com.exdrill.cave_enhancements.particle;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.particle.AnimatedParticle;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleFactory;
import net.minecraft.client.particle.SpriteProvider;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particle.DefaultParticleType;

public class SoothingNoteParticle extends AnimatedParticle {


    public SoothingNoteParticle(ClientWorld world, double x, double y, double z, double velocityX, double velocityY, double velocityZ, SpriteProvider spriteProvider) {
        super(world, x, y, z, spriteProvider, 0.0F);
        this.velocityX = velocityX;
        this.velocityY = velocityY;
        this.velocityZ = velocityZ;
        this.scale *= 3F;
        this.collidesWithWorld = false;
        this.gravityStrength = 0.0F;
        this.maxAge = 60 + this.random.nextInt(12);
        this.setSpriteForAge(spriteProvider);

    }

    @Environment(EnvType.CLIENT)
    public static class SoothingNoteFactory implements ParticleFactory<DefaultParticleType> {
        private final SpriteProvider spriteProvider;

        public SoothingNoteFactory(SpriteProvider spriteProvider) {
            this.spriteProvider = spriteProvider;
        }

        public Particle createParticle(DefaultParticleType defaultParticleType, ClientWorld clientWorld, double d, double e, double f, double g, double h, double i) {
            SoothingNoteParticle glowParticle = new SoothingNoteParticle(clientWorld, d, e, f, 0.0D, 0.0D, 0.0D, this.spriteProvider);
            boolean j = true;
            boolean k = true;
            return glowParticle;
        }
    }
}
