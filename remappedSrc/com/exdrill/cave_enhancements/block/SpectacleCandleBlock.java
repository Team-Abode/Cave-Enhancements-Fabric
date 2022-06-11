package com.exdrill.cave_enhancements.block;

import com.exdrill.cave_enhancements.block.entity.SpectacleCandleBlockEntity;
import com.exdrill.cave_enhancements.registry.ModBlocks;
import com.google.common.collect.ImmutableList;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectMaps;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import net.minecraft.Util;
import net.minecraft.block.*;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.AbstractCandleBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import java.util.List;
import java.util.function.ToIntFunction;

public class SpectacleCandleBlock extends AbstractSpectacleCandleBlock implements EntityBlock  {
    public static final int field_31050 = 1;
    public static final int field_31051 = 4;
    public static final IntegerProperty CANDLES;
    public static final BooleanProperty LIT;
    public static final BooleanProperty WATERLOGGED;
    public static final ToIntFunction<BlockState> STATE_TO_LUMINANCE;
    private static final Int2ObjectMap<List<Vec3>> CANDLES_TO_PARTICLE_OFFSETS;
    private static final VoxelShape ONE_CANDLE_SHAPE;
    private static final VoxelShape TWO_CANDLES_SHAPE;
    private static final VoxelShape THREE_CANDLES_SHAPE;
    private static final VoxelShape FOUR_CANDLES_SHAPE;

    public SpectacleCandleBlock(Properties settings) {
        super(settings);
        this.registerDefaultState(this.stateDefinition.any().setValue(CANDLES, 1).setValue(LIT, false).setValue(WATERLOGGED, false));
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new SpectacleCandleBlockEntity(pos, state);
    }

    public InteractionResult use(BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        if (player.getAbilities().mayBuild && player.getItemInHand(hand).isEmpty() && state.getValue(LIT)) {
            extinguish(player, state, world, pos);
            return InteractionResult.sidedSuccess(world.isClientSide);
        } else {
            return InteractionResult.PASS;
        }
    }

    public boolean canBeReplaced(BlockState state, BlockPlaceContext context) {
        return !context.isSecondaryUseActive() && context.getItemInHand().getItem() == this.asItem() && state.getValue(CANDLES) < 4 || super.canBeReplaced(state, context);
    }

    public BlockState getStateForPlacement(BlockPlaceContext ctx) {
        BlockState blockState = ctx.getLevel().getBlockState(ctx.getClickedPos());
        if (blockState.is(this)) {
            return blockState.cycle(CANDLES);
        } else {
            FluidState fluidState = ctx.getLevel().getFluidState(ctx.getClickedPos());
            boolean bl = fluidState.getType() == Fluids.WATER;
            return super.getStateForPlacement(ctx).setValue(WATERLOGGED, bl);
        }
    }

    public BlockState updateShape(BlockState state, Direction direction, BlockState neighborState, LevelAccessor world, BlockPos pos, BlockPos neighborPos) {
        if (state.getValue(WATERLOGGED)) {
            world.scheduleTick(pos, Fluids.WATER, Fluids.WATER.getTickDelay(world));
        }

        return super.updateShape(state, direction, neighborState, world, pos, neighborPos);
    }

    public FluidState getFluidState(BlockState state) {
        return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
    }

    public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        switch(state.getValue(CANDLES)) {
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

    public RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(CANDLES, LIT, WATERLOGGED);
    }

    public boolean tryFillWithFluid(LevelAccessor world, BlockPos pos, BlockState state, FluidState fluidState) {
        if (!(Boolean)state.getValue(WATERLOGGED) && fluidState.getType() == Fluids.WATER) {
            BlockState blockState = state.setValue(WATERLOGGED, true);
            if (state.getValue(LIT)) {
                extinguish(null, blockState, world, pos);
            } else {
                world.setBlock(pos, blockState, 3);
            }

            world.scheduleTick(pos, fluidState.getType(), fluidState.getType().getTickDelay(world));
            return true;
        } else {
            return false;
        }
    }

    public static boolean canBeLit(BlockState state) {
        return state.is(BlockTags.CANDLES, (statex) -> statex.hasProperty(LIT) && statex.hasProperty(WATERLOGGED)) && !(Boolean)state.getValue(LIT) && !(Boolean)state.getValue(WATERLOGGED);
    }

    protected Iterable<Vec3> getParticleOffsets(BlockState state) {
        return CANDLES_TO_PARTICLE_OFFSETS.get(state.getValue(CANDLES));
    }

    protected boolean isNotLit(BlockState state) {
        return !(Boolean)state.getValue(WATERLOGGED) && super.isNotLit(state);
    }

    public boolean canSurvive(BlockState state, LevelReader world, BlockPos pos) {
        return Block.canSupportCenter(world, pos.below(), Direction.UP);
    }

    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level world, BlockState state, BlockEntityType<T> type) {
        return createTickerHelper(type, ModBlocks.SPECTACLE_CANDLE_BLOCK_ENTITY, SpectacleCandleBlockEntity::tick);
    }

    static {
        CANDLES = BlockStateProperties.CANDLES;
        LIT = AbstractCandleBlock.LIT;
        WATERLOGGED = BlockStateProperties.WATERLOGGED;
        STATE_TO_LUMINANCE = (state) -> (Boolean)state.getValue(LIT) ? 3 * state.getValue(CANDLES) : 0;
        CANDLES_TO_PARTICLE_OFFSETS = Util.make(() -> {
            Int2ObjectMap<List<Vec3>> int2ObjectMap = new Int2ObjectOpenHashMap();
            int2ObjectMap.defaultReturnValue(ImmutableList.of());
            int2ObjectMap.put(1, ImmutableList.of(new Vec3(0.5D, 0.5D, 0.5D)));
            int2ObjectMap.put(2, ImmutableList.of(new Vec3(0.375D, 0.44D, 0.5D), new Vec3(0.625D, 0.5D, 0.44D)));
            int2ObjectMap.put(3, ImmutableList.of(new Vec3(0.5D, 0.313D, 0.625D), new Vec3(0.375D, 0.44D, 0.5D), new Vec3(0.56D, 0.5D, 0.44D)));
            int2ObjectMap.put(4, ImmutableList.of(new Vec3(0.44D, 0.313D, 0.56D), new Vec3(0.625D, 0.44D, 0.56D), new Vec3(0.375D, 0.44D, 0.375D), new Vec3(0.56D, 0.5D, 0.375D)));
            return Int2ObjectMaps.unmodifiable(int2ObjectMap);
        });
        ONE_CANDLE_SHAPE = Block.box(7.0D, 0.0D, 7.0D, 9.0D, 6.0D, 9.0D);
        TWO_CANDLES_SHAPE = Block.box(5.0D, 0.0D, 6.0D, 11.0D, 6.0D, 9.0D);
        THREE_CANDLES_SHAPE = Block.box(5.0D, 0.0D, 6.0D, 10.0D, 6.0D, 11.0D);
        FOUR_CANDLES_SHAPE = Block.box(5.0D, 0.0D, 5.0D, 11.0D, 6.0D, 10.0D);
    }
}