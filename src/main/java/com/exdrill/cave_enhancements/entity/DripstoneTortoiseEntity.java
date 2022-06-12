package com.exdrill.cave_enhancements.entity;

import com.exdrill.cave_enhancements.registry.ModEntities;
import com.exdrill.cave_enhancements.registry.ModSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Difficulty;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.NeutralMob;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.Path;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.EnumSet;
import java.util.UUID;

public class DripstoneTortoiseEntity extends PathfinderMob implements NeutralMob {
    private static final EntityDataAccessor<Integer> ANGER = SynchedEntityData.defineId(DripstoneTortoiseEntity.class, EntityDataSerializers.INT);

    private static final EntityDataAccessor<Boolean> SHOULD_STOMP = SynchedEntityData.defineId(DripstoneTortoiseEntity.class, EntityDataSerializers.BOOLEAN);

    public final AnimationState stompingAnimationState = new AnimationState();

    public int stompTimer;

    public int soothed;

    @Nullable
    private UUID angryAt;

    public DripstoneTortoiseEntity(EntityType<? extends PathfinderMob> entityType, Level world) {
        super(entityType, world);
        this.xpReward = 30;
    }

    //NBT
    protected void defineSynchedData() {
        super.defineSynchedData();

        this.entityData.define(ANGER, 0);
        this.entityData.define(SHOULD_STOMP, false);
    }

    public void addAdditionalSaveData(CompoundTag nbt) {
        super.addAdditionalSaveData(nbt);

        nbt.putBoolean("shouldStomp", getShouldStomp());

        addPersistentAngerSaveData(nbt);
    }

    public void readAdditionalSaveData(CompoundTag nbt) {
        super.readAdditionalSaveData(nbt);

        setShouldStomp(nbt.getBoolean("shouldStomp"));

        readPersistentAngerSaveData(this.level, nbt);
    }

    public void setShouldStomp(boolean shouldStomp){
        entityData.set(SHOULD_STOMP, shouldStomp);
    }

