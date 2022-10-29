package com.teamabode.cave_enhancements.entity.dripstone_tortoise.goals;

import com.teamabode.cave_enhancements.entity.dripstone_tortoise.DripstoneTortoise;
import net.minecraft.world.entity.ai.goal.PanicGoal;

public class DripstoneTortoisePanicGoal extends PanicGoal {

    private final DripstoneTortoise dripstoneTortoise;

    public DripstoneTortoisePanicGoal(DripstoneTortoise dripstoneTortoise) {
        super(dripstoneTortoise, 1.5F);
        this.dripstoneTortoise = dripstoneTortoise;
    }

    public boolean canUse() {
        return dripstoneTortoise.isBaby() && super.canUse();
    }
}
