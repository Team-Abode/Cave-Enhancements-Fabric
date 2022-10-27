package com.teamabode.cave_enhancements.entity.dripstone_tortoise;

import com.teamabode.cave_enhancements.registry.ModEntities;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.UUID;

public class DripstonePike extends Entity {

    @Nullable
    private LivingEntity owner;
    @Nullable
    private UUID ownerUUID;

    public DripstonePike(EntityType<? extends DripstonePike> entityType, Level world) {
        super(entityType, world);
    }

    public DripstonePike(Level level, double x, double y, double z, LivingEntity owner) {
        this(ModEntities.DRIPSTONE_PIKE, level);
        this.setPos(x, y, z);
        this.setOwner(owner);
    }

    public void setOwner(@Nullable LivingEntity owner) {
        this.owner = owner;
        this.ownerUUID = owner == null ? null : owner.getUUID();
    }

    @Nullable
    public LivingEntity getOwner() {
        if (this.owner == null && this.ownerUUID != null && this.level instanceof ServerLevel) {
            Entity entity = ((ServerLevel)this.level).getEntity(this.ownerUUID);

            if (entity instanceof LivingEntity) {
                this.owner = (LivingEntity)entity;
            }
        }

        return this.owner;
    }

    protected void addAdditionalSaveData(CompoundTag compound) {
        if (this.ownerUUID != null) {
            compound.putUUID("Owner", this.ownerUUID);
        }
    }

    public void tick() {
        if (this.tickCount % 2 == 0) {
            List<LivingEntity> list = level.getEntitiesOfClass(LivingEntity.class, getBoundingBox().inflate(0.5D));

            for (LivingEntity livingEntity : list) {
                this.dealDamageTo(livingEntity);
            }

        }
        if (this.tickCount % 10 == 0) {
            this.discard();
        }
    }

    private void dealDamageTo(LivingEntity target) {
        LivingEntity owner = this.getOwner();

        if (target.isAlive() && !target.isInvulnerable() && target != owner && !(target instanceof DripstoneTortoise))  {
            target.hurt(DamageSource.STALAGMITE, 2.0F);
        }
    }

    protected void readAdditionalSaveData(CompoundTag compound) {
        if (compound.hasUUID("Owner")) {
            this.ownerUUID = compound.getUUID("Owner");
        }
    }

    public Packet<?> getAddEntityPacket() {
        return new ClientboundAddEntityPacket(this);
    }

    protected void defineSynchedData() {
    }
}