    public boolean getShouldStomp(){
        return entityData.get(SHOULD_STOMP);
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
    protected void registerGoals() {
        this.targetSelector.addGoal(1, (new DripstoneTortoiseRevengeGoal(this)).setAlertOthers(new Class[0]));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal(this, Player.class, 10, true, false, this::shouldAngerAt));
        this.goalSelector.addGoal(4, new SpikeAttackGoal(this, 1.5D, true));
        this.goalSelector.addGoal(5, new RandomStrollGoal(this, 1.5D));
        this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 8F));
        this.goalSelector.addGoal(6, new RandomLookAroundGoal(this));
        this.targetSelector.addGoal(6, new RandomSpikeGoal());
    }

    public static AttributeSupplier.Builder createDripstoneTortoiseAttributes() {
        return PathfinderMob.createMobAttributes()
                .add(Attributes.MOVEMENT_SPEED, 0.125D)
                .add(Attributes.MAX_HEALTH, 40)
                .add(Attributes.ARMOR, 5)
                .add(Attributes.ARMOR_TOUGHNESS, 2)
                .add(Attributes.KNOCKBACK_RESISTANCE, 0.6)
                .add(Attributes.ATTACK_DAMAGE, 3);
    }

    @Override
    public boolean isInvulnerableTo(DamageSource damageSource) {
        if(damageSource == DamageSource.STALAGMITE || damageSource == DamageSource.FALLING_STALACTITE || damageSource.isProjectile() || damageSource.getEntity() instanceof DripstoneTortoiseEntity) return true;

        return super.isInvulnerableTo(damageSource);
    }

    public boolean shouldAngerAt(Object entity) {
        if (!this.canAttack((LivingEntity)entity)) {
            return false;
        } else {
            return ((LivingEntity) entity).getType() == EntityType.PLAYER && this.isAngryAtAllPlayers(((LivingEntity) entity).level) || ((LivingEntity) entity).getUUID().equals(this.getPersistentAngerTarget());
        }
    }



    @Override
    public void tick() {
        if (this.level.isClientSide) {
            if (this.getShouldStomp()) {
                this.stompingAnimationState.startIfStopped(this.tickCount);
            } else {
                this.stompingAnimationState.stop();
            }
        }
        super.tick();
    }



    @Override
    public boolean canBeLeashed(Player player) {
        return false;
    }

    @Override
    protected void moveTowardsClosestSpace(double x, double y, double z) {
        super.moveTowardsClosestSpace(x, y, z);
    }

    //Anger
    public int getRemainingPersistentAngerTime() {
        return this.entityData.get(ANGER);
    }

    public void setRemainingPersistentAngerTime(int angerTime) {
        this.entityData.set(ANGER, angerTime);
    }

    public void startPersistentAngerTimer() {
        this.setRemainingPersistentAngerTime(400);
    }

    @Nullable
    public UUID getPersistentAngerTarget() {
        return this.angryAt;
    }

    public void setPersistentAngerTarget(@Nullable UUID angryAt) {
        this.angryAt = angryAt;
    }

    public void sooth(){
        soothed = 2;
        setPersistentAngerTarget(null);
        setRemainingPersistentAngerTime(0);
    }

    //Tick
    @Override
    protected void customServerAiStep() {
        if (!this.level.isClientSide) {
            this.updatePersistentAnger((ServerLevel)this.level, false);

            stompTimer--;

            if(stompTimer <= 0){
                setShouldStomp(false);
            }

            soothed--;
        }
    }

    public static boolean isSpawnDark(ServerLevelAccessor world, BlockPos pos, RandomSource random) {
        if (world.getBrightness(LightLayer.SKY, pos) > random.nextInt(32)) {
            return false;
        } else if (world.getBrightness(LightLayer.BLOCK, pos) > 0) {
            return false;
        } else {
            int i = world.getLevel().isThundering() ? world.getMaxLocalRawBrightness(pos, 10) : world.getMaxLocalRawBrightness(pos);
            return i <= random.nextInt(8);
        }
    }

    public static boolean canSpawnInDark(EntityType<? extends PathfinderMob> type, ServerLevelAccessor world, MobSpawnType spawnReason, BlockPos pos, RandomSource random) {
        return world.getDifficulty() != Difficulty.PEACEFUL && isSpawnDark(world, pos, random) && checkMobSpawnRules(type, world, spawnReason, pos, random);
    }


    //Goals
    private class DripstoneTortoiseRevengeGoal extends HurtByTargetGoal {
        DripstoneTortoiseRevengeGoal(DripstoneTortoiseEntity dripstoneTortoise) {
            super(dripstoneTortoise);
        }

        public boolean canContinueToUse() {
            return isAngry() && super.canContinueToUse();
        }

        protected void alertOther(Mob mob, LivingEntity target) {
            if (mob instanceof DripstoneTortoiseEntity && mob.hasLineOfSight(target)) {
                mob.setTarget(target);
            }

        }
    }

    public void summonPike(Vec3 pos){
        float y = (float) pos.y();

        BlockPos blockDownPos = new BlockPos(pos.x(), y - 1, pos.z());

        while(y < level.getMaxBuildHeight() && !level.getBlockState(blockDownPos).entityCanStandOnFace(level, blockDownPos, this, Direction.UP)){
            y -= 0.1F;

            blockDownPos = new BlockPos(blockDownPos.getX(), y, blockDownPos.getZ());
        }

        if(y >= level.getMaxBuildHeight()){
            y = (float) pos.y();
        }

        DripstonePikeEntity spellPart = new DripstonePikeEntity(ModEntities.DRIPSTONE_PIKE, level);

        spellPart.setPosRaw(pos.x(), y, pos.z());

        spellPart.owner = this;

        level.addFreshEntity(spellPart);
    }

    private class SpikeAttackGoal extends Goal {
        protected final PathfinderMob mob;
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
                Vec3 targetPos = target.position();

                for(int i = 0; i < 10; i++){
                    summonPike(new Vec3(random.nextIntBetweenInclusive((int) -1.5F, (int) 1.5F) + targetPos.x(),  targetPos.y(), random.nextIntBetweenInclusive((int) -1.5F, (int) 1.5F)  + targetPos.z()));
                }

                setShouldStomp(true);

                stompTimer = 10;

                level.playSound(null, new BlockPos(position()), SoundEvents.DRIPSTONE_BLOCK_BREAK, SoundSource.HOSTILE, 1F, 1F);
            }
        }



        public SpikeAttackGoal(PathfinderMob mob, double speed, boolean pauseWhenMobIdle) {
            this.mob = mob;
            this.speed = speed;
            this.pauseWhenMobIdle = pauseWhenMobIdle;
            this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
        }

        public boolean canUse() {
            long l = this.mob.level.getGameTime();
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
                    this.path = this.mob.getNavigation().createPath(livingEntity, 0);
                    if (this.path != null) {
                        return true;
                    } else {
                        return this.getSquaredMaxAttackDistance(livingEntity) >= this.mob.distanceToSqr(livingEntity.getX(), livingEntity.getY(), livingEntity.getZ());
                    }
                }
            }
        }

        public boolean canContinueToUse() {
            if(soothed > 0) return false;

            LivingEntity livingEntity = this.mob.getTarget();

            if (livingEntity == null) {
                return false;
            } else if (!livingEntity.isAlive()) {
                return false;
            } else if (!this.pauseWhenMobIdle) {
                return !this.mob.getNavigation().isDone();
            } else if (!this.mob.isWithinRestriction(livingEntity.blockPosition())) {
                return false;
            } else {
                return !(livingEntity instanceof Player) || !livingEntity.isSpectator() && !((Player)livingEntity).isCreative();
            }
        }

        public void start() {
            this.mob.getNavigation().moveTo(this.path, this.speed);
            this.mob.setAggressive(true);
            this.updateCountdownTicks = 0;
            this.cooldown = 0;
        }

        public void stop() {
            LivingEntity livingEntity = this.mob.getTarget();

            if (!EntitySelector.NO_CREATIVE_OR_SPECTATOR.test(livingEntity)) {
                this.mob.setTarget(null);
            }

            this.mob.setAggressive(false);
            this.mob.getNavigation().stop();
        }

        public boolean requiresUpdateEveryTick() {
            return true;
        }

        public void tick() {
            LivingEntity livingEntity = this.mob.getTarget();
            if (livingEntity != null) {
                this.mob.getLookControl().setLookAt(livingEntity, 30.0F, 30.0F);
                double d = this.mob.distanceToSqr(livingEntity.getX(), livingEntity.getY(), livingEntity.getZ());
                this.updateCountdownTicks = Math.max(this.updateCountdownTicks - 1, 0);
                if ((this.pauseWhenMobIdle || this.mob.getSensing().hasLineOfSight(livingEntity)) && this.updateCountdownTicks <= 0 && (this.targetX == 0.0D && this.targetY == 0.0D && this.targetZ == 0.0D || livingEntity.distanceToSqr(this.targetX, this.targetY, this.targetZ) >= 1.0D || this.mob.getRandom().nextFloat() < 0.05F)) {
                    this.targetX = livingEntity.getX();
                    this.targetY = livingEntity.getY();
                    this.targetZ = livingEntity.getZ();
                    this.updateCountdownTicks = 4 + this.mob.getRandom().nextInt(7);
                    if (d > 1024.0D) {
                        this.updateCountdownTicks += 10;
                    } else if (d > 256.0D) {
                        this.updateCountdownTicks += 5;
                    }

                    if (!this.mob.getNavigation().moveTo(livingEntity, this.speed)) {
                        this.updateCountdownTicks += 15;
                    }

                    this.updateCountdownTicks = this.adjustedTickDelay(this.updateCountdownTicks);
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
                Vec3 targetPos = position();

                for(int i = 0; i < 10; i++){
                    summonPike(new Vec3(random.nextIntBetweenInclusive((int) -1.5F, (int) 1.5F) + targetPos.x(),  targetPos.y(), random.nextIntBetweenInclusive( (int) -1.5F,  (int) 1.5F)  + targetPos.z()));
                }

                setShouldStomp(true);

                stompTimer = 10;

                level.playSound(null, new BlockPos(position()), SoundEvents.DRIPSTONE_BLOCK_BREAK, SoundSource.HOSTILE, 1F, 1F);
            }
        }

        public RandomSpikeGoal() {}

        public boolean canUse() {
            if(isAggressive()) return false;

            long l = level.getGameTime();
            if (l - this.lastUpdateTime < 20L) {
                return false;
            } else {
                this.lastUpdateTime = l;

                return true;
            }
        }

        public boolean canContinueToUse() {
            return !isAggressive();
        }

        public void start() {
            this.cooldown = 0;
        }

        public void stop() {
        }

        public boolean requiresUpdateEveryTick() {
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
