package com.teamabode.cave_enhancements.entity.dripstone_tortoise;

import com.teamabode.cave_enhancements.registry.ModTags;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.AABB;
import org.jetbrains.annotations.Nullable;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.ArrayList;
import java.util.List;

public class DripstonePike extends Mob {

    public int dieTimer = 20;
    public int damageDelay = 4;
    public boolean didDamage = false;
    public LivingEntity owner;
    public boolean checkedSight =  false;

    public final AnimationState risingAnimationState = new AnimationState();

    private static final EntityDataAccessor<Boolean> INVULNERABLE = SynchedEntityData.defineId(DripstonePike.class, EntityDataSerializers.BOOLEAN);

    public DripstonePike(EntityType<? extends DripstonePike> entityType, Level world) {
        super(entityType, world);

        noPhysics = true;
    }

    public void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(INVULNERABLE, true);
    }

    @Override
    @ParametersAreNonnullByDefault
    public boolean isInvulnerableTo(DamageSource damageSource) {
        return damageSource != DamageSource.OUT_OF_WORLD;
    }

    @Override

    public boolean isInvulnerable() {
        return this.entityData.get(INVULNERABLE);
    }

    @Override
    public Iterable<ItemStack> getArmorSlots() {
        return new ArrayList<ItemStack>() {};
    }

    @Override
    public ItemStack getItemBySlot(EquipmentSlot slot) {
        return new ItemStack(Items.DIAMOND, 0);
    }

    @Override
    public void setItemSlot(EquipmentSlot slot, ItemStack stack) {

    }

    @Nullable
    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor world, DifficultyInstance difficulty, MobSpawnType spawnReason, @Nullable SpawnGroupData entityData, @Nullable CompoundTag entityNbt) {



        return super.finalizeSpawn(world, difficulty, spawnReason, entityData, entityNbt);
    }

    @Override
    public HumanoidArm getMainArm() {
        return HumanoidArm.RIGHT;
    }

    public static AttributeSupplier.Builder createDripstonePikeAttributes() {
        return Monster.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 1)
                .add(Attributes.KNOCKBACK_RESISTANCE, 1);
    }

    @Override
    public boolean isNoGravity() {
        return true;
    }

    @Override
    public void tick() {
        super.tick();

        if (this.tickCount == 5) {
            BlockPos pos = new BlockPos(this.getX(), this.getY(), this.getZ());
            RandomSource random = level.getRandom();

            if (random.nextInt(50) == 0) {
                if (level.getBlockState(pos.above()).is(ModTags.PIKE_DESTROYABLES) || level.getBlockState(pos.below()).is(ModTags.PIKE_DESTROYABLES)) {
                    level.playSound(null, pos, SoundEvents.POINTED_DRIPSTONE_LAND, SoundSource.BLOCKS, 1.0F, 1.0F);
                    ItemEntity itemEntity = new ItemEntity(this.level, this.getX(), this.getY(), this.getZ(), Items.POINTED_DRIPSTONE.getDefaultInstance());
                    this.level.levelEvent(2001, pos, Block.getId(Blocks.POINTED_DRIPSTONE.defaultBlockState()));
                    this.level.addFreshEntity(itemEntity);
                    this.discard();
                }
            }

        }


        if (this.level.isClientSide) {
            this.risingAnimationState.startIfStopped(this.tickCount);
        }
        if(!level.isClientSide()) {
            if(!checkedSight){
                checkedSight = true;

                if(owner != null && !hasLineOfSight(owner)){
                    discard();
                }
            }

            damageDelay--;

            if(!didDamage && damageDelay <= 0){
                didDamage = true;

                AABB box = new AABB(new BlockPos(position().x(), position().y(), position().z())).inflate(.1);

                List<Entity> list = level.getEntitiesOfClass(Entity.class, box, (e) -> LivingEntity.class.isAssignableFrom(e.getClass()));

                Entity otherEntity;

                for (Entity entity : list) {
                    otherEntity = entity;

                    otherEntity.hurt(DamageSource.STALAGMITE, 8);

                    if (otherEntity instanceof Creeper) {
                        otherEntity.hurt(DamageSource.STALAGMITE, 20);
                    }
                }
            }

            dieTimer--;

            if (dieTimer <= 0) {
                discard();
            }
        }
    }
}
