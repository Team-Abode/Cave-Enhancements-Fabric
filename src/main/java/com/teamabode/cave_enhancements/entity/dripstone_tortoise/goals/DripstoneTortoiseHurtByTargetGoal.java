package com.teamabode.cave_enhancements.entity.dripstone_tortoise.goals;

import com.teamabode.cave_enhancements.entity.dripstone_tortoise.DripstoneTortoise;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;

public class DripstoneTortoiseHurtByTargetGoal extends HurtByTargetGoal {

    DripstoneTortoise dripstoneTortoise;

    public DripstoneTortoiseHurtByTargetGoal(PathfinderMob pathfinderMob) {
        super(pathfinderMob);

        this.dripstoneTortoise = (DripstoneTortoise) pathfinderMob;
    }

    public boolean canContinueToUse() {
        return dripstoneTortoise.isAngry() && super.canContinueToUse();
    }

    protected void alertOther(Mob mob, LivingEntity target) {
        if (mob instanceof DripstoneTortoise && mob.hasLineOfSight(target)) {
            mob.setTarget(target);
        }
    }

}
