package com.teamabode.cave_enhancements.entity.cruncher.goals;

import com.teamabode.cave_enhancements.CaveEnhancements;
import com.teamabode.cave_enhancements.entity.cruncher.Cruncher;
import com.teamabode.cave_enhancements.registry.ModBlocks;
import net.fabricmc.fabric.api.tag.convention.v1.ConventionalBlockTags;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.level.Level;

public class CruncherEatBlockGoal extends Goal {

    private final Cruncher cruncher;
    int currentTick = 0;
    boolean isFinished = true;
    boolean hasSuccessfullyFinished = true;

    public CruncherEatBlockGoal(Cruncher cruncher) {
        this.cruncher = cruncher;
    }

    public boolean canUse() {
        return cruncher.canMine();
    }

    public boolean canContinueToUse() {
        return cruncher.canMine() && !cruncher.level.getBlockState(cruncher.blockPosition().below()).is(ConventionalBlockTags.ORES);
    }

    public void tick() {
        Level level = cruncher.getLevel();
        BlockPos pos = cruncher.blockPosition().below();
        currentTick++;

        cruncher.getLookControl().setLookAt(pos.getX(), pos.getY(), pos.getZ());

        if (currentTick % 40 == 0 && level.getBlockState(pos).is(BlockTags.BASE_STONE_OVERWORLD)) {
            level.destroyBlock(pos, true);
            currentTick = 0;
        }
    }

    public void stop() {
        CaveEnhancements.LOGGER.info("Finished CruncherEatBlockGoal");
        cruncher.setCanMine(false);
    }

    public boolean requiresUpdateEveryTick() {
        return true;
    }
}
