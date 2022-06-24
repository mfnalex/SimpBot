package com.github.cxlina.smpbot.util;

public class TranslationUtil {

    public static String translateDamageReason(String key) {
        return switch (key) {
            case "CONTACT" -> "Contact";
            case "ENTITY_ATTACK", "ENTITY_SWEEP_ATTACK" -> "Entity Attack";
            case "PROJECTILE" -> "Projectile";
            case "SUFFOCATION" -> "Suffocation";
            case "FALL" -> "Fall Damage";
            case "FIRE" -> "Fire";
            case "FIRE_TICK" -> "Fire Tick";
            case "MELTING" -> "Melting";
            case "LAVA" -> "Lava";
            case "DROWNING" -> "Drowning";
            case "BLOCK_EXPLOSION", "ENTITY_EXPLOSION" -> "Explosion";
            case "VOID" -> "Void";
            case "LIGHTNING" -> "Lightning";
            case "SUICIDE" -> "Suicide";
            case "STARVATION" -> "Hunger";
            case "POISON" -> "Poison";
            case "MAGIC" -> "Magic";
            case "WITHER" -> "Wither";
            case "FALLING_BLOCK" -> "Falling Block";
            case "THORNS" -> "Thorns";
            case "DRAGON_BREATH" -> "Dragon Breath";
            case "FLY_INTO_WALL" -> "Kinetic Energy";
            case "HOT_FLOOR" -> "Magma Block";
            case "CRAMMING" -> "Entity Cramming";
            case "DRYOUT" -> "Dryout";
            case "FREEZE" -> "Freeze";
            case "SONIC_BOOM" -> "Sonic Boom";
            default -> "Unknown";
        };
    }
}