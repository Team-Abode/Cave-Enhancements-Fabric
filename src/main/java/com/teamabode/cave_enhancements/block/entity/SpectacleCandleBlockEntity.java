package com.teamabode.cave_enhancements.block.entity;

import com.teamabode.cave_enhancements.block.SpectacleCandleBlock;
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

public class SpectacleCandleBlockEntity extends BlockEntity {
    public SpectacleCandleBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.SPECTACLE_CANDLE, pos, state);
    }

    public static void tick(Level level, BlockPos pos, BlockState state) {

        if(!state.getValue(SpectacleCandleBlock.LIT)) return;

        int range = 0;

        switch (state.getValue(SpectacleCandleBlock.CANDLES)) {
            case 1 -> range = 3;
            case 2 -> range = 5;
            case 3 -> range = 7;
            case 4 -> range = 9;
        }

        AABB box = new AABB(pos).inflate(range - 1);

        List<Player> list = level.getEntitiesOfClass(Player.class, box);
        Iterator<Player> iterator = list.iterator();

        if (level.getGameTime() % 40L == 0) {
            Player player;
            while (iterator.hasNext()) {
                player = iterator.next();
                player.addEffect(new MobEffectInstance(MobEffects.NIGHT_VISION, 300, 0, true, true));

            }
        }
    }
}
