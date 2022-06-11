package com.exdrill.cave_enhancements.entity;

import com.exdrill.cave_enhancements.registry.ModTags;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.entity.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.CreeperEntity;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Arm;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DripstonePikeEntity extends MobEntity {

    public int dieTimer = 20;
    public int damageDelay = 4;
    public boolean didDamage = false;
    public LivingEntity owner;
    public boolean checkedSight =  false;

    public final AnimationState risingAnimationState = new AnimationState();

    private static final TrackedData<Boolean> INVULNERABLE = DataTracker.registerData(DripstonePikeEntity.class, TrackedDataHandlerRegistry.BOOLEAN);

    public DripstonePikeEntity(EntityType<? extends DripstonePikeEntity> entityType, World world) {
        super(entityType, world);

        noClip = true;
    }

    public void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(INVULNERABLE, true);
    }

    public boolean isInvulnerableTo(DamageSource damageSource) {
        return damageSource != DamageSource.OUT_OF_WORLD;
    }

    public boolean isInvulnerable() {
        return this.dataTracker.get(INVULNERABLE);
    }

    @Override
    public Iterable<ItemStack> getArmorItems() {
        return new ArrayList<ItemStack>() {};
    }

    @Override
    public ItemStack getEquippedStack(EquipmentSlot slot) {
        return new ItemStack(Items.DIAMOND, 0);
    }

    @Override
    public void equipStack(EquipmentSlot slot, ItemStack stack) {

    }

    @Nullable
    @Override
    public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason, @Nullable EntityData entityData, @Nullable NbtCompound entityNbt) {



        return super.initialize(world, difficulty, spawnReason, entityData, entityNbt);
    }

    @Override
    public Arm getMainArm() {
        return Arm.RIGHT;
    }

    public static DefaultAttributeContainer.Builder createDripstonePikeAttributes() {
        return HostileEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 1)
                .add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, 1);
    }

    @Override
    public boolean hasNoGravity() {
        return true;
    }

    @Override
    public void tick() {
        super.tick();

        if (this.age == 5) {
            BlockPos pos = new BlockPos(this.getX(), this.getY(), this.getZ());
            Random random = world.getRandom();

            if (!(random.nextFloat() < 0.9f)) {
                if (world.getBlockState(pos.up()).isIn(ModTags.PIKE_DESTROYABLES) || world.getBlockState(pos.down()).isIn(ModTags.PIKE_DESTROYABLES)) {
                    world.playSound(null, pos, SoundEvents.BLOCK_POINTED_DRIPSTONE_LAND, SoundCategory.BLOCKS, 1.0F, 1.0F);
                    ItemEntity itemEntity = new ItemEntity(this.world, this.getX(), this.getY(), this.getZ(), Items.POINTED_DRIPSTONE.getDefaultStack());
                    this.world.syncWorldEvent(2001, pos, Block.getRawIdFromState(Blocks.POINTED_DRIPSTONE.getDefaultState()));
                    this.world.spawnEntity(itemEntity);
                    this.discard();
                }
            }

        }


        if (this.world.isClient) {
            this.risingAnimationState.startIfNotRunning(this.age);
        }
        if(!world.isClient()) {
            if(!checkedSight){
                checkedSight = true;

                if(owner != null && !canSee(owner)){
                    discard();
                }
            }

            damageDelay--;

            if(!didDamage && damageDelay <= 0){
                didDamage = true;

                Box box = new Box(new BlockPos(getPos().getX(), getPos().getY(), getPos().getZ())).expand(.1);

                List<Entity> list = world.getEntitiesByClass(Entity.class, box, (e) -> LivingEntity.class.isAssignableFrom(e.getClass()));

                Entity otherEntity;

                for (Entity entity : list) {
                    otherEntity = entity;

                    otherEntity.damage(DamageSource.mobProjectile(this, owner), 8);

                    if (otherEntity instanceof CreeperEntity) {
                        otherEntity.damage(DamageSource.mobProjectile(this, owner), 20);
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
