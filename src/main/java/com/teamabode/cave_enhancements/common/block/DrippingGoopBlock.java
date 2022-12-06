package com.teamabode.cave_enhancements.common.block;

import com.teamabode.cave_enhancements.core.registry.ModBlocks;
import com.teamabode.cave_enhancements.core.registry.ModParticles;
import com.teamabode.cave_enhancements.core.registry.ModSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
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
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

@SuppressWarnings("deprecation")
public class DrippingGoopBlock extends Block implements SimpleWaterloggedBlock {

    private static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    public static final BooleanProperty HANGING = BooleanProperty.create("hanging");

    public static final VoxelShape SHAPE = Block.box(1,0,1,15,16,15);;

    public DrippingGoopBlock(Properties settings) {
        super(settings);
        registerDefaultState(getStateDefinition().any().setValue(HANGING, true));
        this.registerDefaultState(this.defaultBlockState().setValue(WATERLOGGED, false));
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> stateManager) {
        stateManager.add(HANGING).add(WATERLOGGED);
    }

    public FluidState getFluidState(BlockState state) {
        return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
    }

    public BlockState getStateForPlacement(BlockPlaceContext ctx) {
        FluidState fluidState = ctx.getLevel().getFluidState(ctx.getClickedPos());
        boolean waterCheck = fluidState.getType() == Fluids.WATER;
        BlockState blockState = super.defaultBlockState();
        return blockState.setValue(WATERLOGGED, waterCheck);
    }


    // "Scaffold"-like Mechanic
    public InteractionResult use(BlockState blockState, Level level, BlockPos blockPos, Player player, InteractionHand interactionHand, BlockHitResult blockHitResult) {

        BlockPos pos = getBottomPos(blockPos, level);
        ItemStack itemStack = player.getItemInHand(interactionHand);

        if (level.getBlockState(pos).getMaterial().isReplaceable() && itemStack.getItem() == ModBlocks.DRIPPING_GOOP.asItem()) {
            level.setBlockAndUpdate(pos, ModBlocks.DRIPPING_GOOP.defaultBlockState().setValue(WATERLOGGED, level.isWaterAt(pos)));
            level.playSound(player, pos, ModSounds.BLOCK_GOOP_DECORATION_PLACE, SoundSource.BLOCKS, 1.0F, 1.0F);
            if (!player.isCreative()) {
                itemStack.shrink(1);
            }


            return InteractionResult.SUCCESS;
        } else {
            return InteractionResult.FAIL;
        }
    }

    private BlockPos getBottomPos(BlockPos pos, Level level) {
        Block block = this;
        while (block == ModBlocks.DRIPPING_GOOP) {
            pos = pos.below();
            BlockState state = level.getBlockState(pos);
            block = state.getBlock();
        }
        return pos;
    }

    // Dripping Goop Sliding
    public void entityInside(BlockState blockState, Level level, BlockPos blockPos, Entity entity) {
        if (this.isSlidingDown(entity)) {
            doSlideMovement(entity);
            slidingEffects(level, entity);
        }
        super.entityInside(blockState, level, blockPos, entity);
    }

    private void slidingEffects(Level level, Entity entity) {
        if (level.random.nextInt(30) == 0) {
            entity.playSound(ModSounds.BLOCK_GOOP_BLOCK_SLIDE, 1.0F, 1.0F);
        }
        if (level instanceof ServerLevel server && server.random.nextInt(5) == 0) {

            BlockState blockState = ModBlocks.DRIPPING_GOOP.defaultBlockState();

            server.sendParticles(new BlockParticleOption(ParticleTypes.BLOCK, blockState), entity.getX(), entity.getY(), entity.getZ(), 5, 0.0, 0.0, 0.0, 0.0);
        }
    }

    private void doSlideMovement(Entity entity) {
        Vec3 vec3 = entity.getDeltaMovement();
        if (vec3.y < -0.13) {
            double d = -0.05 / vec3.y;
            entity.setDeltaMovement(new Vec3(vec3.x * d, -0.35, vec3.z * d));
        } else {
            entity.setDeltaMovement(new Vec3(vec3.x, -0.05, vec3.z));
        }
        entity.resetFallDistance();
    }

    private boolean isSlidingDown(Entity entity) {
        return !entity.isOnGround();
    }

    public VoxelShape getShape(BlockState blockState, BlockGetter blockView, BlockPos pos, CollisionContext shapeContext) {
        return SHAPE;
    }

    public BlockState updateShape(BlockState state, Direction direction, BlockState neighborState, LevelAccessor world, BlockPos pos, BlockPos neighborPos) {
        if (state.getValue(WATERLOGGED)) {
            world.scheduleTick(pos, Fluids.WATER, Fluids.WATER.getTickDelay(world));
        }
        return direction == Direction.DOWN ? state.setValue(HANGING, this.getHangingState(neighborState)) : direction == Direction.UP && !this.canSurvive(state, world, pos) ? Blocks.AIR.defaultBlockState() : super.updateShape(state, direction, neighborState, world, pos, neighborPos);
    }

    private boolean getHangingState(BlockState state) {
        return !state.is(ModBlocks.DRIPPING_GOOP);
    }

    public boolean canSurvive(BlockState state, LevelReader world, BlockPos pos) {
        return Block.canSupportCenter(world, pos.above(), Direction.DOWN) || world.getBlockState(pos.above()).is(ModBlocks.DRIPPING_GOOP);
    }

    public void animateTick(BlockState state, Level world, BlockPos pos, RandomSource random) {
        if (state.getValue(HANGING)) {
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
}
