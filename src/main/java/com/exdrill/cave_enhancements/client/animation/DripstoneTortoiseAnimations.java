package com.exdrill.cave_enhancements.client.animation;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.animation.AnimationChannel;
import net.minecraft.client.animation.AnimationChannel.Interpolations;
import net.minecraft.client.animation.AnimationChannel.Targets;
import net.minecraft.client.animation.AnimationDefinition;
import net.minecraft.client.animation.AnimationDefinition.Builder;
import net.minecraft.client.animation.Keyframe;
import net.minecraft.client.animation.KeyframeAnimations;

@Environment(EnvType.CLIENT)
public class DripstoneTortoiseAnimations {

    public static final AnimationDefinition STOMPING;
    public static final AnimationDefinition RISING;

    static {
        STOMPING = Builder.withLength(0.52F)
                .addAnimation("body",
                        new AnimationChannel(Targets.ROTATION,
                                new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
                                new Keyframe(0.16F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 12.5F), Interpolations.CATMULLROM),
                                new Keyframe(0.28F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
                        )
                )
                .addAnimation("head",
                        new AnimationChannel(Targets.ROTATION,
                                new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
                                new Keyframe(0.16F, KeyframeAnimations.degreeVec(-25.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
                                new Keyframe(0.28F, KeyframeAnimations.degreeVec(20.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
                                new Keyframe(0.48F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
                        )
                )
                .addAnimation("leg0",
                        new AnimationChannel(Targets.ROTATION,
                                new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
                                new Keyframe(0.16F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 45.0F), Interpolations.CATMULLROM),
                                new Keyframe(0.28F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
                        )
                )
                .addAnimation("leg1",
                        new AnimationChannel(Targets.ROTATION,
                            new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
                            new Keyframe(0.16F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 22.5F), Interpolations.CATMULLROM),
                            new Keyframe(0.28F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
                        )
                )
                .build();

        RISING = Builder.withLength(0.6F)
                .addAnimation("pike",
                        new AnimationChannel(Targets.SCALE,
                            new Keyframe(0.0F, KeyframeAnimations.scaleVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR),
                            new Keyframe(0.1F, KeyframeAnimations.scaleVec(1.0F, 1.0F, 1.0F), Interpolations.LINEAR)
                        )
                )

                .addAnimation("pike",
                        new AnimationChannel(Targets.POSITION,
                            new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR),
                            new Keyframe(0.1F, KeyframeAnimations.posVec(0.0F, 24.0F, 0.0F), Interpolations.LINEAR),
                            new Keyframe( 0.5F, KeyframeAnimations.posVec(0.0F, 24.0F, 0.0F), Interpolations.LINEAR),
                            new Keyframe(0.6F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR)
                        )
                )
                .build();
    }
}
