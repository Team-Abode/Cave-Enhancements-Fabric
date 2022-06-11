package com.exdrill.cave_enhancements.item;

import com.exdrill.cave_enhancements.entity.HarmonicArrowEntity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ArrowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class HarmonicArrowItem extends ArrowItem {

    public HarmonicArrowItem(Properties settings) {
        super(settings);
    }

    @Override
    public AbstractArrow createArrow(Level world, ItemStack stack, LivingEntity shooter) {
        return new HarmonicArrowEntity(world, shooter);
    }
}
