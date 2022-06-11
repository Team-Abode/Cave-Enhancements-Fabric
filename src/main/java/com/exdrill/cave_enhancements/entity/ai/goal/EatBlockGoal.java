package com.exdrill.cave_enhancements.entity.ai.goal;

import com.exdrill.cave_enhancements.entity.CruncherEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;

import java.util.EnumSet;

public class EatBlockGoal extends Goal {
    private final CruncherEntity mob;
    private final World world;
    private int timer;

    public EatBlockGoal(CruncherEntity mob) {
        this.mob = mob;
        this.world = mob.world;
        this.setControls(EnumSet.of(Control.MOVE, Control.LOOK, Control.JUMP));
    }

    public boolean canStart() {
        if (this.mob.eatingTicks > 0 && this.mob.world.getTime() - this.mob.lastEatTick > 120L) {
            BlockPos blockPos = this.mob.getBlockPos().down();

                return this.world.getBlockState(blockPos.down()).isIn(BlockTags.BASE_STONE_OVERWORLD);
        }
        return false;
    }

    public void start() {
        this.timer = this.getTickCount(40);
        this.world.sendEntityStatus(this.mob, (byte)10);
        this.mob.getNavigation().stop();
    }

    public void stop() {
        this.mob.lastEatTick = this.mob.world.getTime();
        this.timer = 0;

    }

    public boolean shouldContinue() {
        return this.timer > 0;
    }

    public void tick() {
        this.timer = Math.max(0, this.timer - 1);
        if (this.timer == this.getTickCount(4)) {
            BlockPos blockPos = this.mob.getBlockPos().down();
            if (this.world.getGameRules().getBoolean(GameRules.DO_MOB_GRIEFING) && this.world.getBlockState(blockPos).isIn(BlockTags.BASE_STONE_OVERWORLD)) {
                this.world.breakBlock(blockPos, false);
                this.mob.isEatingBlock(true);
            }

            this.mob.onEatingGrass();
            this.mob.emitGameEvent(GameEvent.EAT, this.mob);
        }
    }
}
