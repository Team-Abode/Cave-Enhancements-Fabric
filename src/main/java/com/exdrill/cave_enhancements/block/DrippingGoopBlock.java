package com.exdrill.cave_enhancements.block;

import com.exdrill.cave_enhancements.registry.ModBlocks;
import com.exdrill.cave_enhancements.registry.ModParticles;
import net.minecraft.block.*;
import net.minecraft.entity.LivingEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;
import org.jetbrains.annotations.Nullable;

public class DrippingGoopBlock extends Block implements Waterloggable {

    private static final BooleanProperty WATERLOGGED;

    public static final BooleanProperty HANGING = BooleanProperty.of("hanging");

    public static final VoxelShape SHAPE;

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> stateManager) {
        stateManager.add(HANGING).add(WATERLOGGED);
    }

    static {
        WATERLOGGED = Properties.WATERLOGGED;
    }

    public FluidState getFluidState(BlockState state) {
        return state.get(WATERLOGGED) ? Fluids.WATER.getStill(false) : super.getFluidState(state);
    }

    public DrippingGoopBlock(Settings settings) {
        super(settings);
        setDefaultState(getStateManager().getDefaultState().with(HANGING, true));
        this.setDefaultState(this.getDefaultState().with(WATERLOGGED, false));
    }

    public BlockState getPlacementState(ItemPlacementContext ctx) {
        FluidState fluidState = ctx.getWorld().getFluidState(ctx.getBlockPos());
        boolean waterCheck = fluidState.getFluid() == Fluids.WATER;
        BlockState blockState = super.getDefaultState();
        return blockState.with(WATERLOGGED, waterCheck);
    }

    public static boolean canDrip(BlockState state) {
        return state.get(HANGING) && !(Boolean)state.get(WATERLOGGED);
    }

    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        if (state.get(WATERLOGGED)) {
            world.createAndScheduleFluidTick(pos, Fluids.WATER, Fluids.WATER.getTickRate(world));
        }
        return direction == Direction.DOWN ? state.with(HANGING, this.HangingState(neighborState)) : direction == Direction.UP && !this.canPlaceAt(state, world, pos) ? Blocks.AIR.getDefaultState() : super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
    }

    private boolean HangingState(BlockState state) {
        return !state.isOf(ModBlocks.DRIPPING_GOOP);
    }

    public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        return Block.sideCoversSmallSquare(world, pos.up(), Direction.DOWN) || world.getBlockState(pos.up()).isOf(ModBlocks.DRIPPING_GOOP);
    }

    public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
        if (canDrip(state)) {
            float f = random.nextFloat();
            if (!(f > 0.1F)) {
                createParticle(world, pos, state);
            }
        }
    }

    public static void createParticle(World world, BlockPos pos, BlockState state) {
        if (world.isClient) {
            Vec3d vec3d = state.getModelOffset(world, pos);
            double e = (double)pos.getX() + 0.5D + vec3d.x;
            double f = (double)((float)(pos.getY() + 1) - 0.8F) - 0.0625D;
            double g = (double)pos.getZ() + 0.5D + vec3d.z;
            ParticleEffect particleEffect = ModParticles.SMALL_GOOP_DRIP;
            world.addParticle(particleEffect, e, f, g, 0.0D, -0.5D, 0.0D);
        }
    }

    static {
        SHAPE = Block.createCuboidShape(1,0,1,15,16,15);
    }

    @Override
    public void onPlaced(World world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack itemStack) {
        super.onPlaced(world, pos, state, placer, itemStack);
        this.setDefaultState(this.getDefaultState().with(WATERLOGGED, world.getFluidState(pos).getFluid() == Fluids.WATER));
    }

    @Override
    public VoxelShape getOutlineShape(BlockState blockState, BlockView blockView, BlockPos pos, ShapeContext shapeContext) {
        return SHAPE;
    }



    public boolean isTranslucent(BlockState state, BlockView world, BlockPos pos) {
        return state.getFluidState().isEmpty();
    }
}
