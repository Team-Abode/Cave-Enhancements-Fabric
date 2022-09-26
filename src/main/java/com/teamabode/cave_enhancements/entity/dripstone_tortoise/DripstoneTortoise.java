package com.teamabode.cave_enhancements.entity.dripstone_tortoise;

import com.teamabode.cave_enhancements.entity.dripstone_tortoise.goals.DripstoneTortoiseHurtByTargetGoal;
import com.teamabode.cave_enhancements.entity.dripstone_tortoise.goals.DripstoneTortoiseRandomSpikeGoal;
import com.teamabode.cave_enhancements.entity.dripstone_tortoise.goals.DripstoneTortoiseSpikeAttackGoal;
import com.teamabode.cave_enhancements.registry.ModEntities;
import com.teamabode.cave_enhancements.registry.ModSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Difficulty;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.NeutralMob;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.UUID;

@ParametersAreNonnullByDefault
public class DripstoneTortoise extends PathfinderMob implements NeutralMob {
    private static final EntityDataAccessor<Integer> ANGER = SynchedEntityData.defineId(DripstoneTortoise.class, EntityDataSerializers.INT);

    private static final EntityDataAccessor<Boolean> SHOULD_STOMP = SynchedEntityData.defineId(DripstoneTortoise.class, EntityDataSerializers.BOOLEAN);

    public final AnimationState stompingAnimationState = new AnimationState();

    public int stompTimer;

    public int soothed;

    @Nullable
    private UUID angryAt;

    public DripstoneTortoise(EntityType<? extends PathfinderMob> entityType, Level world) {
        super(entityType, world);
        this.xpReward = 15;
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
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, Player.class, 10, true, false, this::shouldAngerAt));
        this.goalSelector.addGoal(4, new DripstoneTortoiseSpikeAttackGoal(this, 1.5D, true));
        this.goalSelector.addGoal(5, new RandomStrollGoal(this, 1.5D));
        this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 8F));
        this.goalSelector.addGoal(6, new RandomLookAroundGoal(this));
        this.targetSelector.addGoal(6, new DripstoneTortoiseRandomSpikeGoal(this));
        this.targetSelector.addGoal(1, (new DripstoneTortoiseHurtByTargetGoal(this)).setAlertOthers());
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
        if(damageSource == DamageSource.STALAGMITE || damageSource == DamageSource.FALLING_STALACTITE || damageSource.isProjectile() || damageSource.getEntity() instanceof DripstoneTortoise) return true;

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

    public static boolean isDarkEnoughToSpawn(ServerLevelAccessor serverLevelAccessor, BlockPos blockPos, RandomSource randomSource) {
        if (serverLevelAccessor.getBrightness(LightLayer.SKY, blockPos) > randomSource.nextInt(32)) {
            return false;
        } else {
            DimensionType dimensionType = serverLevelAccessor.dimensionType();
            int i = dimensionType.monsterSpawnBlockLightLimit();
            if (i < 15 && serverLevelAccessor.getBrightness(LightLayer.BLOCK, blockPos) > i) {
                return false;
            } else {
                int j = serverLevelAccessor.getLevel().isThundering() ? serverLevelAccessor.getMaxLocalRawBrightness(blockPos, 10) : serverLevelAccessor.getMaxLocalRawBrightness(blockPos);
                return j <= dimensionType.monsterSpawnLightTest().sample(randomSource);
            }
        }
    }

    public static boolean checkDripstoneTortoiseSpawnRules(EntityType<? extends DripstoneTortoise> entityType, ServerLevelAccessor serverLevelAccessor, MobSpawnType mobSpawnType, BlockPos blockPos, RandomSource randomSource) {
        return serverLevelAccessor.getDifficulty() != Difficulty.PEACEFUL && isDarkEnoughToSpawn(serverLevelAccessor, blockPos, randomSource) && checkMobSpawnRules(entityType, serverLevelAccessor, mobSpawnType, blockPos, randomSource);
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
        DripstoneTortoise dripstoneTortoise = this;

        Vec3 vec3 = dripstoneTortoise.getDeltaMovement();
        dripstoneTortoise.setDeltaMovement(vec3.x, 0.15, vec3.z);

        DripstonePike dripstonePike = new DripstonePike(ModEntities.DRIPSTONE_PIKE, level);

        dripstonePike.setPosRaw(pos.x(), y, pos.z());

        dripstonePike.owner = this;


        level.addFreshEntity(dripstonePike);
    }
}
