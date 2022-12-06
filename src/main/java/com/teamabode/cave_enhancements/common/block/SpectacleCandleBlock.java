package com.teamabode.cave_enhancements.common.block;

import com.teamabode.cave_enhancements.common.block.entity.SpectacleCandleBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.CandleBlock;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public class SpectacleCandleBlock extends CandleBlock implements EntityBlock {

    public SpectacleCandleBlock(Properties properties) {
        super(properties);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new SpectacleCandleBlockEntity(blockPos, blockState);
    }


    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState blockState, BlockEntityType<T> blockEntityType) {
        return (world1, pos, state1, blockEntity) -> {
            if (blockEntity instanceof SpectacleCandleBlockEntity) {
                SpectacleCandleBlockEntity.tick(world1, pos, state1);
            }
        };
    }
}