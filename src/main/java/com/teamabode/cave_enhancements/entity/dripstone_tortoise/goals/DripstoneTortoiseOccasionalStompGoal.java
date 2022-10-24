package com.teamabode.cave_enhancements.entity.dripstone_tortoise.goals;

import com.teamabode.cave_enhancements.entity.dripstone_tortoise.DripstoneTortoise;
import net.minecraft.util.TimeUtil;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.ai.goal.Goal;

public class DripstoneTortoiseOccasionalStompGoal extends Goal {

    private static final UniformInt OCCASIONAL_STOMP_COOLDOWN = TimeUtil.rangeOfSeconds(50, 70);
    private final DripstoneTortoise dripstoneTortoise;

    public DripstoneTortoiseOccasionalStompGoal(DripstoneTortoise dripstoneTortoise) {
        this.dripstoneTortoise = dripstoneTortoise;
    }

    public boolean canUse() {
        return dripstoneTortoise.getOccasionalStompCooldown() == 0 && dripstoneTortoise.isOnGround();
    }

    public boolean canContinueToUse() {
        return dripstoneTortoise.getOccasionalStompCooldown() == 0;
    }

    public void start() {
        System.out.println("DripstoneTortoiseOccasionalStompGoal initialized.");
        for (int i = 0; i <= 10; i++) {
            double randomX = dripstoneTortoise.getRandomX(i * 0.65);
            double randomZ = dripstoneTortoise.getRandomZ(i * 0.65);
            dripstoneTortoise.summonPike(randomX, randomZ, dripstoneTortoise.getY(), dripstoneTortoise.getY() + 1);
        }
        dripstoneTortoise.setOccasionalStompCooldown(OCCASIONAL_STOMP_COOLDOWN.sample(dripstoneTortoise.getRandom()));
    }
}
