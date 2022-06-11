package com.exdrill.cave_enhancements.block;

import com.exdrill.cave_enhancements.block.entity.SpectacleCandleBlockEntity;
import com.exdrill.cave_enhancements.registry.ModBlocks;
import com.google.common.collect.ImmutableList;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectMaps;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Util;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;

import java.util.List;
import java.util.function.ToIntFunction;

public class SpectacleCandleBlock extends AbstractSpectacleCandleBlock implements BlockEntityProvider  {
    public static final int field_31050 = 1;
    public static final int field_31051 = 4;
    public static final IntProperty CANDLES;
    public static final BooleanProperty LIT;
    public static final BooleanProperty WATERLOGGED;
    public static final ToIntFunction<BlockState> STATE_TO_LUMINANCE;
    private static final Int2ObjectMap<List<Vec3d>> CANDLES_TO_PARTICLE_OFFSETS;
    private static final VoxelShape ONE_CANDLE_SHAPE;
    private static final VoxelShape TWO_CANDLES_SHAPE;
    private static final VoxelShape THREE_CANDLES_SHAPE;
    private static final VoxelShape FOUR_CANDLES_SHAPE;

    public SpectacleCandleBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.stateManager.getDefaultState().with(CANDLES, 1).with(LIT, false).with(WATERLOGGED, false));
    }

    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new SpectacleCandleBlockEntity(pos, state);
    }

    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (player.getAbilities().allowModifyWorld && player.getStackInHand(hand).isEmpty() && state.get(LIT)) {
            extinguish(player, state, world, pos);
            return ActionResult.success(world.isClient);
        } else {
            return ActionResult.PASS;
        }
    }

    public boolean canReplace(BlockState state, ItemPlacementContext context) {
        return !context.shouldCancelInteraction() && context.getStack().getItem() == this.asItem() && state.get(CANDLES) < 4 || super.canReplace(state, context);
    }

    public BlockState getPlacementState(ItemPlacementContext ctx) {
        BlockState blockState = ctx.getWorld().getBlockState(ctx.getBlockPos());
        if (blockState.isOf(this)) {
            return blockState.cycle(CANDLES);
        } else {
            FluidState fluidState = ctx.getWorld().getFluidState(ctx.getBlockPos());
            boolean bl = fluidState.getFluid() == Fluids.WATER;
            return super.getPlacementState(ctx).with(WATERLOGGED, bl);
        }
    }

    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        if (state.get(WATERLOGGED)) {
            world.createAndScheduleFluidTick(pos, Fluids.WATER, Fluids.WATER.getTickRate(world));
        }

        return super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
    }

    public FluidState getFluidState(BlockState state) {
        return state.get(WATERLOGGED) ? Fluids.WATER.getStill(false) : super.getFluidState(state);
    }

    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        switch(state.get(CANDLES)) {
            case 1:
            default:
                return ONE_CANDLE_SHAPE;
            case 2:
                return TWO_CANDLES_SHAPE;
            case 3:
                return THREE_CANDLES_SHAPE;
            case 4:
                return FOUR_CANDLES_SHAPE;
        }
    }

    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(CANDLES, LIT, WATERLOGGED);
    }

    public boolean tryFillWithFluid(WorldAccess world, BlockPos pos, BlockState state, FluidState fluidState) {
        if (!(Boolean)state.get(WATERLOGGED) && fluidState.getFluid() == Fluids.WATER) {
            BlockState blockState = state.with(WATERLOGGED, true);
            if (state.get(LIT)) {
                extinguish(null, blockState, world, pos);
            } else {
                world.setBlockState(pos, blockState, 3);
            }

            world.createAndScheduleFluidTick(pos, fluidState.getFluid(), fluidState.getFluid().getTickRate(world));
            return true;
        } else {
            return false;
        }
    }

    public static boolean canBeLit(BlockState state) {
        return state.isIn(BlockTags.CANDLES, (statex) -> statex.contains(LIT) && statex.contains(WATERLOGGED)) && !(Boolean)state.get(LIT) && !(Boolean)state.get(WATERLOGGED);
    }

    protected Iterable<Vec3d> getParticleOffsets(BlockState state) {
        return CANDLES_TO_PARTICLE_OFFSETS.get(state.get(CANDLES));
    }

    protected boolean isNotLit(BlockState state) {
        return !(Boolean)state.get(WATERLOGGED) && super.isNotLit(state);
    }

    public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        return Block.sideCoversSmallSquare(world, pos.down(), Direction.UP);
    }

    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return checkType(type, ModBlocks.SPECTACLE_CANDLE_BLOCK_ENTITY, SpectacleCandleBlockEntity::tick);
    }

    static {
        CANDLES = Properties.CANDLES;
        LIT = AbstractCandleBlock.LIT;
        WATERLOGGED = Properties.WATERLOGGED;
        STATE_TO_LUMINANCE = (state) -> (Boolean)state.get(LIT) ? 3 * state.get(CANDLES) : 0;
        CANDLES_TO_PARTICLE_OFFSETS = Util.make(() -> {
            Int2ObjectMap<List<Vec3d>> int2ObjectMap = new Int2ObjectOpenHashMap();
            int2ObjectMap.defaultReturnValue(ImmutableList.of());
            int2ObjectMap.put(1, ImmutableList.of(new Vec3d(0.5D, 0.5D, 0.5D)));
            int2ObjectMap.put(2, ImmutableList.of(new Vec3d(0.375D, 0.44D, 0.5D), new Vec3d(0.625D, 0.5D, 0.44D)));
            int2ObjectMap.put(3, ImmutableList.of(new Vec3d(0.5D, 0.313D, 0.625D), new Vec3d(0.375D, 0.44D, 0.5D), new Vec3d(0.56D, 0.5D, 0.44D)));
            int2ObjectMap.put(4, ImmutableList.of(new Vec3d(0.44D, 0.313D, 0.56D), new Vec3d(0.625D, 0.44D, 0.56D), new Vec3d(0.375D, 0.44D, 0.375D), new Vec3d(0.56D, 0.5D, 0.375D)));
            return Int2ObjectMaps.unmodifiable(int2ObjectMap);
        });
        ONE_CANDLE_SHAPE = Block.createCuboidShape(7.0D, 0.0D, 7.0D, 9.0D, 6.0D, 9.0D);
        TWO_CANDLES_SHAPE = Block.createCuboidShape(5.0D, 0.0D, 6.0D, 11.0D, 6.0D, 9.0D);
        THREE_CANDLES_SHAPE = Block.createCuboidShape(5.0D, 0.0D, 6.0D, 10.0D, 6.0D, 11.0D);
        FOUR_CANDLES_SHAPE = Block.createCuboidShape(5.0D, 0.0D, 5.0D, 11.0D, 6.0D, 10.0D);
    }
}