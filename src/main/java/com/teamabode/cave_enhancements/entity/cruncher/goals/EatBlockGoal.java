package com.teamabode.cave_enhancements.entity.cruncher.goals;

import com.teamabode.cave_enhancements.entity.cruncher.Cruncher;
import java.util.EnumSet;

import com.teamabode.cave_enhancements.registry.ModTags;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;

public class EatBlockGoal extends Goal {
    private final Cruncher mob;
    private final Level world;
    private int timer;

    public EatBlockGoal(Cruncher mob) {
        this.mob = mob;
        this.world = mob.level;
        this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK, Flag.JUMP));
    }

    public boolean canUse() {
        if (this.mob.eatingTicks > 0 && this.mob.level.getGameTime() - this.mob.lastEatTick > 120L) {
            BlockPos blockPos = this.mob.blockPosition().below();

                return this.world.getBlockState(blockPos.below()).is(ModTags.CRUNCHER_CONSUMABLES);
        }
        return false;
    }

    public void start() {
        this.timer = this.adjustedTickDelay(40);
        this.world.broadcastEntityEvent(this.mob, (byte)10);
        this.mob.getNavigation().stop();
    }

    public void stop() {
        this.mob.lastEatTick = this.mob.level.getGameTime();
        this.timer = 0;

    }

    public boolean canContinueToUse() {
        return this.timer > 0;
    }

    public void tick() {
        this.timer = Math.max(0, this.timer - 1);
        if (this.timer == this.adjustedTickDelay(4)) {
            BlockPos blockPos = this.mob.blockPosition().below();
            if (this.world.getGameRules().getBoolean(GameRules.RULE_MOBGRIEFING) && this.world.getBlockState(blockPos).is(BlockTags.BASE_STONE_OVERWORLD)) {
                this.world.destroyBlock(blockPos, false);
                this.mob.isEatingBlock(true);
            }

            this.mob.ate();
            this.mob.gameEvent(GameEvent.EAT, this.mob);
        }
    }
}
