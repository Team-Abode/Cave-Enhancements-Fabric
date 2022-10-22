package com.teamabode.cave_enhancements.entity.cruncher.goals;

import com.teamabode.cave_enhancements.CaveEnhancements;
import com.teamabode.cave_enhancements.entity.cruncher.Cruncher;
import net.fabricmc.fabric.api.tag.convention.v1.ConventionalBlockTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.util.DefaultRandomPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class CruncherOreSearchGoal extends Goal {

    private final Cruncher cruncher;
    boolean isFinished = false;
    boolean hasSuccessfullyFinished = false;
    @Nullable BlockPos finishedPos = null;
    int goalTickDuration = 0;

    public CruncherOreSearchGoal(Cruncher cruncher) {
        this.cruncher = cruncher;
    }

    public boolean canUse() {
        if (cruncher.isSearching()) {
            Level level = cruncher.getLevel();
            finishedPos = getOrePosition(level, 10, 50);
            return true;
        }
        return false;
    }

    public boolean canContinueToUse() {
        return cruncher.isSearching() && !isFinished;
    }

    @Nullable
    private BlockPos getOrePosition(Level level, int radius, int tries) {
        for (int i = 0; i <= tries; i++) {
            Vec3 vec3 = DefaultRandomPos.getPos(cruncher, radius, 3);
            if (vec3 == null) continue;
            BlockPos potentialPos = new BlockPos(vec3);

            for (int j = 0; j <= 10; j++) {
                BlockPos pos = potentialPos.below(j);
                if (level.getBlockState(pos).is(ConventionalBlockTags.ORES)) {
                    return potentialPos;
                }
            }
        }

        return null;
    }

    public void tick() {
        goalTickDuration++;
        if (finishedPos == null) {
            isFinished = true;
            return;
        }

        cruncher.getNavigation().moveTo(finishedPos.getX(), finishedPos.getY(), finishedPos.getZ(), 2.0F);

        if (cruncher.blockPosition().distToCenterSqr(finishedPos.getX(), finishedPos.getY(), finishedPos.getZ()) <= 2.5) {
            hasSuccessfullyFinished = true;
            isFinished = true;
        }
        if (goalTickDuration % 300 == 0) {
            isFinished = true;
        }
    }

    public boolean requiresUpdateEveryTick() {
        return true;
    }

    public void stop() {
        if (hasSuccessfullyFinished) {
            if (finishedPos != null) {
                CaveEnhancements.LOGGER.info("Successful finish");
                cruncher.setDeltaMovement(0.0, 0.0, 0.0);
                cruncher.teleportToWithTicket(finishedPos.getX() + 0.5D, finishedPos.getY() ,finishedPos.getZ() + 0.5D);
                cruncher.setCanMine(true);
            }
        }
        if (finishedPos == null) {
            if (cruncher.getLevel() instanceof ServerLevel server) {
                server.sendParticles(ParticleTypes.ANGRY_VILLAGER, cruncher.getX(), cruncher.getY(), cruncher.getZ(), 1, 0, 0, 0, 0);
            }
        }

        CaveEnhancements.LOGGER.info("Finished CruncherOreSearchGoal");

        cruncher.setSearching(false);
        cruncher.getNavigation().stop();
    }
}
