package com.teamabode.cave_enhancements.entity.dripstone_tortoise.goals;

import com.teamabode.cave_enhancements.entity.dripstone_tortoise.DripstoneTortoise;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.ai.goal.MoveToBlockGoal;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;

public class DripstoneTortoiseLayEggGoal extends MoveToBlockGoal {
    private final DripstoneTortoise dripstoneTortoise;
    boolean hasLayedEggs;

    public DripstoneTortoiseLayEggGoal(DripstoneTortoise dripstoneTortoise, double d) {
        super(dripstoneTortoise, d, 16);
        hasLayedEggs = false;
        this.dripstoneTortoise = dripstoneTortoise;
    }

    public boolean canUse() {
        return dripstoneTortoise.isPregnant() && dripstoneTortoise.getTarget() == null && !dripstoneTortoise.isBaby() && super.canUse();
    }

    public boolean canContinueToUse() {
        return super.canContinueToUse() && dripstoneTortoise.getTarget() == null && dripstoneTortoise.isPregnant();
    }

    public void tick() {
        super.tick();
        if (this.isReachedTarget() && !hasLayedEggs) {
            dripstoneTortoise.level.setBlock(blockPos.above(), Blocks.TURTLE_EGG.defaultBlockState().setValue(BlockStateProperties.EGGS, dripstoneTortoise.getRandom().nextInt(4) + 1), 3);
            dripstoneTortoise.setPregnant(false);
            hasLayedEggs = true;
        }
    }

    protected boolean isValidTarget(LevelReader level, BlockPos pos) {
        return level.getBlockState(pos).is(Blocks.DRIPSTONE_BLOCK);
    }
}
