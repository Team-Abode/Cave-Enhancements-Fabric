package com.exdrill.cave_enhancements.entity;

import com.exdrill.cave_enhancements.registry.ModEntities;
import com.exdrill.cave_enhancements.registry.ModSounds;
import net.minecraft.block.BlockState;
import net.minecraft.entity.AnimationState;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.ai.pathing.Path;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.Angerable;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.Difficulty;
import net.minecraft.world.LightType;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.EnumSet;
import java.util.UUID;

public class DripstoneTortoiseEntity extends PathAwareEntity implements Angerable {
    private static final TrackedData<Integer> ANGER = DataTracker.registerData(DripstoneTortoiseEntity.class, TrackedDataHandlerRegistry.INTEGER);

    private static final TrackedData<Boolean> SHOULD_STOMP = DataTracker.registerData(DripstoneTortoiseEntity.class, TrackedDataHandlerRegistry.BOOLEAN);

    public final AnimationState stompingAnimationState = new AnimationState();

    public int stompTimer;

    public int soothed;

    @Nullable
    private UUID angryAt;

    public DripstoneTortoiseEntity(EntityType<? extends PathAwareEntity> entityType, World world) {
        super(entityType, world);
        this.experiencePoints = 30;
    }

    //NBT
    protected void initDataTracker() {
        super.initDataTracker();

        this.dataTracker.startTracking(ANGER, 0);
        this.dataTracker.startTracking(SHOULD_STOMP, false);
    }

    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);

        nbt.putBoolean("shouldStomp", getShouldStomp());

        writeAngerToNbt(nbt);
    }

    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);

        setShouldStomp(nbt.getBoolean("shouldStomp"));

        readAngerFromNbt(this.world, nbt);
    }

    public void setShouldStomp(boolean shouldStomp){
        dataTracker.set(SHOULD_STOMP, shouldStomp);
    }

    public boolean getShouldStomp(){
        return dataTracker.get(SHOULD_STOMP);
    }

    // Sounds



    @Override
    protected SoundEvent getDeathSound() {
        return ModSounds.ENTITY_DRIPSTONE_TORTOISE_DEATH;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return ModSounds.ENTITY_DRIPSTONE_TORTOISE_HURT;
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return ModSounds.ENTITY_DRIPSTONE_TORTOISE_IDLE;
    }

    @Override
    protected void playStepSound(BlockPos pos, BlockState state) {
        SoundEvent sound = ModSounds.ENTITY_DRIPSTONE_TORTOISE_STEP;
        this.playSound(sound, 0.15F, 1.0F);
    }

    //AI
    protected void initGoals() {
        this.targetSelector.add(1, (new DripstoneTortoiseRevengeGoal(this)).setGroupRevenge(new Class[0]));
        this.targetSelector.add(3, new ActiveTargetGoal(this, PlayerEntity.class, 10, true, false, this::shouldAngerAt));
        this.goalSelector.add(4, new SpikeAttackGoal(this, 1.5D, true));
        this.goalSelector.add(5, new WanderAroundGoal(this, 1.5D));
        this.goalSelector.add(6, new LookAtEntityGoal(this, PlayerEntity.class, 8F));
        this.goalSelector.add(6, new LookAroundGoal(this));
        this.targetSelector.add(6, new RandomSpikeGoal());
    }

    public static DefaultAttributeContainer.Builder createDripstoneTortoiseAttributes() {
        return PathAwareEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.125D)
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 40)
                .add(EntityAttributes.GENERIC_ARMOR, 5)
                .add(EntityAttributes.GENERIC_ARMOR_TOUGHNESS, 2)
                .add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, 0.6)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 3);
    }

    @Override
    public boolean isInvulnerableTo(DamageSource damageSource) {
        if(damageSource == DamageSource.STALAGMITE || damageSource == DamageSource.FALLING_STALACTITE || damageSource.isProjectile() || damageSource.getAttacker() instanceof DripstoneTortoiseEntity) return true;

        return super.isInvulnerableTo(damageSource);
    }

    public boolean shouldAngerAt(Object entity) {
        if (!this.canTarget((LivingEntity)entity)) {
            return false;
        } else {
            return ((LivingEntity) entity).getType() == EntityType.PLAYER && this.isUniversallyAngry(((LivingEntity) entity).world) || ((LivingEntity) entity).getUuid().equals(this.getAngryAt());
        }
    }



    @Override
    public void tick() {
        if (this.world.isClient) {
            if (this.getShouldStomp()) {
                this.stompingAnimationState.startIfNotRunning(this.age);
            } else {
                this.stompingAnimationState.stop();
            }
        }
        super.tick();
    }



    @Override
    public boolean canBeLeashedBy(PlayerEntity player) {
        return false;
    }

    @Override
    protected void pushOutOfBlocks(double x, double y, double z) {
        super.pushOutOfBlocks(x, y, z);
    }

    //Anger
    public int getAngerTime() {
        return this.dataTracker.get(ANGER);
    }

    public void setAngerTime(int angerTime) {
        this.dataTracker.set(ANGER, angerTime);
    }

    public void chooseRandomAngerTime() {
        this.setAngerTime(400);
    }

    @Nullable
    public UUID getAngryAt() {
        return this.angryAt;
    }

    public void setAngryAt(@Nullable UUID angryAt) {
        this.angryAt = angryAt;
    }

    public void sooth(){
        soothed = 2;
        setAngryAt(null);
        setAngerTime(0);
    }

    //Tick
    @Override
    protected void mobTick() {
        if (!this.world.isClient) {
            this.tickAngerLogic((ServerWorld)this.world, false);

            stompTimer--;

            if(stompTimer <= 0){
                setShouldStomp(false);
            }

            soothed--;
        }
    }

    public static boolean isSpawnDark(ServerWorldAccess world, BlockPos pos, Random random) {
        if (world.getLightLevel(LightType.SKY, pos) > random.nextInt(32)) {
            return false;
        } else if (world.getLightLevel(LightType.BLOCK, pos) > 0) {
            return false;
        } else {
            int i = world.toServerWorld().isThundering() ? world.getLightLevel(pos, 10) : world.getLightLevel(pos);
            return i <= random.nextInt(8);
        }
    }

    public static boolean canSpawnInDark(EntityType<? extends PathAwareEntity> type, ServerWorldAccess world, SpawnReason spawnReason, BlockPos pos, Random random) {
        return world.getDifficulty() != Difficulty.PEACEFUL && isSpawnDark(world, pos, random) && canMobSpawn(type, world, spawnReason, pos, random);
    }


    //Goals
    private class DripstoneTortoiseRevengeGoal extends RevengeGoal {
        DripstoneTortoiseRevengeGoal(DripstoneTortoiseEntity dripstoneTortoise) {
            super(dripstoneTortoise);
        }

        public boolean shouldContinue() {
            return hasAngerTime() && super.shouldContinue();
        }

        protected void setMobEntityTarget(MobEntity mob, LivingEntity target) {
            if (mob instanceof DripstoneTortoiseEntity && mob.canSee(target)) {
                mob.setTarget(target);
            }

        }
    }

    public void summonPike(Vec3d pos){
        float y = (float) pos.getY();

        BlockPos blockDownPos = new BlockPos(pos.getX(), y - 1, pos.getZ());

        while(y < world.getTopY() && !world.getBlockState(blockDownPos).isSolidSurface(world, blockDownPos, this, Direction.UP)){
            y -= 0.1F;

            blockDownPos = new BlockPos(blockDownPos.getX(), y, blockDownPos.getZ());
        }

        if(y >= world.getTopY()){
            y = (float) pos.getY();
        }

        DripstonePikeEntity spellPart = new DripstonePikeEntity(ModEntities.DRIPSTONE_PIKE, world);

        spellPart.setPos(pos.getX(), y, pos.getZ());

        spellPart.owner = this;

        world.spawnEntity(spellPart);
    }

    private class SpikeAttackGoal extends Goal {
        protected final PathAwareEntity mob;
        private final double speed;
        private final boolean pauseWhenMobIdle;
        private Path path;
        private double targetX;
        private double targetY;
        private double targetZ;
        private int updateCountdownTicks;
        private int cooldown;
        private long lastUpdateTime;

        protected double getSquaredMaxAttackDistance(LivingEntity entity) {
            return 20;
        }

        protected void attack(LivingEntity target, double squaredDistance) {
            double d = getSquaredMaxAttackDistance(target);
            if (squaredDistance <= d && this.cooldown <= 0) {
                this.resetCooldown();
                Vec3d targetPos = target.getPos();

                for(int i = 0; i < 10; i++){
                    summonPike(new Vec3d(random.nextBetween((int) -1.5F, (int) 1.5F) + targetPos.getX(),  targetPos.getY(), random.nextBetween((int) -1.5F, (int) 1.5F)  + targetPos.getZ()));
                }

                setShouldStomp(true);

                stompTimer = 10;

                world.playSound(null, new BlockPos(getPos()), SoundEvents.BLOCK_DRIPSTONE_BLOCK_BREAK, SoundCategory.HOSTILE, 1F, 1F);
            }
        }



        public SpikeAttackGoal(PathAwareEntity mob, double speed, boolean pauseWhenMobIdle) {
            this.mob = mob;
            this.speed = speed;
            this.pauseWhenMobIdle = pauseWhenMobIdle;
            this.setControls(EnumSet.of(Control.MOVE, Control.LOOK));
        }

        public boolean canStart() {
            long l = this.mob.world.getTime();
            if (l - this.lastUpdateTime < 20L) {
                return false;
            } else {
                this.lastUpdateTime = l;
                LivingEntity livingEntity = this.mob.getTarget();
                if (livingEntity == null) {
                    return false;
                } else if (!livingEntity.isAlive()) {
                    return false;
                } else {
                    this.path = this.mob.getNavigation().findPathTo(livingEntity, 0);
                    if (this.path != null) {
                        return true;
                    } else {
                        return this.getSquaredMaxAttackDistance(livingEntity) >= this.mob.squaredDistanceTo(livingEntity.getX(), livingEntity.getY(), livingEntity.getZ());
                    }
                }
            }
        }

        public boolean shouldContinue() {
            if(soothed > 0) return false;

            LivingEntity livingEntity = this.mob.getTarget();

            if (livingEntity == null) {
                return false;
            } else if (!livingEntity.isAlive()) {
                return false;
            } else if (!this.pauseWhenMobIdle) {
                return !this.mob.getNavigation().isIdle();
            } else if (!this.mob.isInWalkTargetRange(livingEntity.getBlockPos())) {
                return false;
            } else {
                return !(livingEntity instanceof PlayerEntity) || !livingEntity.isSpectator() && !((PlayerEntity)livingEntity).isCreative();
            }
        }

        public void start() {
            this.mob.getNavigation().startMovingAlong(this.path, this.speed);
            this.mob.setAttacking(true);
            this.updateCountdownTicks = 0;
            this.cooldown = 0;
        }

        public void stop() {
            LivingEntity livingEntity = this.mob.getTarget();

            if (!EntityPredicates.EXCEPT_CREATIVE_OR_SPECTATOR.test(livingEntity)) {
                this.mob.setTarget(null);
            }

            this.mob.setAttacking(false);
            this.mob.getNavigation().stop();
        }

        public boolean shouldRunEveryTick() {
            return true;
        }

        public void tick() {
            LivingEntity livingEntity = this.mob.getTarget();
            if (livingEntity != null) {
                this.mob.getLookControl().lookAt(livingEntity, 30.0F, 30.0F);
                double d = this.mob.squaredDistanceTo(livingEntity.getX(), livingEntity.getY(), livingEntity.getZ());
                this.updateCountdownTicks = Math.max(this.updateCountdownTicks - 1, 0);
                if ((this.pauseWhenMobIdle || this.mob.getVisibilityCache().canSee(livingEntity)) && this.updateCountdownTicks <= 0 && (this.targetX == 0.0D && this.targetY == 0.0D && this.targetZ == 0.0D || livingEntity.squaredDistanceTo(this.targetX, this.targetY, this.targetZ) >= 1.0D || this.mob.getRandom().nextFloat() < 0.05F)) {
                    this.targetX = livingEntity.getX();
                    this.targetY = livingEntity.getY();
                    this.targetZ = livingEntity.getZ();
                    this.updateCountdownTicks = 4 + this.mob.getRandom().nextInt(7);
                    if (d > 1024.0D) {
                        this.updateCountdownTicks += 10;
                    } else if (d > 256.0D) {
                        this.updateCountdownTicks += 5;
                    }

                    if (!this.mob.getNavigation().startMovingTo(livingEntity, this.speed)) {
                        this.updateCountdownTicks += 15;
                    }

                    this.updateCountdownTicks = this.getTickCount(this.updateCountdownTicks);
                }

                this.cooldown = Math.max(this.cooldown - 1, 0);
                this.attack(livingEntity, d);
            }
        }

        protected void resetCooldown() {
            this.cooldown = 20;
        }
    }

    private class RandomSpikeGoal extends Goal {
        private int cooldown;
        private long lastUpdateTime;

        protected void spike() {
            if (this.cooldown <= 0) {
                this.resetCooldown();
                Vec3d targetPos = getPos();

                for(int i = 0; i < 10; i++){
                    summonPike(new Vec3d(random.nextBetween((int) -1.5F, (int) 1.5F) + targetPos.getX(),  targetPos.getY(), random.nextBetween( (int) -1.5F,  (int) 1.5F)  + targetPos.getZ()));
                }

                setShouldStomp(true);

                stompTimer = 10;

                world.playSound(null, new BlockPos(getPos()), SoundEvents.BLOCK_DRIPSTONE_BLOCK_BREAK, SoundCategory.HOSTILE, 1F, 1F);
            }
        }

        public RandomSpikeGoal() {}

        public boolean canStart() {
            if(isAttacking()) return false;

            long l = world.getTime();
            if (l - this.lastUpdateTime < 20L) {
                return false;
            } else {
                this.lastUpdateTime = l;

                return true;
            }
        }

        public boolean shouldContinue() {
            return !isAttacking();
        }

        public void start() {
            this.cooldown = 0;
        }

        public void stop() {
        }

        public boolean shouldRunEveryTick() {
            return true;
        }

        public void tick() {
            this.cooldown = Math.max(this.cooldown - 1, 0);

            this.spike();
        }

        protected void resetCooldown() {
            this.cooldown = 400;
        }
    }
}
