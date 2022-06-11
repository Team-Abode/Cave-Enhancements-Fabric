package com.exdrill.cave_enhancements.client.render.entity.animation;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.animation.Animation;
import net.minecraft.client.render.entity.animation.Animation.Builder;
import net.minecraft.client.render.entity.animation.AnimationHelper;
import net.minecraft.client.render.entity.animation.Keyframe;
import net.minecraft.client.render.entity.animation.Transformation;
import net.minecraft.client.render.entity.animation.Transformation.Interpolations;
import net.minecraft.client.render.entity.animation.Transformation.Targets;

@Environment(EnvType.CLIENT)
public class DripstoneTortoiseAnimations {

    public static final Animation STOMPING;
    public static final Animation RISING;

    // field_37884 = LINEAR
    // field_37885 = CATMULLROM

    static {
        STOMPING = Builder.create(0.52F)
                .addBoneAnimation("body",
                        new Transformation(Targets.ROTATE,
                                new Keyframe(0.0F, AnimationHelper.method_41829(0.0F, 0.0F, 0.0F), Interpolations.field_37885),
                                new Keyframe(0.16F, AnimationHelper.method_41829(0.0F, 0.0F, 12.5F), Interpolations.field_37885),
                                new Keyframe(0.28F, AnimationHelper.method_41829(0.0F, 0.0F, 0.0F), Interpolations.field_37885)))
                .addBoneAnimation("head",
                        new Transformation(Targets.ROTATE,
                                new Keyframe(0.0F, AnimationHelper.method_41829(0.0F, 0.0F, 0.0F), Interpolations.field_37885),
                                new Keyframe(0.16F, AnimationHelper.method_41829(-25.0F, 0.0F, 0.0F), Interpolations.field_37885),
                                new Keyframe(0.28F, AnimationHelper.method_41829(20.0F, 0.0F, 0.0F), Interpolations.field_37885),
                                new Keyframe(0.48F, AnimationHelper.method_41829(0.0F, 0.0F, 0.0F), Interpolations.field_37885)))
                .addBoneAnimation("leg0", new Transformation(Targets.ROTATE,
                                new Keyframe(0.0F, AnimationHelper.method_41829(0.0F, 0.0F, 0.0F), Interpolations.field_37885),
                                new Keyframe(0.16F, AnimationHelper.method_41829(0.0F, 0.0F, 45.0F), Interpolations.field_37885),
                                new Keyframe(0.28F, AnimationHelper.method_41829(0.0F, 0.0F, 0.0F), Interpolations.field_37885)))
                .addBoneAnimation("leg1", new Transformation(Targets.ROTATE,
                        new Keyframe(0.0F, AnimationHelper.method_41829(0.0F, 0.0F, 0.0F), Interpolations.field_37885),
                        new Keyframe(0.16F, AnimationHelper.method_41829(0.0F, 0.0F, 22.5F), Interpolations.field_37885),
                        new Keyframe(0.28F, AnimationHelper.method_41829(0.0F, 0.0F, 0.0F), Interpolations.field_37885)))
                .build();

        RISING = Builder.create(0.6F)
                .addBoneAnimation("pike", new Transformation(Targets.SCALE,
                        new Keyframe(0.0F, AnimationHelper.method_41822(0.0F, 0.0F, 0.0F), Interpolations.field_37884),
                        new Keyframe(0.1F, AnimationHelper.method_41822(1.0F, 1.0F, 1.0F), Interpolations.field_37884)))

                .addBoneAnimation("pike",
                        new Transformation(Targets.TRANSLATE,
                            new Keyframe(0.0F, AnimationHelper.method_41823(0.0F, 0.0F, 0.0F), Interpolations.field_37884),
                            new Keyframe(0.1F, AnimationHelper.method_41823(0.0F, 24.0F, 0.0F), Interpolations.field_37884),
                            new Keyframe( 0.5F, AnimationHelper.method_41823(0.0F, 24.0F, 0.0F), Interpolations.field_37884),
                            new Keyframe(0.6F, AnimationHelper.method_41823(0.0F, 0.0F, 0.0F), Interpolations.field_37884))).build();
    }
}
