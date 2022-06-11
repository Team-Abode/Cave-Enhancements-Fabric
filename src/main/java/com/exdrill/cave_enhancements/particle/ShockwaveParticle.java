package com.exdrill.cave_enhancements.particle;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleFactory;
import net.minecraft.client.particle.ParticleTextureSheet;
import net.minecraft.client.particle.SpriteProvider;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.texture.Sprite;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Quaternion;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3f;

@Environment(EnvType.CLIENT)
public class ShockwaveParticle extends Particle {
    protected Sprite sprite;
    protected float scale;

    ShockwaveParticle(ClientWorld world, double x, double y, double z, double d) {
        super(world, x, y, z, 0.0D, 0.0D, 0.0D);
        this.scale = 1;
        this.velocityX = 0;
        this.velocityY = 0;
        this.velocityZ = 0;
        this.red = 1;
        this.green = 1;
        this.blue = 1;
        this.maxAge = 5;
        this.setColor(1, 1, 1);
    }

    public void buildGeometry(VertexConsumer vertexConsumer, Camera camera, float tickDelta) {
        Vec3d cameraPos = camera.getPos();

        float x = (float)(MathHelper.lerp(tickDelta, this.prevPosX, this.x) - cameraPos.getX());
        float y = (float)(MathHelper.lerp(tickDelta, this.prevPosY, this.y) - cameraPos.getY());
        float z = (float)(MathHelper.lerp(tickDelta, this.prevPosZ, this.z) - cameraPos.getZ());

        float size = this.getSize(tickDelta) * this.age / 20F * 25F + tickDelta / 20F * 25F;

        float minU = this.getMinU();
        float maxU = this.getMaxU();
        float minV = this.getMinV();
        float maxV = this.getMaxV();

        //Makes it fully bright
        int light = 15728640;

        float xVO = 0.5f;
        float yVO = 1F/16F;

        addBand(vertexConsumer, xVO, yVO, size, x, y, z - 0.5f * size, minU, maxU, minV, maxV, light, 0f);
        addBand(vertexConsumer, xVO, yVO, size, x, y, z + 0.5f * size, minU, maxU, minV, maxV, light, 0f);
        addBand(vertexConsumer, xVO, yVO, size, x - 0.5f * size, y, z, minU, maxU, minV, maxV, light, 90f);
        addBand(vertexConsumer, xVO, yVO, size, x + 0.5f * size, y, z, minU, maxU, minV, maxV, light, 90f);

        addBand(vertexConsumer, xVO, yVO, 1.5f * size, x, y, z - 0.75f * size, minU, maxU, minV, maxV, light, 0f);
        addBand(vertexConsumer, xVO, yVO, 1.5f * size, x, y, z + 0.75f * size, minU, maxU, minV, maxV, light, 0f);
        addBand(vertexConsumer, xVO, yVO, 1.5f * size, x - 0.75f * size, y, z, minU, maxU, minV, maxV, light, 90f);
        addBand(vertexConsumer, xVO, yVO, 1.5f * size, x + 0.75f * size, y, z, minU, maxU, minV, maxV, light, 90f);

        addBand(vertexConsumer, xVO, yVO, 2f * size, x, y, z - 1f * size, minU, maxU, minV, maxV, light, 0f);
        addBand(vertexConsumer, xVO, yVO, 2f * size, x, y, z + 1f * size, minU, maxU, minV, maxV, light, 0f);
        addBand(vertexConsumer, xVO, yVO, 2f * size, x - 1f * size, y, z, minU, maxU, minV, maxV, light, 90f);
        addBand(vertexConsumer, xVO, yVO, 2f * size, x + 1f * size, y, z, minU, maxU, minV, maxV, light, 90f);
    }

