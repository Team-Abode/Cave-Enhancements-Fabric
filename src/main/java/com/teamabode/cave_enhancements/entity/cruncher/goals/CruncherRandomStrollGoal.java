package com.teamabode.cave_enhancements.entity.cruncher.goals;

import com.teamabode.cave_enhancements.entity.cruncher.Cruncher;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;

public class CruncherRandomStrollGoal extends RandomStrollGoal {

    private final Cruncher cruncher;

    public CruncherRandomStrollGoal(PathfinderMob pathfinderMob) {
        super(pathfinderMob, 1.0F, 40);
        this.cruncher = (Cruncher) pathfinderMob;
    }

    public boolean canUse() {
        if (!cruncher.isSearching()) return true;
        if (!cruncher.canMine()) return true;
        return super.canUse();
    }

    public boolean canContinueToUse() {
        return super.canContinueToUse();
    }
}
