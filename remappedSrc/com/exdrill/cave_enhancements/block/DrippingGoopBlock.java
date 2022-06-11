package com.exdrill.cave_enhancements.block;

import com.exdrill.cave_enhancements.registry.ModBlocks;
import com.exdrill.cave_enhancements.registry.ModParticles;
import net.minecraft.block.*;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

public class DrippingGoopBlock extends Block implements SimpleWaterloggedBlock {

    private static final BooleanProperty WATERLOGGED;

    public static final BooleanProperty HANGING = BooleanProperty.create("hanging");

    public static final VoxelShape SHAPE;

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> stateManager) {
        stateManager.add(HANGING).add(WATERLOGGED);
    }

    static {
        WATERLOGGED = BlockStateProperties.WATERLOGGED;
    }

    public FluidState getFluidState(BlockState state) {
        return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
    }

    public DrippingGoopBlock(Properties settings) {
        super(settings);
        registerDefaultState(getStateDefinition().any().setValue(HANGING, true));
        this.registerDefaultState(this.defaultBlockState().setValue(WATERLOGGED, false));
    }

    public BlockState getStateForPlacement(BlockPlaceContext ctx) {
        FluidState fluidState = ctx.getLevel().getFluidState(ctx.getClickedPos());
        boolean waterCheck = fluidState.getType() == Fluids.WATER;
        BlockState blockState = super.defaultBlockState();
        return blockState.setValue(WATERLOGGED, waterCheck);
    }

    public static boolean canDrip(BlockState state) {
        return state.getValue(HANGING) && !(Boolean)state.getValue(WATERLOGGED);
    }

    public BlockState updateShape(BlockState state, Direction direction, BlockState neighborState, LevelAccessor world, BlockPos pos, BlockPos neighborPos) {
        if (state.getValue(WATERLOGGED)) {
            world.scheduleTick(pos, Fluids.WATER, Fluids.WATER.getTickDelay(world));
        }
        return direction == Direction.DOWN ? state.setValue(HANGING, this.HangingState(neighborState)) : direction == Direction.UP && !this.canSurvive(state, world, pos) ? Blocks.AIR.defaultBlockState() : super.updateShape(state, direction, neighborState, world, pos, neighborPos);
    }

    private boolean HangingState(BlockState state) {
        return !state.is(ModBlocks.DRIPPING_GOOP);
    }

    public boolean canSurvive(BlockState state, LevelReader world, BlockPos pos) {
        return Block.canSupportCenter(world, pos.above(), Direction.DOWN) || world.getBlockState(pos.above()).is(ModBlocks.DRIPPING_GOOP);
    }

    public void animateTick(BlockState state, Level world, BlockPos pos, RandomSource random) {
        if (canDrip(state)) {
            float f = random.nextFloat();
            if (!(f > 0.1F)) {
                createParticle(world, pos, state);
            }
        }
    }

    public static void createParticle(Level world, BlockPos pos, BlockState state) {
        if (world.isClientSide) {
            Vec3 vec3d = state.getOffset(world, pos);
            double e = (double)pos.getX() + 0.5D + vec3d.x;
            double f = (double)((float)(pos.getY() + 1) - 0.8F) - 0.0625D;
            double g = (double)pos.getZ() + 0.5D + vec3d.z;
            ParticleOptions particleEffect = ModParticles.SMALL_GOOP_DRIP;
            world.addParticle(particleEffect, e, f, g, 0.0D, -0.5D, 0.0D);
        }
    }

    static {
        SHAPE = Block.box(1,0,1,15,16,15);
    }

    @Override
    public void setPlacedBy(Level world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack itemStack) {
        super.setPlacedBy(world, pos, state, placer, itemStack);
        this.registerDefaultState(this.defaultBlockState().setValue(WATERLOGGED, world.getFluidState(pos).getType() == Fluids.WATER));
    }

    @Override
    public VoxelShape getShape(BlockState blockState, BlockGetter blockView, BlockPos pos, CollisionContext shapeContext) {
        return SHAPE;
    }



    public boolean propagatesSkylightDown(BlockState state, BlockGetter world, BlockPos pos) {
        return state.getFluidState().isEmpty();
    }
}