    public void addBand(VertexConsumer vertexConsumer, float xVO, float yVO, float size, float x, float y, float z, float minU, float maxU, float minV, float maxV, int light, float rotation){
        Vec3f[] vertices = new Vec3f[]{new Vec3f(-xVO * size, -yVO, 0.0F), new Vec3f(-xVO * size, yVO, 0.0F), new Vec3f(xVO * size, yVO, 0.0F), new Vec3f(xVO * size, -yVO, 0.0F)};

        Quaternion quaternion = Quaternion.fromEulerXyzDegrees(new Vec3f(0, rotation, 0));

        for(int i = 0; i < 4; ++i) {
            Vec3f vec3f = vertices[i];
            vec3f.rotate(quaternion);
            vec3f.add(x, y, z);
        }

        vertexConsumer.vertex(vertices[0].getX(), vertices[0].getY(), vertices[0].getZ()).texture(maxU, maxV).color(this.red, this.green, this.blue, this.alpha).light(light).next();
        vertexConsumer.vertex(vertices[1].getX(), vertices[1].getY(), vertices[1].getZ()).texture(maxU, minV).color(this.red, this.green, this.blue, this.alpha).light(light).next();
        vertexConsumer.vertex(vertices[2].getX(), vertices[2].getY(), vertices[2].getZ()).texture(minU, minV).color(this.red, this.green, this.blue, this.alpha).light(light).next();
        vertexConsumer.vertex(vertices[3].getX(), vertices[3].getY(), vertices[3].getZ()).texture(minU, maxV).color(this.red, this.green, this.blue, this.alpha).light(light).next();

        vertices = new Vec3f[]{new Vec3f(xVO * size, -yVO, 0.0F), new Vec3f(xVO * size, yVO, 0.0F), new Vec3f(-xVO * size, yVO, 0.0F), new Vec3f(-xVO * size, -yVO, 0.0F)};

        for(int i = 0; i < 4; ++i) {
            Vec3f vec3f = vertices[i];
            vec3f.rotate(quaternion);
            vec3f.add(x, y, z);
        }

        vertexConsumer.vertex(vertices[0].getX(), vertices[0].getY(), vertices[0].getZ()).texture(maxU, maxV).color(this.red, this.green, this.blue, this.alpha).light(light).next();
        vertexConsumer.vertex(vertices[1].getX(), vertices[1].getY(), vertices[1].getZ()).texture(maxU, minV).color(this.red, this.green, this.blue, this.alpha).light(light).next();
        vertexConsumer.vertex(vertices[2].getX(), vertices[2].getY(), vertices[2].getZ()).texture(minU, minV).color(this.red, this.green, this.blue, this.alpha).light(light).next();
        vertexConsumer.vertex(vertices[3].getX(), vertices[3].getY(), vertices[3].getZ()).texture(minU, maxV).color(this.red, this.green, this.blue, this.alpha).light(light).next();
    }

    public ParticleTextureSheet getType() {
        return ParticleTextureSheet.PARTICLE_SHEET_OPAQUE;
    }

    public float getSize(float tickDelta) {
        return this.scale;
    }

    @Environment(EnvType.CLIENT)
    public static class Factory implements ParticleFactory<DefaultParticleType> {
        private final SpriteProvider spriteProvider;

        public Factory(SpriteProvider spriteProvider) {
            this.spriteProvider = spriteProvider;
        }

        public Particle createParticle(DefaultParticleType defaultParticleType, ClientWorld clientWorld, double d, double e, double f, double g, double h, double i) {
            ShockwaveParticle shockwave = new ShockwaveParticle(clientWorld, d, e, f, g);
            shockwave.setSprite(this.spriteProvider);
            return shockwave;
        }
    }

    protected void setSprite(Sprite sprite) {
        this.sprite = sprite;
    }

    protected float getMinU() {
        return this.sprite.getMinU();
    }

    protected float getMaxU() {
        return this.sprite.getMaxU();
    }

    protected float getMinV() {
        return this.sprite.getMinV();
    }

    protected float getMaxV() {
        return this.sprite.getMaxV();
    }

    public void setSprite(SpriteProvider spriteProvider) {
        this.setSprite(spriteProvider.getSprite(this.random));
    }
}
