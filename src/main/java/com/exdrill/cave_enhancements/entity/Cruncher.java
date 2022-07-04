package com.exdrill.cave_enhancements.entity;

import com.exdrill.cave_enhancements.entity.ai.goal.EatBlockGoal;
import com.exdrill.cave_enhancements.registry.ModTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
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
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.EnumSet;
import java.util.List;
import java.util.function.Predicate;

@ParametersAreNonnullByDefault
public class Cruncher extends Animal {

    private static final EntityDataAccessor<Boolean> IS_EATING_BLOCK = SynchedEntityData.defineId(Cruncher.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> IS_SHEARED = SynchedEntityData.defineId(Cruncher.class, EntityDataSerializers.BOOLEAN);
    private static final Ingredient TEMPTING_ITEMS;
    public long lastEatTick;
    public int eatingTicks = 0;
    public int eatingAnimation = 0;
    public int eatingTime;
    public boolean hasItem;

    public Cruncher(EntityType<? extends Animal> entityType, Level world) {
        super(entityType, world);

        this.setCanPickUpLoot(true);
        this.xpReward = 5;
    }


    @Nullable
    @Override
    public AgeableMob getBreedOffspring(ServerLevel serverLevel, AgeableMob ageableMob) {
        return null;
    }

    // NBT Data
    public void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(IS_EATING_BLOCK, false);
        this.entityData.define(IS_SHEARED, false);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag nbt) {
        super.addAdditionalSaveData(nbt);
        nbt.putLong("lastEatTick", this.lastEatTick);
        nbt.putInt("eatingTicks", this.eatingTicks);
        nbt.putBoolean("isEatingBlock", this.isEating());
        nbt.putBoolean("isSheared", this.hasBeenSheared());
    }
    @Override
    public void readAdditionalSaveData(CompoundTag nbt) {
        super.readAdditionalSaveData(nbt);
        this.lastEatTick = nbt.getLong("lastEatTick");
        this.eatingTicks = nbt.getInt("eatingTicks");
        isEatingBlock(nbt.getBoolean("isEatingBlock"));
        isSheared(nbt.getBoolean("isSheared"));
    }

    // Eating Block NBT
    public void isEatingBlock(boolean isEating) {
        this.entityData.set(IS_EATING_BLOCK, isEating);
    }

    public boolean isEating() {
        return this.entityData.get(IS_EATING_BLOCK);
    }

    // Sheared NBT
    public void isSheared(boolean isSheared) {
        this.entityData.set(IS_SHEARED, isSheared);
    }

    public boolean hasBeenSheared() {
        return this.entityData.get(IS_SHEARED);
    }


    public static boolean checkCruncherSpawnRules(EntityType<? extends Animal> entityType, LevelAccessor levelAccessor, MobSpawnType mobSpawnType, BlockPos blockPos, RandomSource randomSource) {
        return levelAccessor.getBlockState(blockPos.below()).is(ModTags.CRUNCHERS_SPAWNABLE_ON) && isBrightEnoughToSpawn(levelAccessor, blockPos);
    }


    // Ticking
    @Override
    public void tick() {
        ItemStack itemStack = this.getItemBySlot(EquipmentSlot.MAINHAND);
        if (this.eatingTicks > 0) {
            this.eatingTicks--;
        }
        if (this.isEating() && this.eatingAnimation < 9) {
            this.eatingAnimation = 10;
        }
        if (this.eatingAnimation >= 10 && this.eatingAnimation < 25) {
            this.eatingAnimation++;
        }
        if (this.eatingAnimation == 25) {
            this.eatingAnimation = 0;
            this.isEatingBlock(false);
        }
        if (itemStack.is(Items.GLOW_BERRIES) && !hasItem) {
            hasItem = true;
        }
        if (itemStack.isEmpty() && hasItem) {
            hasItem = false;
            this.eatingTicks = 1200;
            System.out.println("Finished Eating?");
        }

        super.tick();
    }



    @Override
    protected boolean isAffectedByFluids() {
        return true;
    }

