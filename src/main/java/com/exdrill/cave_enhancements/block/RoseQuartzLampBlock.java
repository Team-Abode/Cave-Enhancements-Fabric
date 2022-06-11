package com.exdrill.cave_enhancements.block;

import net.minecraft.block.*;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;
import org.jetbrains.annotations.Nullable;

public class RoseQuartzLampBlock extends Block implements Waterloggable {
    public static final BooleanProperty WATERLOGGED;
    public static final DirectionProperty FACING;
    public static final VoxelShape NORTH_SHAPE;
    public static final VoxelShape SOUTH_SHAPE;
    public static final VoxelShape EAST_SHAPE;
    public static final VoxelShape WEST_SHAPE;
    public static final VoxelShape UP_SHAPE;
    public static final VoxelShape DOWN_SHAPE;

    public RoseQuartzLampBlock(Settings settings) {
        super(settings);
    }

    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(WATERLOGGED, FACING);
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return getShape(state);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return getShape(state);
    }

    public VoxelShape getShape(BlockState state) {
        Direction direction = state.get(FACING);
        return switch (direction) {
            case NORTH -> NORTH_SHAPE;
            case SOUTH -> SOUTH_SHAPE;
            case EAST -> EAST_SHAPE;
            case WEST -> WEST_SHAPE;
            case DOWN -> DOWN_SHAPE;
            case UP -> UP_SHAPE;
        };
    }

    public BlockState mirror(BlockState state, BlockMirror mirror) {
        return state.rotate(mirror.getRotation(state.get(FACING)));
    }

    public PistonBehavior getPistonBehavior(BlockState state) {
        return PistonBehavior.DESTROY;
    }

    public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        Direction direction = state.get(FACING);
        BlockPos blockPos = pos.offset(direction.getOpposite());
        return world.getBlockState(blockPos).isSideSolidFullSquare(world, blockPos, direction);
    }

    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        if (state.get(WATERLOGGED)) {
            world.createAndScheduleFluidTick(pos, Fluids.WATER, Fluids.WATER.getTickRate(world));
        }

        return direction == state.get(FACING).getOpposite() && !state.canPlaceAt(world, pos) ? Blocks.AIR.getDefaultState() : super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
    }

    @Nullable
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        WorldAccess worldAccess = ctx.getWorld();
        BlockPos blockPos = ctx.getBlockPos();
        return this.getDefaultState().with(WATERLOGGED, worldAccess.getFluidState(blockPos).getFluid() == Fluids.WATER).with(FACING, ctx.getSide());
    }

    public BlockState rotate(BlockState state, BlockRotation rotation) {
        return state.with(FACING, rotation.rotate(state.get(FACING)));
    }

    public FluidState getFluidState(BlockState state) {
        return state.get(WATERLOGGED) ? Fluids.WATER.getStill(false) : super.getFluidState(state);
    }


    static {
        WATERLOGGED = Properties.WATERLOGGED;
        FACING = Properties.FACING;
        NORTH_SHAPE = VoxelShapes.union(Block.createCuboidShape(4.0D, 4.0D, 2.0D, 12.0D, 12.0D, 12.0D), Block.createCuboidShape(7.0D, 7.0D, 12.0D, 9.0D, 9.0D, 16.0D));
        SOUTH_SHAPE = VoxelShapes.union(Block.createCuboidShape(4.0D, 4.0D, 4.0D, 12.0D, 12.0D, 14.0D), Block.createCuboidShape(7D, 7D, 0D, 9D, 9D, 0D));
        EAST_SHAPE = VoxelShapes.union(Block.createCuboidShape(4.0D, 4.0D, 4.0D, 14.0D, 12.0D, 12.0D), Block.createCuboidShape(0D, 7D, 7D, 4D, 9D, 9D));
        WEST_SHAPE = VoxelShapes.union(Block.createCuboidShape(2.0D, 4.0D, 4.0D, 12.0D, 12.0D, 12.0D), Block.createCuboidShape(12D, 7D, 7D, 16D, 9D, 9D));
        UP_SHAPE = VoxelShapes.union(Block.createCuboidShape(4.0D, 4.0D, 4.0D, 12.0D, 14.0D, 12.0D), Block.createCuboidShape(7.0D, 0.0D, 7.0D, 9.0D, 4.0D, 9.0D));
        DOWN_SHAPE = VoxelShapes.union(Block.createCuboidShape(4.0D, 2.0D, 4.0D, 12.0D, 12.0D, 12.0D), Block.createCuboidShape(7.0D, 12.0D, 7.0D, 9.0D, 16.0D, 9.0D));

    }
}
