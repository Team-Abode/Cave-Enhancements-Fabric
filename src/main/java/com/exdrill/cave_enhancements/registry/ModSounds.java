package com.exdrill.cave_enhancements.registry;

import com.exdrill.cave_enhancements.CaveEnhancements;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ModSounds {

    public static final Identifier GLOW_PASTE_PLACE_SOUND = new Identifier(CaveEnhancements.MODID,"item.glow_paste.place");
    public static SoundEvent GLOW_PASTE_PLACE_SOUND_EVENT = new SoundEvent(GLOW_PASTE_PLACE_SOUND);

    public static final Identifier BLOCK_ROSE_QUARTZ_BREAK_ID = new Identifier(CaveEnhancements.MODID, "block.rose_quartz.break");
    public static SoundEvent BLOCK_ROSE_QUARTZ_BREAK = new SoundEvent(BLOCK_ROSE_QUARTZ_BREAK_ID);

    // Goop Block Sounds
    public static final Identifier BLOCK_GOOP_BLOCK_BREAK_ID = new Identifier(CaveEnhancements.MODID, "block.goop_block.break");
    public static SoundEvent BLOCK_GOOP_BLOCK_BREAK = new SoundEvent(BLOCK_GOOP_BLOCK_BREAK_ID);

    public static final Identifier BLOCK_GOOP_BLOCK_STEP_ID = new Identifier(CaveEnhancements.MODID, "block.goop_block.step");
    public static SoundEvent BLOCK_GOOP_BLOCK_STEP = new SoundEvent(BLOCK_GOOP_BLOCK_STEP_ID);

    public static final Identifier BLOCK_GOOP_BLOCK_PLACE_ID = new Identifier(CaveEnhancements.MODID, "block.goop_block.place");
    public static SoundEvent BLOCK_GOOP_BLOCK_PLACE = new SoundEvent(BLOCK_GOOP_BLOCK_PLACE_ID);

    public static final Identifier BLOCK_GOOP_BLOCK_HIT_ID = new Identifier(CaveEnhancements.MODID, "block.goop_block.hit");
    public static SoundEvent BLOCK_GOOP_BLOCK_HIT = new SoundEvent(BLOCK_GOOP_BLOCK_HIT_ID);

    public static final Identifier BLOCK_GOOP_BLOCK_FALL_ID = new Identifier(CaveEnhancements.MODID, "block.goop_block.fall");
    public static SoundEvent BLOCK_GOOP_BLOCK_FALL = new SoundEvent(BLOCK_GOOP_BLOCK_FALL_ID);

    // Goop Decoration Sounds
    public static final Identifier BLOCK_GOOP_DECORATION_BREAK_ID = new Identifier(CaveEnhancements.MODID, "block.goop_decoration.break");
    public static SoundEvent BLOCK_GOOP_DECORATION_BREAK = new SoundEvent(BLOCK_GOOP_DECORATION_BREAK_ID);

    public static final Identifier BLOCK_GOOP_DECORATION_STEP_ID = new Identifier(CaveEnhancements.MODID, "block.goop_decoration.step");
    public static SoundEvent BLOCK_GOOP_DECORATION_STEP = new SoundEvent(BLOCK_GOOP_DECORATION_STEP_ID);

    public static final Identifier BLOCK_GOOP_DECORATION_PLACE_ID = new Identifier(CaveEnhancements.MODID, "block.goop_decoration.place");
    public static SoundEvent BLOCK_GOOP_DECORATION_PLACE = new SoundEvent(BLOCK_GOOP_DECORATION_PLACE_ID);

    public static final Identifier BLOCK_GOOP_DECORATION_HIT_ID = new Identifier(CaveEnhancements.MODID, "block.goop_decoration.hit");
    public static SoundEvent BLOCK_GOOP_DECORATION_HIT = new SoundEvent(BLOCK_GOOP_DECORATION_HIT_ID);

    public static final Identifier BLOCK_GOOP_DECORATION_FALL_ID = new Identifier(CaveEnhancements.MODID, "block.goop_decoration.fall");
    public static SoundEvent BLOCK_GOOP_DECORATION_FALL = new SoundEvent(BLOCK_GOOP_DECORATION_FALL_ID);

    // Goop Entity Sounds
    public static final Identifier ENTITY_GOOP_HURT_ID = new Identifier(CaveEnhancements.MODID, "entity.goop.hurt");
    public static SoundEvent ENTITY_GOOP_HURT = new SoundEvent(ENTITY_GOOP_HURT_ID);

    public static final Identifier ENTITY_GOOP_DEATH_ID = new Identifier(CaveEnhancements.MODID, "entity.goop.death");
    public static SoundEvent ENTITY_GOOP_DEATH = new SoundEvent(ENTITY_GOOP_DEATH_ID);

    // Dripstone Tortoise Entity Sounds
    public static final Identifier ENTITY_DRIPSTONE_TORTOISE_HURT_ID = new Identifier(CaveEnhancements.MODID, "entity.dripstone_tortoise.hurt");
    public static SoundEvent ENTITY_DRIPSTONE_TORTOISE_HURT = new SoundEvent(ENTITY_DRIPSTONE_TORTOISE_HURT_ID);

    public static final Identifier ENTITY_DRIPSTONE_TORTOISE_DEATH_ID = new Identifier(CaveEnhancements.MODID, "entity.dripstone_tortoise.death");
    public static SoundEvent ENTITY_DRIPSTONE_TORTOISE_DEATH = new SoundEvent(ENTITY_DRIPSTONE_TORTOISE_DEATH_ID);

    public static final Identifier ENTITY_DRIPSTONE_TORTOISE_STEP_ID = new Identifier(CaveEnhancements.MODID, "entity.dripstone_tortoise.step");
    public static SoundEvent ENTITY_DRIPSTONE_TORTOISE_STEP = new SoundEvent(ENTITY_DRIPSTONE_TORTOISE_STEP_ID);

    public static final Identifier ENTITY_DRIPSTONE_TORTOISE_IDLE_ID = new Identifier(CaveEnhancements.MODID, "entity.dripstone_tortoise.idle");
    public static SoundEvent ENTITY_DRIPSTONE_TORTOISE_IDLE = new SoundEvent(ENTITY_DRIPSTONE_TORTOISE_IDLE_ID);

    // Block Sounds
    public static final BlockSoundGroup ROSE_QUARTZ  = new BlockSoundGroup(1.0F, 1.0F, BLOCK_ROSE_QUARTZ_BREAK, SoundEvents.BLOCK_GLASS_STEP, SoundEvents.BLOCK_CALCITE_PLACE, SoundEvents.BLOCK_GLASS_HIT, SoundEvents.BLOCK_GLASS_FALL);
    public static final BlockSoundGroup GOOP_BLOCK = new BlockSoundGroup(1.0F, 1.0F, BLOCK_GOOP_BLOCK_BREAK, BLOCK_GOOP_BLOCK_STEP, BLOCK_GOOP_BLOCK_PLACE, BLOCK_GOOP_BLOCK_HIT, BLOCK_GOOP_BLOCK_FALL);
    public static final BlockSoundGroup GOOP_DECORATION = new BlockSoundGroup(1.0F, 1.0F, BLOCK_GOOP_DECORATION_BREAK, BLOCK_GOOP_DECORATION_STEP, BLOCK_GOOP_DECORATION_PLACE, BLOCK_GOOP_DECORATION_HIT, BLOCK_GOOP_DECORATION_FALL);

    public static void register() {
        Registry.register(Registry.SOUND_EVENT, GLOW_PASTE_PLACE_SOUND, GLOW_PASTE_PLACE_SOUND_EVENT);
        Registry.register(Registry.SOUND_EVENT, BLOCK_ROSE_QUARTZ_BREAK_ID, BLOCK_ROSE_QUARTZ_BREAK);

        Registry.register(Registry.SOUND_EVENT, BLOCK_GOOP_BLOCK_BREAK_ID, BLOCK_GOOP_BLOCK_BREAK);
        Registry.register(Registry.SOUND_EVENT, BLOCK_GOOP_BLOCK_STEP_ID, BLOCK_GOOP_BLOCK_STEP);
        Registry.register(Registry.SOUND_EVENT, BLOCK_GOOP_BLOCK_PLACE_ID, BLOCK_GOOP_BLOCK_PLACE);
        Registry.register(Registry.SOUND_EVENT, BLOCK_GOOP_BLOCK_HIT_ID, BLOCK_GOOP_BLOCK_HIT);
        Registry.register(Registry.SOUND_EVENT, BLOCK_GOOP_BLOCK_FALL_ID, BLOCK_GOOP_BLOCK_FALL);

        Registry.register(Registry.SOUND_EVENT, BLOCK_GOOP_DECORATION_BREAK_ID, BLOCK_GOOP_DECORATION_BREAK);
        Registry.register(Registry.SOUND_EVENT, BLOCK_GOOP_DECORATION_STEP_ID, BLOCK_GOOP_DECORATION_STEP);
        Registry.register(Registry.SOUND_EVENT, BLOCK_GOOP_DECORATION_PLACE_ID, BLOCK_GOOP_DECORATION_PLACE);
        Registry.register(Registry.SOUND_EVENT, BLOCK_GOOP_DECORATION_HIT_ID, BLOCK_GOOP_DECORATION_HIT);
        Registry.register(Registry.SOUND_EVENT, BLOCK_GOOP_DECORATION_FALL_ID, BLOCK_GOOP_DECORATION_FALL);

        Registry.register(Registry.SOUND_EVENT, ENTITY_GOOP_HURT_ID, ENTITY_GOOP_HURT);
        Registry.register(Registry.SOUND_EVENT, ENTITY_GOOP_DEATH_ID, ENTITY_GOOP_DEATH);

        Registry.register(Registry.SOUND_EVENT, ENTITY_DRIPSTONE_TORTOISE_HURT_ID, ENTITY_DRIPSTONE_TORTOISE_HURT);
        Registry.register(Registry.SOUND_EVENT, ENTITY_DRIPSTONE_TORTOISE_DEATH_ID, ENTITY_DRIPSTONE_TORTOISE_DEATH);
        Registry.register(Registry.SOUND_EVENT, ENTITY_DRIPSTONE_TORTOISE_STEP_ID, ENTITY_DRIPSTONE_TORTOISE_STEP);
        Registry.register(Registry.SOUND_EVENT, ENTITY_DRIPSTONE_TORTOISE_IDLE_ID, ENTITY_DRIPSTONE_TORTOISE_IDLE);
    }


}
