package com.teamabode.cave_enhancements.entity.cruncher;

import com.teamabode.cave_enhancements.CaveEnhancements;
import com.teamabode.cave_enhancements.entity.cruncher.goals.*;
import com.teamabode.cave_enhancements.registry.ModEntities;
import com.teamabode.cave_enhancements.registry.ModTags;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.PanicGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.TemptGoal;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Optional;
import java.util.function.Predicate;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class Cruncher extends Animal {

    private static final EntityDataAccessor<Boolean> IS_SEARCHING = SynchedEntityData.defineId(Cruncher.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> CAN_MINE = SynchedEntityData.defineId(Cruncher.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> SHEARED = SynchedEntityData.defineId(Cruncher.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Integer> SEARCH_COOLDOWN_TIME = SynchedEntityData.defineId(Cruncher.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Optional<BlockPos>> TARGET_POS = SynchedEntityData.defineId(Cruncher.class, EntityDataSerializers.OPTIONAL_BLOCK_POS);

    public final AnimationState chompAnimationState = new AnimationState();
    public final AnimationState walkAnimationState = new AnimationState();

    public static final Predicate<ItemEntity> GLOW_BERRIES_ONLY = (itemEntity -> itemEntity.isAlive() && !itemEntity.hasPickUpDelay() && itemEntity.getItem().is(Items.GLOW_BERRIES));
    private int ticksSinceEaten = 0;

    public Cruncher(EntityType<? extends Animal> entityType, Level level) {
        super(entityType, level);
        this.setCanPickUpLoot(true);
    }

    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new CruncherOreSearchGoal(this));
        this.goalSelector.addGoal(1, new CruncherEatBlockGoal(this));
        this.goalSelector.addGoal(1, new PanicGoal(this, 1.5F));
        this.goalSelector.addGoal(1, new CruncherLookAtPlayerGoal(this));
        this.goalSelector.addGoal(1, new CruncherMoveToItemGoal(this));
        this.goalSelector.addGoal(2, new CruncherRandomStrollGoal(this));
        this.goalSelector.addGoal(3, new TemptGoal(this, 1.25, Ingredient.of(Items.GLOW_BERRIES), false));
        this.goalSelector.addGoal(7, new RandomLookAroundGoal(this));
    }

    // Save Data
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(IS_SEARCHING, false);
        this.entityData.define(CAN_MINE, false);
        this.entityData.define(SHEARED, false);
        this.entityData.define(SEARCH_COOLDOWN_TIME, 0);
        this.entityData.define(TARGET_POS, Optional.empty());
    }

    public boolean isSearching() {
        return this.entityData.get(IS_SEARCHING);
    }

    public void setSearching(boolean value) {
        this.entityData.set(IS_SEARCHING, value);
    }

    public boolean canMine() { return this.entityData.get(CAN_MINE); }

    public void setCanMine(Boolean value) { this.entityData.set(CAN_MINE, value); }

    @Nullable
    public BlockPos getTargetPos() {
        return this.entityData.get(TARGET_POS).orElse(null);
    }
    public void setTargetPos(@Nullable BlockPos value) {
        this.entityData.set(TARGET_POS, value == null ? Optional.empty() : Optional.of(value));
    }

    public int getSearchCooldownTime() { return this.entityData.get(SEARCH_COOLDOWN_TIME); }

    public void setSearchCooldownTime(int value) { this.entityData.set(SEARCH_COOLDOWN_TIME, value); }

    public boolean isSheared() {
        return this.entityData.get(SHEARED);
    }

    public void setSheared(boolean value) {
        this.entityData.set(SHEARED, value);
    }

    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putBoolean("IsSearching", isSearching());
        compound.putBoolean("CanMine", canMine());
        compound.putBoolean("Sheared", isSheared());
        compound.putInt("SearchCooldownTime", getSearchCooldownTime());
    }

    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        setSearching(compound.getBoolean("IsSearching"));
        setCanMine(compound.getBoolean("CanMine"));
        setSheared(compound.getBoolean("Sheared"));
        setSearchCooldownTime(compound.getInt("SearchCooldownTime"));
    }

    public InteractionResult mobInteract(Player player, InteractionHand hand) {
        if (player.getItemInHand(hand).is(Items.GLOW_BERRIES) && this.getMainHandItem().isEmpty() && !this.isSearching() && !this.canMine() && this.getSearchCooldownTime() == 0) {
            ItemStack itemStack = player.getItemInHand(hand);
            this.setSearching(true);
            this.eatingEffects(itemStack);

            if (!player.getAbilities().instabuild) {
                itemStack.shrink(1);
            }

            return InteractionResult.SUCCESS;
        } else if (player.getItemInHand(hand).is(Items.SHEARS) && !this.isSheared()) {
            this.setSheared(true);
            return InteractionResult.SUCCESS;
        } else {
            return super.mobInteract(player, hand);
        }
    }

    private boolean isMovingOnLand() {
        return this.onGround && this.getDeltaMovement().horizontalDistanceSqr() > 1.0E-6 && !this.isInWaterOrBubble();
    }

    public void tick() {
        if (this.getSearchCooldownTime() > 0 && this.tickCount % 20 == 0) {
            this.setSearchCooldownTime(getSearchCooldownTime() - 1);
        }
        if (this.getPose() == Pose.DIGGING) {
            if (level.isClientSide()) {
                chompAnimationState.startIfStopped(this.tickCount);
            }

        } else {
            if (level.isClientSide()) {
                chompAnimationState.stop();
            }
        }
        if (level.isClientSide()) {
            if (isMovingOnLand()) {
                walkAnimationState.startIfStopped(this.tickCount);
            } else {
                walkAnimationState.stop();
            }
        }
        super.tick();
    }

    // Ai Step
    public void aiStep() {
        if (!this.level.isClientSide() && this.isAlive() && this.isEffectiveAi()) {
            ++this.ticksSinceEaten;
            ItemStack itemStack = this.getItemBySlot(EquipmentSlot.MAINHAND);
            if (itemStack.is(Items.GLOW_BERRIES) && this.getSearchCooldownTime() == 0) {
                if (this.ticksSinceEaten > 200) {
                    ItemStack itemStack2 = itemStack.finishUsingItem(this.level, this);
                    if (!itemStack2.isEmpty()) {
                        this.setItemSlot(EquipmentSlot.MAINHAND, itemStack2);
                    }

                    this.ticksSinceEaten = 0;
                    this.setSearching(true);
                    CaveEnhancements.LOGGER.info("Consumed");
                } else if (this.ticksSinceEaten > 160 && this.random.nextBoolean()) {
                    this.eatingEffects(itemStack);
                }
            }
        }
        super.aiStep();
    }

    private void eatingEffects(ItemStack itemStack) {
        this.playSound(this.getEatingSound(itemStack), 1.0F, 1.0F);
        this.level.broadcastEntityEvent(this, (byte)45);
    }

    public void handleEntityEvent(byte id) {
        if (id == 45) {
            ItemStack itemStack = this.getItemBySlot(EquipmentSlot.MAINHAND);
            if (!itemStack.isEmpty()) {
                for(int i = 0; i < 8; ++i) {
                    Vec3 vec3 = (new Vec3(((double)this.random.nextFloat() - 0.5) * 0.1, Math.random() * 0.1 + 0.1, 0.0)).xRot(-this.getXRot() * 0.017453292F).yRot(-this.getYRot() * 0.017453292F);
                    this.level.addParticle(new ItemParticleOption(ParticleTypes.ITEM, itemStack), this.getX() + this.getLookAngle().x / 2.0, this.getY(), this.getZ() + this.getLookAngle().z / 2.0, vec3.x, vec3.y + 0.05, vec3.z);
                }
            }
        } else {
            super.handleEntityEvent(id);
        }
    }

    public boolean canHoldItem(ItemStack stack) {
        ItemStack itemStack = this.getItemBySlot(EquipmentSlot.MAINHAND);
        return itemStack.isEmpty() && stack.is(Items.GLOW_BERRIES) && ticksSinceEaten > 0 && !this.canMine() && !this.isSearching() && this.getSearchCooldownTime() == 0;
    }

    private void spitOutItem(ItemStack stack) {
        if (!stack.isEmpty() && !this.level.isClientSide) {
            ItemEntity itemEntity = new ItemEntity(this.level, this.getX() + this.getLookAngle().x, this.getY() + 1.0, this.getZ() + this.getLookAngle().z, stack);
            itemEntity.setPickUpDelay(40);
            itemEntity.setThrower(this.getUUID());

            this.playSound(SoundEvents.FOX_SPIT, 1.0F, 1.0F);
            this.level.addFreshEntity(itemEntity);
        }
    }

    private void dropItemStack(ItemStack stack) {
        ItemEntity itemEntity = new ItemEntity(this.level, this.getX(), this.getY(), this.getZ(), stack);
        this.level.addFreshEntity(itemEntity);
    }

    protected void pickUpItem(ItemEntity itemEntity) {
        ItemStack itemStack = itemEntity.getItem();
        if (this.canHoldItem(itemStack)) {
            int i = itemStack.getCount();
            if (i > 1) {
                this.dropItemStack(itemStack.split(i - 1));
            }
            this.spitOutItem(this.getItemBySlot(EquipmentSlot.MAINHAND));
            this.onItemPickup(itemEntity);
            this.setItemSlot(EquipmentSlot.MAINHAND, itemStack.split(1));
            this.setGuaranteedDrop(EquipmentSlot.MAINHAND);
            this.take(itemEntity, itemStack.getCount());
            itemEntity.discard();
            ticksSinceEaten = 0;
        }

    }

    // Spawn Rules
    public static boolean checkCruncherSpawnRules(EntityType<? extends Animal> entityType, LevelAccessor levelAccessor, MobSpawnType mobSpawnType, BlockPos blockPos, RandomSource randomSource) {
        return levelAccessor.getBlockState(blockPos.below()).is(ModTags.CRUNCHERS_SPAWNABLE_ON) && levelAccessor.getRawBrightness(blockPos, 0) >= 12;
    }

    // Attribute Builder
    public static AttributeSupplier.Builder createCruncherAttributes() {
        return Mob.createMobAttributes().add(Attributes.MAX_HEALTH, 15.0D).add(Attributes.ATTACK_DAMAGE, 5.0D).add(Attributes.MOVEMENT_SPEED, 0.2D);
    }

    @Nullable
    public Cruncher getBreedOffspring(ServerLevel level, AgeableMob otherParent) {
        return ModEntities.CRUNCHER.create(level);
    }
}
