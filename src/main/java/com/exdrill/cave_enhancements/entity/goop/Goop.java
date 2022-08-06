package com.exdrill.cave_enhancements.entity.goop;

import com.exdrill.cave_enhancements.registry.ModItems;
import com.exdrill.cave_enhancements.registry.ModSounds;
import com.exdrill.cave_enhancements.registry.ModTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.material.PushReaction;
import org.jetbrains.annotations.Nullable;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public class Goop extends Monster implements GoopBucketable {
    private static final EntityDataAccessor<Boolean> FROM_BUCKET = SynchedEntityData.defineId(Goop.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> STICKING_UP = SynchedEntityData.defineId(Goop.class, EntityDataSerializers.BOOLEAN);

    public Level level;

    public Goop(EntityType<? extends Monster> entityType, Level world) {
        super(entityType, world);
        this.xpReward = 5;
        this.level = world;
    }

    // Sounds
    protected SoundEvent getDeathSound() {
        return ModSounds.ENTITY_GOOP_DEATH;
    }

    protected SoundEvent getHurtSound(DamageSource source) {
        return ModSounds.ENTITY_GOOP_HURT;
    }

    // Attributes
    public static AttributeSupplier.Builder createGoopAttributes() {
        return Monster.createMonsterAttributes()
                .add(Attributes.MOVEMENT_SPEED, 0.0D)
                .add(Attributes.KNOCKBACK_RESISTANCE, 1.0)
                .add(Attributes.MAX_HEALTH, 15)
                .add(Attributes.ARMOR, 2);
    }

    // Mob Group
    public MobType getMobType() {
        return MobType.UNDEFINED;
    }

    // Nbt Related Stuff
    public void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(FROM_BUCKET, false);
        this.entityData.define(STICKING_UP, false);
    }

    public void addAdditionalSaveData(CompoundTag nbt) {
        super.addAdditionalSaveData(nbt);

        nbt.putBoolean("FromBucket", this.isFromBucket());
        nbt.putBoolean("StickingUp", this.isStickingUp());
    }

    public void readAdditionalSaveData(CompoundTag nbt) {
        super.readAdditionalSaveData(nbt);

        setFromBucket(nbt.getBoolean("FromBucket"));
        setStickingUp(nbt.getBoolean("StickingUp"));
    }

    public void copyDataToStack(ItemStack stack) {
        GoopBucketable.copyDataToStack(this, stack);
        stack.getOrCreateTag();
    }

    @Override
    public void copyDataFromNbt(CompoundTag nbt) {
        GoopBucketable.copyDataFromNbt(this, nbt);

        if (nbt.contains("StickingUp")) {
            this.isStickingUp();
        }
    }

    public boolean isFromBucket() {
        return this.entityData.get(FROM_BUCKET);
    }
    public boolean isStickingUp() {
        return this.entityData.get(STICKING_UP);
    }

    public void setFromBucket(boolean fromBucket) {
        this.entityData.set(FROM_BUCKET, fromBucket);
    }

    public void setStickingUp(boolean stickingUp) {
        this.entityData.set(STICKING_UP, stickingUp);
    }

    // Components
    public boolean canBreatheUnderwater() {
        return false;
    }

    @Override
    public boolean isPushable() {
        return false;
    }

    @Override
    public boolean causeFallDamage(float fallDistance, float multiplier, DamageSource source) {
        return false;
    }

    @Override
    public PushReaction getPistonPushReaction() {
        return PushReaction.BLOCK;
    }

    // Bucket Components
    public InteractionResult mobInteract(Player player, InteractionHand hand) {
        return GoopBucketable.tryBucket(player, hand, this).orElse(super.mobInteract(player, hand));
    }
    public ItemStack getBucketItem() {
        return new ItemStack(ModItems.GOOP_BUCKET);
    }

    public SoundEvent getBucketedSound() {
        return ModSounds.ITEM_BUCKET_FILL_GOOP;
    }

    // Despawn Components
    public boolean requiresCustomPersistence() {
        return super.requiresCustomPersistence() || this.isFromBucket();
    }

    public boolean removeWhenFarAway(double distanceSquared) {
        return !this.isFromBucket() && !this.hasCustomName();
    }


    //Spawn Event
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor serverWorld, DifficultyInstance difficulty, MobSpawnType spawnReason, @Nullable SpawnGroupData entityData, @Nullable CompoundTag entityNbt) {
        level = serverWorld.getLevel();
        double x = this.getX();
        double y = this.getY();
        double z = this.getZ();
        double origY = y;
        BlockPos blockUpPos = new BlockPos(x, y + 1, z);
        if (spawnReason != MobSpawnType.NATURAL) {
            if (serverWorld.getBlockState(blockUpPos).entityCanStandOnFace(level, blockUpPos, this, Direction.DOWN)) {
                setStickingUp(true);
            }
        }
        if (spawnReason == MobSpawnType.NATURAL) {
            System.out.println("Spawning goop");
            if (!level.isClientSide()) {
                while(y < level.getMaxBuildHeight() && !serverWorld.getBlockState(blockUpPos).entityCanStandOnFace(level, blockUpPos, this, Direction.DOWN)){
                    x = this.getX();
                    y = this.getY();
                    z = this.getZ();
                    teleportToWithTicket(x, y + 0.1D, z);
                    y = this.getY();
                    blockUpPos = new BlockPos(x, y + 1, z);
                }
                if(y >= level.getMaxBuildHeight()){
                    y = origY;
                    teleportToWithTicket(x, y + 0.1D, z);
                }
            }


            setStickingUp(true);
        }
        if (spawnReason == MobSpawnType.BUCKET) {
            return entityData;
        }
        return super.finalizeSpawn(serverWorld, difficulty, spawnReason, entityData, entityNbt);
    }

    public static boolean checkGoopSpawnRules(EntityType<? extends Monster> entityType, LevelAccessor levelAccessor, MobSpawnType mobSpawnType, BlockPos blockPos, RandomSource randomSource) {
        return levelAccessor.getBlockState(blockPos.below()).is(ModTags.GOOP_SPAWNABLE_ON);
    }

    //Tick for checking if sticking up
    @Override
    public void aiStep() {
        if(!level.isClientSide){
            if(isStickingUp()) {
                this.setDeltaMovement(this.getDeltaMovement().multiply(0D, 0D, 0D));

                if (level != null) {
                    double x = this.getX();
                    double y = this.getY();
                    double z = this.getZ();

                    BlockPos blockUpPos = new BlockPos(x, y + 1, z);

                    if(!level.getBlockState(blockUpPos).entityCanStandOnFace(level, blockUpPos, this, Direction.DOWN)){
                        setStickingUp(false);
                    }
                }
            }
        }
        super.aiStep();
    }

    public int dripCooldown = 12;



    //Tick For Spawning Drip
    @Override
    public void customServerAiStep() {
       dripCooldown--;

       if(dripCooldown <= 0){
           dripCooldown = 12;

           if(getRandom().nextIntBetweenInclusive(1, 100) == 1) {
               drip();
           }
       }

       super.customServerAiStep();
    }

    //Spawn Drip Entity
    public void drip(){
        if(this.isStickingUp()) {
            double x = this.getX();
            double y = this.getY();
            double z = this.getZ();

            BigGoopDripProjectile bigGoopDripEntity = new BigGoopDripProjectile(level, x, y, z);
            level.addFreshEntity(bigGoopDripEntity);
        }
    }
}
