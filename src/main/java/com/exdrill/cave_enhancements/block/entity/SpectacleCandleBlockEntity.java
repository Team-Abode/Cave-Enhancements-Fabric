package com.exdrill.cave_enhancements.block.entity;

import com.exdrill.cave_enhancements.block.SpectacleCandleBlock;
import com.exdrill.cave_enhancements.registry.ModBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;

import java.util.Iterator;
import java.util.List;

public class SpectacleCandleBlockEntity extends BlockEntity {
    public SpectacleCandleBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlocks.SPECTACLE_CANDLE_BLOCK_ENTITY, pos, state);
    }

    public static void tick(World world, BlockPos pos, BlockState state, SpectacleCandleBlockEntity entity) {
        if(!state.get(SpectacleCandleBlock.LIT)) return;

        int range = 3;

        if(state.get(SpectacleCandleBlock.CANDLES) == 2){
            range = 5;
        }else if(state.get(SpectacleCandleBlock.CANDLES) == 3){
            range = 7;
        }else if(state.get(SpectacleCandleBlock.CANDLES) == 4){
            range = 9;
        }

        Box box = new Box(pos).expand(range - 1);

        List<Entity> list = world.getEntitiesByClass(Entity.class, box, (e) -> {return true;});

        Entity otherEntity;
        for(Iterator var2 = list.iterator(); var2.hasNext();) {
            otherEntity = (Entity)var2.next();

            if(otherEntity.getClass() == ServerPlayerEntity.class){
                ((PlayerEntity)otherEntity).addStatusEffect(new StatusEffectInstance(StatusEffects.NIGHT_VISION, 300, 0, true, true));
            }
        }
    }
}
