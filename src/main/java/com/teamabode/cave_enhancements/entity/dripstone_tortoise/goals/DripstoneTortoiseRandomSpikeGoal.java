package com.teamabode.cave_enhancements.entity.dripstone_tortoise.goals;

import com.teamabode.cave_enhancements.entity.dripstone_tortoise.DripstoneTortoise;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.phys.Vec3;

public class DripstoneTortoiseRandomSpikeGoal extends Goal {

    private int cooldown;
    private long lastUpdateTime;
    DripstoneTortoise dripstoneTortoise;

    public DripstoneTortoiseRandomSpikeGoal(DripstoneTortoise dripstoneTortoise) {
        this.dripstoneTortoise = dripstoneTortoise;
    }

    public boolean canUse() {
        if (dripstoneTortoise.isAggressive()) return false;
        if (!dripstoneTortoise.isOnGround()) return false;

        long l = dripstoneTortoise.level.getGameTime();
        if (l - this.lastUpdateTime < 20L) {
            return false;
        } else {
            this.lastUpdateTime = l;

            return true;
        }
    }

    public boolean canContinueToUse() {
        return !dripstoneTortoise.isAggressive() || !dripstoneTortoise.isOnGround();
    }

    public void start() {
        this.cooldown = 0;
    }

    public void tick() {
        this.cooldown = Math.max(this.cooldown - 1, 0);
        this.spike();
    }

    public boolean requiresUpdateEveryTick() {
        return true;
    }

    protected void resetCooldown() {
        this.cooldown = 400;
    }

    protected void spike() {
        if (this.cooldown <= 0) {
            this.resetCooldown();

            Vec3 targetPos = dripstoneTortoise.position();
            RandomSource random = dripstoneTortoise.getRandom();

            for(int i = 0; i < 10; i++){
                dripstoneTortoise.summonPike(new Vec3(random.nextIntBetweenInclusive((int) -1.5F, (int) 1.5F) + targetPos.x(),  targetPos.y(), random.nextIntBetweenInclusive( (int) -1.5F,  (int) 1.5F)  + targetPos.z()));
            }

            dripstoneTortoise.setShouldStomp(true);
            dripstoneTortoise.stompTimer = 10;
            dripstoneTortoise.level.playSound(null, new BlockPos(dripstoneTortoise.position()), SoundEvents.DRIPSTONE_BLOCK_BREAK, SoundSource.HOSTILE, 1F, 1F);
        }
    }
}
