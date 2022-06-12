package com.exdrill.cave_enhancements.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.MultifaceSpreader;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.PushReaction;

public class MultifaceBlock extends net.minecraft.world.level.block.MultifaceBlock {
    public MultifaceBlock(Properties settings) {
        super(settings);
    }

    @Override
    public MultifaceSpreader getSpreader() {
        return null;
    }

    public boolean propagatesSkylightDown(BlockState state, BlockGetter world, BlockPos pos) { return true; }

    @Override
    public PushReaction getPistonPushReaction(BlockState state) {
        return PushReaction.DESTROY;
    }

}
