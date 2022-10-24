package com.teamabode.cave_enhancements.entity.cruncher.goals;

import com.teamabode.cave_enhancements.entity.cruncher.Cruncher;
import com.teamabode.cave_enhancements.registry.ModItems;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.TemptGoal;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;

public class CruncherTemptGoal extends TemptGoal {

    private final Cruncher cruncher;

    public CruncherTemptGoal(PathfinderMob pathfinderMob) {
        super(pathfinderMob, 1.0, Ingredient.of(Items.GLOW_BERRIES, ModItems.GLOW_BERRY_JUICE), false);
        this.cruncher = (Cruncher) pathfinderMob;
    }

    public boolean canUse() {
        if (!cruncher.getEatingState().equals("none")) return false;
        return super.canUse();
    }
}
