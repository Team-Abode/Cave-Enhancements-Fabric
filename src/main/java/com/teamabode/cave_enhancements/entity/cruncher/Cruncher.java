package com.teamabode.cave_enhancements.entity.cruncher;

import com.teamabode.cave_enhancements.CaveEnhancements;
import com.teamabode.cave_enhancements.entity.cruncher.goals.CruncherEatBlockGoal;
import com.teamabode.cave_enhancements.entity.cruncher.goals.CruncherMoveToItemGoal;
import com.teamabode.cave_enhancements.entity.cruncher.goals.CruncherOreSearchGoal;
import com.teamabode.cave_enhancements.entity.cruncher.goals.CruncherRandomStrollGoal;
import com.teamabode.cave_enhancements.registry.ModEntities;
import com.teamabode.cave_enhancements.registry.ModTags;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import org.jetbrains.annotations.Nullable;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.function.Predicate;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class Cruncher extends Animal {

    private static final EntityDataAccessor<Boolean> IS_SEARCHING = SynchedEntityData.defineId(Cruncher.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> CAN_MINE = SynchedEntityData.defineId(Cruncher.class, EntityDataSerializers.BOOLEAN);

    public static final Predicate<ItemEntity> GLOW_BERRIES_ONLY = (itemEntity -> itemEntity.isAlive() && !itemEntity.hasPickUpDelay() && itemEntity.getItem().is(Items.GLOW_BERRIES));
    private int ticksSinceEaten = 0;

    //public final AnimationState walkAnimationState = new AnimationState();

    public Cruncher(EntityType<? extends Animal> entityType, Level level) {
        super(entityType, level);
        this.setCanPickUpLoot(true);
    }

    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(0, new CruncherOreSearchGoal(this));
        this.goalSelector.addGoal(0, new CruncherEatBlockGoal(this));
        this.goalSelector.addGoal(1, new PanicGoal(this, 1.5F));
        this.goalSelector.addGoal(1, new LookAtPlayerGoal(this, Player.class, 5.0F));
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
    }

    public boolean isSearching() {
        return this.entityData.get(IS_SEARCHING);
    }

    public void setSearching(boolean value) {
        this.entityData.set(IS_SEARCHING, value);
    }

    public boolean canMine() { return this.entityData.get(CAN_MINE); }

    public void setCanMine(Boolean value) { this.entityData.set(CAN_MINE, value); }

    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putBoolean("IsSearching", isSearching());
        compound.putBoolean("CanMine", canMine());
    }

    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        setSearching(compound.getBoolean("IsSearching"));
        setCanMine(compound.getBoolean("CanMine"));
    }

    // Animation Conditions
    /*
    private boolean isMovingOnLand() {
        return this.onGround && this.getDeltaMovement().horizontalDistanceSqr() > 1.0E-6 && !this.isInWaterOrBubble();
    }

    // Tick
    public void tick() {
        if (this.level.isClientSide()) {
            if (this.isMovingOnLand()) {
                this.walkAnimationState.start(tickCount);
            } else {
                this.walkAnimationState.stop();
            }
        }
        super.tick();
    }
     */

    // Ai Step
    public void aiStep() {
        if (!this.level.isClientSide() && this.isAlive() && this.isEffectiveAi()) {
            ++this.ticksSinceEaten;
            ItemStack itemStack = this.getItemBySlot(EquipmentSlot.MAINHAND);
            if (itemStack.is(Items.GLOW_BERRIES)) {
                if (this.ticksSinceEaten > 200) {
                    ItemStack itemStack2 = itemStack.finishUsingItem(this.level, this);
                    if (!itemStack2.isEmpty()) {
                        this.setItemSlot(EquipmentSlot.MAINHAND, itemStack2);
                    }

                    this.ticksSinceEaten = 0;
                    this.setSearching(true);
                    CaveEnhancements.LOGGER.info("Consumed");
                } else if (this.ticksSinceEaten > 160 && this.random.nextFloat() < 0.1F) {
                    this.playSound(this.getEatingSound(itemStack), 1.0F, 1.0F);
                    this.level.broadcastEntityEvent(this, (byte)45);
                }
            }
        }
        super.aiStep();
    }

    public boolean canHoldItem(ItemStack stack) {
        ItemStack itemStack = this.getItemBySlot(EquipmentSlot.MAINHAND);
        return itemStack.isEmpty() && stack.is(Items.GLOW_BERRIES) && ticksSinceEaten > 0 && !canMine() && !isSearching();
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
