package com.exdrill.cave_enhancements.entity;

import com.exdrill.cave_enhancements.entity.ai.goal.EatBlockGoal;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.particle.ItemStackParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.recipe.Ingredient;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;

import java.util.EnumSet;
import java.util.List;
import java.util.function.Predicate;

public class CruncherEntity extends PathAwareEntity {
    private static final TrackedData<Boolean> IS_EATING_BLOCK = DataTracker.registerData(CruncherEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    private static final TrackedData<Boolean> IS_SHEARED = DataTracker.registerData(CruncherEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    private static final Ingredient TEMPTING_ITEMS;
    public long lastEatTick;
    public int eatingTicks = 0;
    public int eatingAnimation = 0;
    public int eatingTime;
    public boolean hasItem;

    public CruncherEntity(EntityType<? extends PathAwareEntity> entityType, World world) {
        super(entityType, world);

        this.setCanPickUpLoot(true);
        this.experiencePoints = 5;
    }


    // NBT Data
    public void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(IS_EATING_BLOCK, false);
        this.dataTracker.startTracking(IS_SHEARED, false);
    }

    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        nbt.putLong("lastEatTick", this.lastEatTick);
        nbt.putInt("eatingTicks", this.eatingTicks);
        nbt.putBoolean("isEatingBlock", this.isEating());
        nbt.putBoolean("isSheared", this.hasBeenSheared());
    }
    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        this.lastEatTick = nbt.getLong("lastEatTick");
        this.eatingTicks = nbt.getInt("eatingTicks");
        isEatingBlock(nbt.getBoolean("isEatingBlock"));
        isSheared(nbt.getBoolean("isSheared"));
    }

    // Eating Block NBT
    public void isEatingBlock(boolean isEating) {
        this.dataTracker.set(IS_EATING_BLOCK, isEating);
    }

    public boolean isEating() {
        return this.dataTracker.get(IS_EATING_BLOCK);
    }

    // Sheared NBT
    public void isSheared(boolean isSheared) {
        this.dataTracker.set(IS_SHEARED, isSheared);
    }

    public boolean hasBeenSheared() {
        return this.dataTracker.get(IS_SHEARED);
    }


    // Ticking
    @Override
    public void tick() {
        ItemStack itemStack = this.getEquippedStack(EquipmentSlot.MAINHAND);
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
        if (itemStack.isOf(Items.GLOW_BERRIES) && !hasItem) {
            hasItem = true;
        }
        if (itemStack.isEmpty() && hasItem) {
            hasItem = false;
            this.eatingTicks = 1200;
            System.out.println("Finished Eating?");
        }

        super.tick();
    }

    public int tickTimer() {
        return age;
    }

    @Override
    protected boolean shouldSwimInFluids() {
        return true;
    }

    // Goals
    protected void initGoals() {
        this.goalSelector.add(0, new SwimGoal(this));
        this.goalSelector.add(5, new WanderAroundFarGoal(this, 1.0D));
        this.goalSelector.add(6, new LookAtEntityGoal(this, PlayerEntity.class, 8F));
        this.goalSelector.add(6, new LookAroundGoal(this));
        this.goalSelector.add(0, new EatBlockGoal(this));
        this.goalSelector.add(3, new TemptGoal(this, 1.25, TEMPTING_ITEMS, false));
        this.goalSelector.add(2, new CruncherEntity.PickupItemGoal());
    }

    // Interactions
    public ActionResult interactMob(PlayerEntity player, Hand hand) {
        ItemStack itemStack = player.getStackInHand(hand);
        ItemEntity itemEntity = new ItemEntity(this.world, this.getX(), this.getY(), this.getZ(), Items.MOSS_CARPET.getDefaultStack());
        if (itemStack.isOf(Items.SHEARS) && !this.hasBeenSheared() && this.isAlive()) {
            this.isSheared(true);
            itemStack.damage(1, player, (playerx) -> playerx.sendToolBreakStatus(hand));
            this.world.spawnEntity(itemEntity);
            this.world.playSoundFromEntity(null, this, SoundEvents.ENTITY_SHEEP_SHEAR, SoundCategory.PLAYERS, 1.0F, 1.0F);
            this.emitGameEvent(GameEvent.SHEAR, player);
            return ActionResult.SUCCESS;
        }
        if (itemStack.isOf(Items.GLOW_BERRIES) && this.eatingTicks == 0) {
            if (!this.world.isClient) {
                this.eatingTicks = 1200;
                itemStack.decrement(1);
                this.emitGameEvent(GameEvent.ENTITY_INTERACT, player);
                return ActionResult.SUCCESS;
            } else {
                return ActionResult.CONSUME;
            }
        } else {
            return super.interactMob(player, hand);
        }
    }


    // Leash
    public Vec3d getLeashOffset() {
        return new Vec3d(0.0D, 0.6F * this.getStandingEyeHeight(), this.getWidth() * 0.4F);
    }

    @Override
    public boolean canBeLeashedBy(PlayerEntity player) {
        return true;
    }

    // Attributes
    public static DefaultAttributeContainer.Builder createCruncherAttributes() {
        return HostileEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.15D)
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 15);
    }

    // Can Pick Up Items
    public boolean canPickupItem(ItemStack stack) {
        ItemStack itemStack = this.getEquippedStack(EquipmentSlot.MAINHAND);
        return itemStack.isEmpty() && this.eatingTime > 0 && this.eatingTicks == 0 && stack.isOf(Items.GLOW_BERRIES);
    }

    boolean wantsToPickupItem() {
        ItemStack itemStack = this.getEquippedStack(EquipmentSlot.MAINHAND);
        return itemStack.isOf(Items.GLOW_BERRIES);
    }

    private boolean canEat(ItemStack stack) {
        return stack.getItem().isFood() && this.onGround;
    }
    // Can Drop Items
    private void spit(ItemStack stack) {
        if (!stack.isEmpty() && !this.world.isClient) {
            ItemEntity itemEntity = new ItemEntity(this.world, this.getX() + this.getRotationVector().x, this.getY() + 1.0D, this.getZ() + this.getRotationVector().z, stack);
            itemEntity.setPickupDelay(40);
            itemEntity.setThrower(this.getUuid());
            this.world.spawnEntity(itemEntity);
        }
    }
    private void dropItem(ItemStack stack) {
        ItemEntity itemEntity = new ItemEntity(this.world, this.getX(), this.getY(), this.getZ(), stack);
        this.world.spawnEntity(itemEntity);
    }

    protected void loot(ItemEntity item) {
        ItemStack itemStack = item.getStack();
        if (this.canPickupItem(itemStack)) {
            int i = itemStack.getCount();
            if (i > 1) {
                this.dropItem(itemStack.split(i - 1));
            }

            this.spit(this.getEquippedStack(EquipmentSlot.MAINHAND));
            this.triggerItemPickedUpByEntityCriteria(item);
            this.equipStack(EquipmentSlot.MAINHAND, itemStack.split(1));
            this.handDropChances[EquipmentSlot.MAINHAND.getEntitySlotId()] = 2.0F;
            this.sendPickup(item, itemStack.getCount());
            item.discard();
            this.eatingTime = 0;
        }

    }

    public void tickMovement() {
        if (!this.world.isClient && this.isAlive() && this.canMoveVoluntarily()) {
            ++this.eatingTime;
            ItemStack itemStack = this.getEquippedStack(EquipmentSlot.MAINHAND);
            if (this.canEat(itemStack)) {
                if (this.eatingTime > 600) {
                    ItemStack itemStack2 = itemStack.finishUsing(this.world, this);
                    if (!itemStack2.isEmpty()) {
                        this.equipStack(EquipmentSlot.MAINHAND, itemStack2);
                    }

                    this.eatingTime = 0;
                } else if (this.eatingTime > 560 && this.random.nextFloat() < 0.1F) {
                    this.playSound(this.getEatSound(itemStack), 1.0F, 1.0F);
                    this.world.sendEntityStatus(this, (byte)45);
                }
            }
        }
        super.tickMovement();
    }

    class PickupItemGoal extends Goal {
        public PickupItemGoal() {
            this.setControls(EnumSet.of(Control.MOVE));
        }

        public boolean canStart() {
            if (!CruncherEntity.this.getEquippedStack(EquipmentSlot.MAINHAND).isEmpty()) {
                return false;
            } else {
                if (!CruncherEntity.this.wantsToPickupItem()) {
                    return false;
                } else if (CruncherEntity.this.getRandom().nextInt(toGoalTicks(10)) != 0) {
                    return false;
                } else {
                    List<ItemEntity> list = CruncherEntity.this.world.getEntitiesByClass(ItemEntity.class, CruncherEntity.this.getBoundingBox().expand(8.0D, 8.0D, 8.0D), CruncherEntity.PICKABLE_DROP_FILTER);
                    return !list.isEmpty() && CruncherEntity.this.getEquippedStack(EquipmentSlot.MAINHAND).isEmpty();
                }
            }
        }

        public void tick() {
            List<ItemEntity> list = CruncherEntity.this.world.getEntitiesByClass(ItemEntity.class, CruncherEntity.this.getBoundingBox().expand(8.0D, 8.0D, 8.0D), CruncherEntity.PICKABLE_DROP_FILTER);
            ItemStack itemStack = CruncherEntity.this.getEquippedStack(EquipmentSlot.MAINHAND);
            if (itemStack.isEmpty() && !list.isEmpty()) {
                CruncherEntity.this.getNavigation().startMovingTo(list.get(0), 1.0D);
            }

        }

        public void start() {
            List<ItemEntity> list = CruncherEntity.this.world.getEntitiesByClass(ItemEntity.class, CruncherEntity.this.getBoundingBox().expand(8.0D, 8.0D, 8.0D), CruncherEntity.PICKABLE_DROP_FILTER);
            if (!list.isEmpty()) {
                CruncherEntity.this.getNavigation().startMovingTo(list.get(0), 1.0D);
            }

        }
    }

    public void handleStatus(byte status) {
        if (status == 45) {
            ItemStack itemStack = this.getEquippedStack(EquipmentSlot.MAINHAND);
            if (!itemStack.isEmpty()) {
                for(int i = 0; i < 8; ++i) {
                    Vec3d vec3d = (new Vec3d(((double)this.random.nextFloat() - 0.5D) * 0.1D, Math.random() * 0.1D + 0.1D, 0.0D)).rotateX(-this.getPitch() * 0.017453292F).rotateY(-this.getYaw() * 0.017453292F);
                    this.world.addParticle(new ItemStackParticleEffect(ParticleTypes.ITEM, itemStack), this.getX() + this.getRotationVector().x / 2.0D, this.getY(), this.getZ() + this.getRotationVector().z / 2.0D, vec3d.x, vec3d.y + 0.05D, vec3d.z);
                }
            }
        } else {
            super.handleStatus(status);
        }

    }

    static final Predicate<ItemEntity> PICKABLE_DROP_FILTER;

    static {
        TEMPTING_ITEMS = Ingredient.ofItems(Items.GLOW_BERRIES);
        PICKABLE_DROP_FILTER = (item) -> !item.cannotPickup() && item.isAlive() && item.getStack().getItem() == Items.GLOW_BERRIES;
    }
}