    // Goals
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(5, new WaterAvoidingRandomStrollGoal(this, 1.0D));
        this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 8F));
        this.goalSelector.addGoal(6, new RandomLookAroundGoal(this));
        this.goalSelector.addGoal(0, new EatBlockGoal(this));
        this.goalSelector.addGoal(3, new TemptGoal(this, 1.25, TEMPTING_ITEMS, false));
        this.goalSelector.addGoal(2, new Cruncher.PickupItemGoal());
    }

    // Interactions
    public InteractionResult mobInteract(Player player, InteractionHand hand) {
        ItemStack itemStack = player.getItemInHand(hand);
        ItemEntity itemEntity = new ItemEntity(this.level, this.getX(), this.getY(), this.getZ(), Items.MOSS_CARPET.getDefaultInstance());
        if (itemStack.is(Items.SHEARS) && !this.hasBeenSheared() && this.isAlive()) {
            this.isSheared(true);
            itemStack.hurtAndBreak(1, player, (playerx) -> playerx.broadcastBreakEvent(hand));
            this.level.addFreshEntity(itemEntity);
            this.level.playSound(null, this, SoundEvents.SHEEP_SHEAR, SoundSource.PLAYERS, 1.0F, 1.0F);
            this.gameEvent(GameEvent.SHEAR, player);
            return InteractionResult.SUCCESS;
        }
        if (itemStack.is(Items.GLOW_BERRIES) && this.eatingTicks == 0) {
            if (!this.level.isClientSide) {
                this.eatingTicks = 1200;
                itemStack.shrink(1);
                this.gameEvent(GameEvent.ENTITY_INTERACT, player);
                return InteractionResult.SUCCESS;
            } else {
                return InteractionResult.CONSUME;
            }
        } else {
            return super.mobInteract(player, hand);
        }
    }


    // Leash
    public Vec3 getLeashOffset() {
        return new Vec3(0.0D, 0.6F * this.getEyeHeight(), this.getBbWidth() * 0.4F);
    }

    @Override
    public boolean canBeLeashed(Player player) {
        return true;
    }

    // Attributes
    public static AttributeSupplier.Builder createCruncherAttributes() {
        return Animal.createMobAttributes()
                .add(Attributes.MOVEMENT_SPEED, 0.15D)
                .add(Attributes.MAX_HEALTH, 15);
    }

    // Can Pick Up Items
    public boolean canHoldItem(ItemStack stack) {
        ItemStack itemStack = this.getItemBySlot(EquipmentSlot.MAINHAND);
        return itemStack.isEmpty() && this.eatingTime > 0 && this.eatingTicks == 0 && stack.is(Items.GLOW_BERRIES);
    }

    boolean wantsToPickupItem() {
        ItemStack itemStack = this.getItemBySlot(EquipmentSlot.MAINHAND);
        return itemStack.is(Items.GLOW_BERRIES);
    }

    private boolean canEat(ItemStack stack) {
        return stack.getItem().isEdible() && this.onGround;
    }
    // Can Drop Items
    private void spit(ItemStack stack) {
        if (!stack.isEmpty() && !this.level.isClientSide) {
            ItemEntity itemEntity = new ItemEntity(this.level, this.getX() + this.getLookAngle().x, this.getY() + 1.0D, this.getZ() + this.getLookAngle().z, stack);
            itemEntity.setPickUpDelay(40);
            itemEntity.setThrower(this.getUUID());
            this.level.addFreshEntity(itemEntity);
        }
    }
    private void dropItem(ItemStack stack) {
        ItemEntity itemEntity = new ItemEntity(this.level, this.getX(), this.getY(), this.getZ(), stack);
        this.level.addFreshEntity(itemEntity);
    }

    protected void pickUpItem(ItemEntity item) {
        ItemStack itemStack = item.getItem();
        if (this.canHoldItem(itemStack)) {
            int i = itemStack.getCount();
            if (i > 1) {
                this.dropItem(itemStack.split(i - 1));
            }

            this.spit(this.getItemBySlot(EquipmentSlot.MAINHAND));
            this.onItemPickup(item);
            this.setItemSlot(EquipmentSlot.MAINHAND, itemStack.split(1));
            this.handDropChances[EquipmentSlot.MAINHAND.getIndex()] = 2.0F;
            this.take(item, itemStack.getCount());
            item.discard();
            this.eatingTime = 0;
        }

    }

    public void aiStep() {
        if (!this.level.isClientSide && this.isAlive() && this.isEffectiveAi()) {
            ++this.eatingTime;
            ItemStack itemStack = this.getItemBySlot(EquipmentSlot.MAINHAND);
            if (this.canEat(itemStack)) {
                if (this.eatingTime > 600) {
                    ItemStack itemStack2 = itemStack.finishUsingItem(this.level, this);
                    if (!itemStack2.isEmpty()) {
                        this.setItemSlot(EquipmentSlot.MAINHAND, itemStack2);
                    }

                    this.eatingTime = 0;
                } else if (this.eatingTime > 560 && this.random.nextFloat() < 0.1F) {
                    this.playSound(this.getEatingSound(itemStack), 1.0F, 1.0F);
                    this.level.broadcastEntityEvent(this, (byte)45);
                }
            }
        }
        super.aiStep();
    }

    class PickupItemGoal extends Goal {
        public PickupItemGoal() {
            this.setFlags(EnumSet.of(Flag.MOVE));
        }

        public boolean canUse() {
            if (!Cruncher.this.getItemBySlot(EquipmentSlot.MAINHAND).isEmpty()) {
                return false;
            } else {
                if (!Cruncher.this.wantsToPickupItem()) {
                    return false;
                } else if (Cruncher.this.getRandom().nextInt(reducedTickDelay(10)) != 0) {
                    return false;
                } else {
                    List<ItemEntity> list = Cruncher.this.level.getEntitiesOfClass(ItemEntity.class, Cruncher.this.getBoundingBox().inflate(8.0D, 8.0D, 8.0D), Cruncher.PICKABLE_DROP_FILTER);
                    return !list.isEmpty() && Cruncher.this.getItemBySlot(EquipmentSlot.MAINHAND).isEmpty();
                }
            }
        }

        public void tick() {
            List<ItemEntity> list = Cruncher.this.level.getEntitiesOfClass(ItemEntity.class, Cruncher.this.getBoundingBox().inflate(8.0D, 8.0D, 8.0D), Cruncher.PICKABLE_DROP_FILTER);
            ItemStack itemStack = Cruncher.this.getItemBySlot(EquipmentSlot.MAINHAND);
            if (itemStack.isEmpty() && !list.isEmpty()) {
                Cruncher.this.getNavigation().moveTo(list.get(0), 1.0D);
            }

        }

        public void start() {
            List<ItemEntity> list = Cruncher.this.level.getEntitiesOfClass(ItemEntity.class, Cruncher.this.getBoundingBox().inflate(8.0D, 8.0D, 8.0D), Cruncher.PICKABLE_DROP_FILTER);
            if (!list.isEmpty()) {
                Cruncher.this.getNavigation().moveTo(list.get(0), 1.0D);
            }

        }
    }

    public void handleEntityEvent(byte status) {
        if (status == 45) {
            ItemStack itemStack = this.getItemBySlot(EquipmentSlot.MAINHAND);
            if (!itemStack.isEmpty()) {
                for(int i = 0; i < 8; ++i) {
                    Vec3 vec3d = (new Vec3(((double)this.random.nextFloat() - 0.5D) * 0.1D, Math.random() * 0.1D + 0.1D, 0.0D)).xRot(-this.getXRot() * 0.017453292F).yRot(-this.getYRot() * 0.017453292F);
                    this.level.addParticle(new ItemParticleOption(ParticleTypes.ITEM, itemStack), this.getX() + this.getLookAngle().x / 2.0D, this.getY(), this.getZ() + this.getLookAngle().z / 2.0D, vec3d.x, vec3d.y + 0.05D, vec3d.z);
                }
            }
        } else {
            super.handleEntityEvent(status);
        }

    }

    static final Predicate<ItemEntity> PICKABLE_DROP_FILTER;

    static {
        TEMPTING_ITEMS = Ingredient.of(Items.GLOW_BERRIES);
        PICKABLE_DROP_FILTER = (item) -> !item.hasPickUpDelay() && item.isAlive() && item.getItem().getItem() == Items.GLOW_BERRIES;
    }
}
