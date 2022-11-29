package com.teamabode.cave_enhancements.block;

import com.teamabode.cave_enhancements.block.entity.ReceiverBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.decoration.ItemFrame;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DiodeBlock;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.RedStoneWireBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.WeatheringCopper;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.ComparatorMode;
import net.minecraft.world.ticks.TickPriority;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ReceiverBlock extends DiodeBlock implements EntityBlock {
    public static final BooleanProperty CAN_PASS = BooleanProperty.create("can_pass");
    public WeatheringCopper.WeatherState oxidationLevel;

    public ReceiverBlock(WeatheringCopper.WeatherState oxidationLevel, Properties settings) {
        super(settings);
        this.oxidationLevel = oxidationLevel;
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(POWERED, false).setValue(CAN_PASS, false));
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING, CAN_PASS, POWERED);
    }

    protected int getInputSignal(Level level, BlockPos pos, BlockState state) {
        int i = super.getInputSignal(level, pos, state);
        Direction direction = state.getValue(FACING);
        BlockPos blockPos = pos.relative(direction);
        BlockState blockState = level.getBlockState(blockPos);
        if (blockState.hasAnalogOutputSignal()) {
            i = blockState.getAnalogOutputSignal(level, blockPos);
        } else if (i < 15 && blockState.isRedstoneConductor(level, blockPos)) {
            blockPos = blockPos.relative(direction);
            blockState = level.getBlockState(blockPos);
            int j = Math.max(Integer.MIN_VALUE, blockState.hasAnalogOutputSignal() ? blockState.getAnalogOutputSignal(level, blockPos) : Integer.MIN_VALUE);
            if (j != Integer.MIN_VALUE) {
                i = j;
            }
        }
        return i;
    }

    public int getSignal(BlockState state, BlockGetter world, BlockPos pos, Direction direction) {
        if (!state.getValue(CAN_PASS)) {
            return 0;
        } else {
            return state.getValue(FACING) == direction ? 1 : 0;
        }
    }

    public BlockState getStateForPlacement(BlockPlaceContext ctx) {
        BlockState blockState = super.getStateForPlacement(ctx);
        assert blockState != null;
        return blockState.setValue(CAN_PASS, false).setValue(FACING, ctx.getHorizontalDirection().getOpposite());
    }

    public int getMaxPower() {
        return switch (this.oxidationLevel) {
            case UNAFFECTED -> 2;
            case EXPOSED -> 5;
            case WEATHERED -> 10;
            case OXIDIZED -> 20;
        };
    }

    public boolean isSignalSource(BlockState state) {
        return state.getValue(CAN_PASS);
    }

    protected int getDelay(BlockState state) {
        return 0;
    }

    public void animateTick(BlockState state, Level world, BlockPos pos, RandomSource random) {
        if (state.getValue(POWERED)) {
            Direction direction = state.getValue(FACING);
            double d = (double)pos.getX() + 0.5D + (random.nextDouble() - 0.5D) * 0.2D;
            double e = (double)pos.getY() + 0.4D + (random.nextDouble() - 0.5D) * 0.2D;
            double f = (double)pos.getZ() + 0.5D + (random.nextDouble() - 0.5D) * 0.2D;
            float g = -5.0F;
            if (random.nextBoolean()) {
                g = (float)(4 * 2 - 1);
            }

            g /= 16.0F;
            double h = g * (float)direction.getStepX();
            double i = g * (float)direction.getStepZ();
            world.addParticle(DustParticleOptions.REDSTONE, d + h, e, f + i, 0.0D, 0.0D, 0.0D);
        }
    }

    public RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }

    @Nullable
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new ReceiverBlockEntity(pos, state);
    }

    @Nullable
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level world, BlockState state, BlockEntityType<T> type) {
        return (world1, pos, state1, blockEntity) -> {
            if (blockEntity instanceof ReceiverBlockEntity) {
                ((ReceiverBlockEntity) blockEntity).tick(world1, pos, state1);
            }
        };
    }
}
