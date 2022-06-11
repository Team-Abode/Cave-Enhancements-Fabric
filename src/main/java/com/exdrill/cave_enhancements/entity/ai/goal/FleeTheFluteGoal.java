package com.exdrill.cave_enhancements.entity.ai.goal;

import com.exdrill.cave_enhancements.item.AmethystFluteItem;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.NoPenaltyTargeting;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.pathing.EntityNavigation;
import net.minecraft.entity.ai.pathing.Path;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.util.math.Vec3d;
import org.jetbrains.annotations.Nullable;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.EnumSet;
import java.util.function.Predicate;

public class FleeTheFluteGoal<T extends Entity> extends Goal {
    protected final PathAwareEntity mob;
    private final double walkSpeedModifier;
    private final double sprintSpeedModifier;
    @Nullable
    protected Entity toAvoid;
    protected final float maxDist;
    @Nullable
    protected Path path;
    protected final EntityNavigation pathNav;
    protected final Predicate<Entity> avoidPredicate;

    public FleeTheFluteGoal(PathAwareEntity mob, float distance, double slowSpeed, double fastSpeed) {
        this(mob, EntityPredicates.EXCEPT_CREATIVE_OR_SPECTATOR, distance, slowSpeed, fastSpeed);
    }

    @ParametersAreNonnullByDefault
    public FleeTheFluteGoal(PathAwareEntity mob, Predicate<Entity> avoidPredicate, float maxDist, double slowSpeed, double fastSpeed) {
        this.mob = mob;
        this.avoidPredicate = avoidPredicate;
        this.maxDist = maxDist;
        this.walkSpeedModifier = slowSpeed;
        this.sprintSpeedModifier = fastSpeed;
        this.pathNav = mob.getNavigation();
        this.setControls(EnumSet.of(Control.MOVE));
    }

    @Override
    public boolean canStart() {
        this.mob.world.getOtherEntities(this.mob, this.mob.getBoundingBox().expand(this.maxDist, 3.0D, this.maxDist), this.mob::canSee).forEach(entity -> {
            if (entity instanceof LivingEntity livingEntity && AmethystFluteItem.isScary(livingEntity)) {
                if (this.toAvoid != null) {
                    if (this.toAvoid.squaredDistanceTo(this.mob) > entity.squaredDistanceTo(this.mob)) this.toAvoid = entity;
                } else this.toAvoid = entity;
            }
        });

        if (this.toAvoid == null) {
            return false;
        } else {
            Vec3d vec3d = NoPenaltyTargeting.findFrom(this.mob, 16, 7, this.toAvoid.getPos());
            if (vec3d == null) {
                return false;
            } else if (this.toAvoid.squaredDistanceTo(vec3d.x, vec3d.y, vec3d.z) < this.toAvoid.squaredDistanceTo(this.mob)) {
                return false;
            } else {
                this.path = this.pathNav.findPathTo(vec3d.x, vec3d.y, vec3d.z, 0);
                return this.path != null;
            }
        }
    }

    public boolean shouldContinue() {
        return !this.pathNav.isIdle();
    }

    public void start() {
        this.pathNav.startMovingAlong(this.path, this.walkSpeedModifier);
    }

    public void stop() {
        this.toAvoid = null;
    }

    public void tick() {
        if (this.toAvoid != null && this.mob.squaredDistanceTo(this.toAvoid) < 49.0D) {
            this.mob.getNavigation().setSpeed(this.sprintSpeedModifier);
        } else {
            this.mob.getNavigation().setSpeed(this.walkSpeedModifier);
        }
    }
}
