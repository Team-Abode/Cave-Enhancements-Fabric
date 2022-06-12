package com.exdrill.cave_enhancements.block.entity;

import com.exdrill.cave_enhancements.block.SpectacleCandleBlock;
import com.exdrill.cave_enhancements.registry.ModBlocks;
import java.util.Iterator;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;

public class SpectacleCandleBlockEntity extends BlockEntity {
    public SpectacleCandleBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlocks.SPECTACLE_CANDLE_BLOCK_ENTITY, pos, state);
    }

    public static void tick(Level world, BlockPos pos, BlockState state, SpectacleCandleBlockEntity entity) {
        if(!state.getValue(SpectacleCandleBlock.LIT)) return;

        int range = 3;

        if(state.getValue(SpectacleCandleBlock.CANDLES) == 2){
            range = 5;
        }else if(state.getValue(SpectacleCandleBlock.CANDLES) == 3){
            range = 7;
        }else if(state.getValue(SpectacleCandleBlock.CANDLES) == 4){
            range = 9;
        }

        AABB box = new AABB(pos).inflate(range - 1);

        List<Entity> list = world.getEntitiesOfClass(Entity.class, box, (e) -> {return true;});

        Entity otherEntity;
        for(Iterator var2 = list.iterator(); var2.hasNext();) {
            otherEntity = (Entity)var2.next();

            if(otherEntity.getClass() == ServerPlayer.class){
                ((Player)otherEntity).addEffect(new MobEffectInstance(MobEffects.NIGHT_VISION, 300, 0, true, true));
            }
        }
    }
}
