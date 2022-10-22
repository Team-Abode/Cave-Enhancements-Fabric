package com.teamabode.cave_enhancements.entity.cruncher.goals;

import com.teamabode.cave_enhancements.entity.cruncher.Cruncher;
import com.teamabode.cave_enhancements.registry.ModTags;
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
    boolean reachedPosition = false;

    @Nullable BlockPos targetPos = null;

    int goalTickTime = 0;

    public CruncherOreSearchGoal(Cruncher cruncher) {
        this.cruncher = cruncher;
    }

    public boolean canUse() {
        return cruncher.isSearching();
    }

    public void start(){
        targetPos = getOrePosition(cruncher.getLevel(), 15, 150);
    }

    public boolean canContinueToUse() {
        return cruncher.isSearching() && !isFinished && targetPos != null && goalTickTime < 300 && cruncher.getLastHurtByMob() == null;
    }

    public void tick() {
        goalTickTime++;

        if (goalTickTime >= 300) return;

        if (targetPos == null) return;

        cruncher.getNavigation().moveTo(cruncher.getNavigation().createPath(targetPos, 0), 2.0F);

        if (cruncher.position().distanceTo(Vec3.atCenterOf(targetPos)) <= 1D) {
            reachedPosition = true;

            isFinished = true;
        }
    }

    public void stop() {
        if (reachedPosition) {
            cruncher.setDeltaMovement(0.0, 0.0, 0.0);
            cruncher.teleportToWithTicket(targetPos.getX() + 0.5D, targetPos.getY(), targetPos.getZ() + 0.5D);
            cruncher.setCanMine(true);
            cruncher.setTargetBlockX(targetPos.getX());
            cruncher.setTargetBlockZ(targetPos.getZ());
        }else {
            if (cruncher.getLevel() instanceof ServerLevel server) {
                server.sendParticles(ParticleTypes.ANGRY_VILLAGER, cruncher.getX(), cruncher.getY(), cruncher.getZ(), 1, 0, 0, 0, 0);
            }
        }

        cruncher.setSearching(false);
        cruncher.getNavigation().stop();

        goalTickTime = 0;
        targetPos = null;
        reachedPosition = false;
        isFinished = false;
    }

    @Nullable
    private BlockPos getOrePosition(Level level, int radius, int tries) {
        List<BlockPos> potentialPositions = new ArrayList<>();

        for (int i = 0; i < tries; i++) {
            Vec3 vec = DefaultRandomPos.getPos(cruncher, radius, 5);

            if(vec == null) continue;

            BlockPos potentialPos = new BlockPos(vec);

            for (int j = 0; j <= 10; j++) {
                BlockPos pos = potentialPos.below(j);

                if (level.getBlockState(pos).getMaterial().isLiquid()) continue;
                if (level.getBlockState(pos).isAir()) continue;
                if (level.getBlockState(pos).is(Blocks.BEDROCK)) continue;
                if (!level.getBlockState(pos.below()).is(ModTags.CRUNCHER_CONSUMABLES)) continue;

                if (level.getBlockState(pos).is(ModTags.CRUNCHER_SEARCHABLES)) {
                    potentialPositions.add(potentialPos);
                }
            }
        }

        if (potentialPositions.size() == 0) return null;

        return potentialPositions.get(level.getRandom().nextInt(potentialPositions.size()));
    }

    public boolean requiresUpdateEveryTick() {
        return true;
    }
}
