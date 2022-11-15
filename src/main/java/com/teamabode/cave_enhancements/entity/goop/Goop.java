package com.teamabode.cave_enhancements.entity.goop;

import com.teamabode.cave_enhancements.registry.ModBlocks;
import com.teamabode.cave_enhancements.registry.ModItems;
import com.teamabode.cave_enhancements.registry.ModSounds;
import com.teamabode.cave_enhancements.registry.ModTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.RandomSource;
import net.minecraft.util.TimeUtil;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.phys.AABB;
import org.jetbrains.annotations.Nullable;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;

@ParametersAreNonnullByDefault
public class Goop extends Monster implements GoopBucketable {
    private static final EntityDataAccessor<Boolean> FROM_BUCKET = SynchedEntityData.defineId(Goop.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> STICKING_UP = SynchedEntityData.defineId(Goop.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Integer> DRIP_COOLDOWN = SynchedEntityData.defineId(Goop.class, EntityDataSerializers.INT);

    public Goop(EntityType<? extends Monster> entityType, Level level) {
        super(entityType, level);
        this.xpReward = 5;
        this.setDripCooldown(TimeUtil.rangeOfSeconds(50, 70).sample(getRandom()));
    }

    public static AttributeSupplier.Builder createGoopAttributes() {
        return Monster.createMonsterAttributes()
                .add(Attributes.MOVEMENT_SPEED, 0.0D)
                .add(Attributes.KNOCKBACK_RESISTANCE, 1.0)
                .add(Attributes.MAX_HEALTH, 15)
                .add(Attributes.ARMOR, 2);
    }

    public void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(FROM_BUCKET, false);
        this.entityData.define(STICKING_UP, false);
        this.entityData.define(DRIP_COOLDOWN, 0);
    }

    public void addAdditionalSaveData(CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.putBoolean("FromBucket", this.fromBucket());
        tag.putBoolean("StickingUp", this.isStickingUp());
        tag.putInt("DripCooldown", this.getDripCooldown());
    }

    public void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        setFromBucket(tag.getBoolean("FromBucket"));
        setStickingUp(tag.getBoolean("StickingUp"));
        setDripCooldown(tag.getInt("DripCooldown"));
    }

    public void saveDefaultDataToBucketTag(ItemStack stack) {
        GoopBucketable.saveDefaultDataToBucketTag(this, stack);
        stack.getOrCreateTag();
    }

    public void loadDefaultDataFromBucketTag(CompoundTag nbt) {
        GoopBucketable.loadDefaultDataFromBucketTag(this, nbt);
    }

    public boolean fromBucket() {
        return this.entityData.get(FROM_BUCKET);
    }

    public void setFromBucket(boolean fromBucket) {
        this.entityData.set(FROM_BUCKET, fromBucket);
    }

    public boolean isStickingUp() {
        return this.entityData.get(STICKING_UP);
    }

    public void setStickingUp(boolean stickingUp) {
        this.entityData.set(STICKING_UP, stickingUp);
    }

    public int getDripCooldown() {
        return this.entityData.get(DRIP_COOLDOWN);
    }

    public void setDripCooldown(int dripCooldown) {
        this.entityData.set(DRIP_COOLDOWN, dripCooldown);
    }

