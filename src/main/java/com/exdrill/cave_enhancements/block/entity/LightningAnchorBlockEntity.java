package com.exdrill.cave_enhancements.block.entity;

import com.exdrill.cave_enhancements.registry.ModBlocks;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;

public class LightningAnchorBlockEntity extends BlockEntity {
    public int ticksTillActivate = 30;

    public LightningAnchorBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlocks.LIGHTNING_ANCHOR_BLOCK_ENTITY, pos, state);
    }

    public static void tick(Level world, BlockPos pos, LightningAnchorBlockEntity entity) {
        if(world.isClientSide()) return;

        if(entity.ticksTillActivate > 0){
            entity.ticksTillActivate--;
        }

        AABB box = new AABB(pos).inflate(1.5);

        List<Entity> list = world.getEntitiesOfClass(Entity.class, box, (e) -> true);

        Entity otherEntity;
        for (Entity value : list) {
            otherEntity = value;
            if (otherEntity.getClass() == LightningBolt.class && entity.ticksTillActivate <= 0) {
                world.setBlockAndUpdate(pos, ModBlocks.CHARGED_LIGHTNING_ANCHOR.defaultBlockState());
                return;
            }
        }
    }
}
