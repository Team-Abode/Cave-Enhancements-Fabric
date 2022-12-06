package com.teamabode.cave_enhancements.common.entity.cruncher.goals;

import com.teamabode.cave_enhancements.common.entity.cruncher.Cruncher;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.player.Player;

import java.util.Objects;

public class CruncherLookAtPlayerGoal extends LookAtPlayerGoal {

    private final Cruncher cruncher;

    public CruncherLookAtPlayerGoal(Mob mob) {
        super(mob, Player.class, 5.0F);
        this.cruncher = (Cruncher) mob;
    }

    public boolean canUse() {
        if (!Objects.equals(cruncher.getEatingState(), "none")) return false;
        return super.canUse();
    }

    public boolean canContinueToUse() {
        if (!Objects.equals(cruncher.getEatingState(), "none")) return false;
        return super.canContinueToUse();
    }
}
