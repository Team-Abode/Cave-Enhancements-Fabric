package com.teamabode.cave_enhancements.block.entity;

import com.teamabode.cave_enhancements.block.SpectacleCandleCakeBlock;
import com.teamabode.cave_enhancements.registry.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;

import java.util.Iterator;
import java.util.List;

public class SpectacleCandleCakeBlockEntity extends BlockEntity {

    public SpectacleCandleCakeBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(ModBlockEntities.SPECTACLE_CANDLE_CAKE, blockPos, blockState);
    }

    public static void tick(Level level, BlockPos pos, BlockState state) {

        if (level.getGameTime() % 40L == 0) {
            if (!state.getValue(SpectacleCandleCakeBlock.LIT)) return;

            AABB box = new AABB(pos).inflate(2);

            List<Player> list = level.getEntitiesOfClass(Player.class, box);
            Iterator<Player> iterator = list.iterator();

            Player player;
            while (iterator.hasNext()) {
                player = iterator.next();
                player.addEffect(new MobEffectInstance(MobEffects.NIGHT_VISION, 300, 0, true, true));
            }
        }

    }
}
