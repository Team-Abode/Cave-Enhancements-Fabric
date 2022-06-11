package com.exdrill.cave_enhancements.entity.ai.goal;

import com.exdrill.cave_enhancements.item.AmethystFluteItem;
import org.jetbrains.annotations.Nullable;

import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.util.DefaultRandomPos;
import net.minecraft.world.level.pathfinder.Path;
import net.minecraft.world.phys.Vec3;
import java.util.EnumSet;
import java.util.function.Predicate;

public class FleeTheFluteGoal<T extends Entity> extends Goal {
    protected final PathfinderMob mob;
    private final double walkSpeedModifier;
    private final double sprintSpeedModifier;
    @Nullable
    protected Entity toAvoid;
    protected final float maxDist;
    @Nullable
    protected Path path;
    protected final PathNavigation pathNav;
    protected final Predicate<Entity> avoidPredicate;

    public FleeTheFluteGoal(PathfinderMob mob, float distance, double slowSpeed, double fastSpeed) {
        this(mob, EntitySelector.NO_CREATIVE_OR_SPECTATOR, distance, slowSpeed, fastSpeed);
    }

    @ParametersAreNonnullByDefault
    public FleeTheFluteGoal(PathfinderMob mob, Predicate<Entity> avoidPredicate, float maxDist, double slowSpeed, double fastSpeed) {
        this.mob = mob;
        this.avoidPredicate = avoidPredicate;
        this.maxDist = maxDist;
        this.walkSpeedModifier = slowSpeed;
        this.sprintSpeedModifier = fastSpeed;
        this.pathNav = mob.getNavigation();
        this.setFlags(EnumSet.of(Flag.MOVE));
    }

    @Override
    public boolean canUse() {
        this.mob.level.getEntities(this.mob, this.mob.getBoundingBox().inflate(this.maxDist, 3.0D, this.maxDist), this.mob::hasLineOfSight).forEach(entity -> {
            if (entity instanceof LivingEntity livingEntity && AmethystFluteItem.isScary(livingEntity)) {
                if (this.toAvoid != null) {
                    if (this.toAvoid.distanceToSqr(this.mob) > entity.distanceToSqr(this.mob)) this.toAvoid = entity;
                } else this.toAvoid = entity;
            }
        });

        if (this.toAvoid == null) {
            return false;
        } else {
            Vec3 vec3d = DefaultRandomPos.getPosAway(this.mob, 16, 7, this.toAvoid.position());
            if (vec3d == null) {
                return false;
            } else if (this.toAvoid.distanceToSqr(vec3d.x, vec3d.y, vec3d.z) < this.toAvoid.distanceToSqr(this.mob)) {
                return false;
            } else {
                this.path = this.pathNav.createPath(vec3d.x, vec3d.y, vec3d.z, 0);
                return this.path != null;
            }
        }
    }

    public boolean canContinueToUse() {
        return !this.pathNav.isDone();
    }

    public void start() {
        this.pathNav.moveTo(this.path, this.walkSpeedModifier);
    }

    public void stop() {
        this.toAvoid = null;
    }

    public void tick() {
        if (this.toAvoid != null && this.mob.distanceToSqr(this.toAvoid) < 49.0D) {
            this.mob.getNavigation().setSpeedModifier(this.sprintSpeedModifier);
        } else {
            this.mob.getNavigation().setSpeedModifier(this.walkSpeedModifier);
        }
    }
}
