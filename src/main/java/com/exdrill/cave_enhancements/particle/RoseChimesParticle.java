package com.exdrill.cave_enhancements.particle;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.particle.AnimatedParticle;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleFactory;
import net.minecraft.client.particle.SpriteProvider;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particle.DefaultParticleType;

@Environment(EnvType.CLIENT)
public class RoseChimesParticle extends AnimatedParticle {

    RoseChimesParticle(ClientWorld world, double x, double y, double z, double velocityX, double velocityY, double velocityZ, SpriteProvider spriteProvider) {
        super(world, x, y, z, spriteProvider, 0.0F);
        this.velocityX = 0.1 * (random.nextBetween(0, 2) * 2 - 1);
        this.velocityY = 0.1 * (random.nextBetween(0, 2) * 2 - 1);
        this.velocityZ = 0.1 * (random.nextBetween(0, 2) * 2 - 1);
        this.scale = 0.2F;
        this.collidesWithWorld = false;
        this.gravityStrength = 0.0F;
        this.maxAge = 8;
        this.setSpriteForAge(spriteProvider);
    }

    public void tick() {
        super.tick();

        this.velocityX /= 3;
        this.velocityY /= 3;
        this.velocityZ /= 3;
    }

    @Environment(EnvType.CLIENT)
    public static class RoseChimesFactory implements ParticleFactory<DefaultParticleType> {
        private final SpriteProvider spriteProvider;

        public RoseChimesFactory(SpriteProvider spriteProvider) {
            this.spriteProvider = spriteProvider;
        }



        public Particle createParticle(DefaultParticleType defaultParticleType, ClientWorld clientWorld, double d, double e, double f, double g, double h, double i) {

            int velocity = clientWorld.random.nextBetween(-1, 1);


            return new RoseChimesParticle(clientWorld, d + (velocity * 0.5F), e + (velocity * 0.5F), f + (velocity * 0.5F), 0.0D, 0.0D, 0.0D, this.spriteProvider);
        }
    }
}
