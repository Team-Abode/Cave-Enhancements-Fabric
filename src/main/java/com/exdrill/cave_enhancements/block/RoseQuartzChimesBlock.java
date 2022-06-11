package com.exdrill.cave_enhancements.block;

import com.exdrill.cave_enhancements.block.entity.RoseQuartzChimesBlockEntity;
import com.exdrill.cave_enhancements.registry.ModBlocks;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;

public class RoseQuartzChimesBlock extends BlockWithEntity {

    public RoseQuartzChimesBlock (FabricBlockSettings settings){
        super(settings);
    }

    public static final VoxelShape COLLISION_SHAPE;
    public static final VoxelShape RAYCAST_SHAPE;

    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new RoseQuartzChimesBlockEntity(pos, state);
    }

    @Override
    public boolean isTranslucent(BlockState state, BlockView world, BlockPos pos) {
        return super.isTranslucent(state, world, pos);
    }

    @Override
    public VoxelShape getRaycastShape(BlockState state, BlockView world, BlockPos pos) {
        return RAYCAST_SHAPE;
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return COLLISION_SHAPE;
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return checkType(type, ModBlocks.ROSE_QUARTZ_CHIMES_BLOCK_ENTITY, RoseQuartzChimesBlockEntity::tick);
    }

    public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        return Block.sideCoversSmallSquare(world, pos.up(), Direction.DOWN) || world.getBlockState(pos.up()).isIn(BlockTags.LEAVES);
    }

    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        return direction == Direction.UP && !this.canPlaceAt(state, world, pos) ? Blocks.AIR.getDefaultState() : super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
    }

    static {
        COLLISION_SHAPE = createCuboidShape(0,12,0,16,16,16);
        RAYCAST_SHAPE = createCuboidShape(0,0,0,16,16,16);
    }
}
