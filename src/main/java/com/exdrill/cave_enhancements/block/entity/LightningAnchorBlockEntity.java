package com.exdrill.cave_enhancements.block.entity;

import com.exdrill.cave_enhancements.registry.ModBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LightningEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;

import java.util.List;

public class LightningAnchorBlockEntity extends BlockEntity {
    public int ticksTillActivate = 30;

    public LightningAnchorBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlocks.LIGHTNING_ANCHOR_BLOCK_ENTITY, pos, state);
    }

    public static void tick(World world, BlockPos pos, BlockState state, LightningAnchorBlockEntity entity) {
        if(world.isClient()) return;

        if(entity.ticksTillActivate > 0){
            entity.ticksTillActivate--;
        }

        Box box = new Box(pos).expand(1.5);

        List<Entity> list = world.getEntitiesByClass(Entity.class, box, (e) -> true);

        Entity otherEntity;
        for (Entity value : list) {
            otherEntity = value;
            if (otherEntity.getClass() == LightningEntity.class && entity.ticksTillActivate <= 0) {
                world.setBlockState(pos, ModBlocks.CHARGED_LIGHTNING_ANCHOR.getDefaultState());
                return;
            }
        }
    }
}
