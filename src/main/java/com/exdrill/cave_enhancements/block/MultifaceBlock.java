package com.exdrill.cave_enhancements.block;

import net.minecraft.block.BlockState;
import net.minecraft.block.LichenGrower;
import net.minecraft.block.MultifaceGrowthBlock;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;

public class MultifaceBlock extends MultifaceGrowthBlock {
    public MultifaceBlock(Settings settings) {
        super(settings);
    }

    @Override
    public LichenGrower getGrower() {
        return null;
    }

    public boolean isTranslucent(BlockState state, BlockView world, BlockPos pos) { return true; }

    @Override
    public PistonBehavior getPistonBehavior(BlockState state) {
        return PistonBehavior.DESTROY;
    }

}