    public SpawnGroupData finalizeSpawn(ServerLevelAccessor serverWorld, DifficultyInstance difficulty, MobSpawnType spawnReason, @Nullable SpawnGroupData entityData, @Nullable CompoundTag entityNbt) {
        level = serverWorld.getLevel();
        double x = this.getX();
        double y = this.getY();
        double z = this.getZ();
        double origY = y;
        BlockPos blockUpPos = new BlockPos(x, y + 1, z);


        if (spawnReason == MobSpawnType.NATURAL) {
            if (!level.isClientSide()) {
                while(y < level.getMaxBuildHeight() && !serverWorld.getBlockState(blockUpPos).entityCanStandOnFace(level, blockUpPos, this, Direction.DOWN)){
                    x = this.getX();
                    y = this.getY();
                    z = this.getZ();
                    teleportToWithTicket(x, y + 0.1D, z);
                    y = this.getY();
                    blockUpPos = new BlockPos(x, y + 1, z);
                }
                if (y >= level.getMaxBuildHeight()){
                    y = origY;
                    teleportToWithTicket(x, y + 0.1D, z);
                }
            }
            setStickingUp(true);
        } else {
            setStickingUp(serverWorld.getBlockState(blockUpPos).entityCanStandOnFace(level, blockUpPos, this, Direction.DOWN));
        }
        if (spawnReason == MobSpawnType.BUCKET) {
            return entityData;
        }
        return super.finalizeSpawn(serverWorld, difficulty, spawnReason, entityData, entityNbt);
    }

    public static boolean checkGoopSpawnRules(EntityType<? extends Monster> entityType, LevelAccessor levelAccessor, MobSpawnType mobSpawnType, BlockPos blockPos, RandomSource randomSource) {
        return levelAccessor.getBlockState(blockPos.below()).is(ModTags.GOOP_SPAWNABLE_ON);
    }

    public void aiStep() {
        if (!level.isClientSide){
            if(isStickingUp()) {
                this.setDeltaMovement(this.getDeltaMovement().multiply(0D, 0D, 0D));

                if (level != null) {
                    double x = this.getX();
                    double y = this.getY();
                    double z = this.getZ();

                    BlockPos blockUpPos = new BlockPos(x, y + 1, z);

                    if (!level.getBlockState(blockUpPos).entityCanStandOnFace(level, blockUpPos, this, Direction.DOWN)){
                        setStickingUp(false);
                    }
                }
            }

        }
        super.aiStep();
    }

    public void customServerAiStep() {
       this.setDripCooldown(this.getDripCooldown() - 1);

       if (this.tickCount % 40 == 0) {
           List<LivingEntity> list = level.getEntitiesOfClass(LivingEntity.class, new AABB(this.getX(), this.getY(), this.getZ(), this.getX(), this.getY() - 20, this.getZ()));
           for (LivingEntity livingEntity : list) {
               if (livingEntity.level.getBlockState(livingEntity.blockPosition()).is(ModBlocks.GOOP_TRAP) && livingEntity.isAlive()) {
                   this.drip();
               }
           }
       }
       if (this.getDripCooldown() <= 0) {
           this.drip();
           this.setDripCooldown(TimeUtil.rangeOfSeconds(50, 70).sample(random));
       }
       super.customServerAiStep();
    }

    private void drip(){
        if(this.isStickingUp()) {
            BigGoopDrip bigGoopDripEntity = new BigGoopDrip(level, this);
            bigGoopDripEntity.setPos(this.position());
            level.addFreshEntity(bigGoopDripEntity);
        }
    }

    public InteractionResult mobInteract(Player player, InteractionHand hand) {
        return GoopBucketable.bucketMobPickup(player, hand, this).orElse(super.mobInteract(player, hand));
    }

    public boolean isPushable() {
        return false;
    }

    public PushReaction getPistonPushReaction() {
        return PushReaction.BLOCK;
    }

    public boolean causeFallDamage(float fallDistance, float multiplier, DamageSource source) {
        return false;
    }

    public ItemStack getBucketItemStack() {
        return new ItemStack(ModItems.GOOP_BUCKET);
    }

    public SoundEvent getPickupSound() {
        return ModSounds.ITEM_BUCKET_FILL_GOOP;
    }

    public boolean requiresCustomPersistence() {
        return super.requiresCustomPersistence() || this.fromBucket();
    }

    public boolean removeWhenFarAway(double distanceSquared) {
        return !this.fromBucket() && !this.hasCustomName();
    }

    protected SoundEvent getDeathSound() {
        return ModSounds.ENTITY_GOOP_DEATH;
    }

    protected SoundEvent getHurtSound(DamageSource source) {
        return ModSounds.ENTITY_GOOP_HURT;
    }
}
