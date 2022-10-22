package com.teamabode.cave_enhancements.entity.cruncher.goals;

import com.teamabode.cave_enhancements.entity.cruncher.Cruncher;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;

public class CruncherRandomStrollGoal extends RandomStrollGoal {

    private final Cruncher cruncher;

    public CruncherRandomStrollGoal(PathfinderMob pathfinderMob) {
        super(pathfinderMob, 1.0F);
        this.cruncher = (Cruncher) pathfinderMob;
    }

    public boolean canUse() {
        if (cruncher.isSearching()) return false;
        return super.canUse();
    }

    public boolean canContinueToUse() {
        if (cruncher.isSearching()) return false;
        return super.canContinueToUse();
    }
}
