package com.teamabode.cave_enhancements.entity.cruncher.goals;

import com.teamabode.cave_enhancements.CaveEnhancements;
import com.teamabode.cave_enhancements.entity.cruncher.Cruncher;
import com.teamabode.cave_enhancements.registry.ModBlocks;
import net.fabricmc.fabric.api.tag.convention.v1.ConventionalBlockTags;
import net.fabricmc.loader.impl.lib.sat4j.core.Vec;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class CruncherEatBlockGoal extends Goal {

    private final Cruncher cruncher;
    int currentTick = 0;

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

        Vec3 vec3 = new Vec3(cruncher.getTargetBlockX() + 0.5D, cruncher.getY(), cruncher.getTargetBlockZ() + 0.5D);

        if(cruncher.position().distanceTo(vec3) <=  1){
            if(cruncher.position().distanceTo(vec3) > 0.2){
                cruncher.setDeltaMovement(0.0, 0.0, 0.0);
                cruncher.teleportToWithTicket(vec3.x, vec3.y, vec3.z);
            }

            if (currentTick > 40 && level.getBlockState(pos).is(BlockTags.BASE_STONE_OVERWORLD)) {
                level.destroyBlock(pos, true);
                currentTick = 0;
            }
        }else {
            cruncher.getNavigation().moveTo(cruncher.getNavigation().createPath(new BlockPos(vec3), 0), 2F);
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
