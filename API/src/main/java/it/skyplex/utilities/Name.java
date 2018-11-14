package it.skyplex.utilities;

import org.bukkit.entity.EntityType;

import java.util.Map;
import java.util.NavigableMap;
import java.util.TreeMap;

public class Name {

    private static final NavigableMap<Long, String> suffixes = new TreeMap<>();
    static {
        suffixes.put(1_000L, "k");
        suffixes.put(1_000_000L, "M");
        suffixes.put(1_000_000_000L, "B");
        suffixes.put(1_000_000_000_000L, "T");
    }

    /**
     * Gets the potion name.
     * @param type
     * @return
     */
    public String getPotionName(String type) {
        switch (type) {
            case "speed":
                return "speed";
            case "slowness":
                return "slow";
            case "fast_digging":
                return "Haste";
            case "slow_digging":
                return "Mining fatigue";
            case "increase_damage":
                return "Strength";
            case "heal":
                return "Instant Health";
            case "harm":
                return "Instant Damage";
            case "jump":
                return "Jump Boost";
            case "confusion":
                return "Nausea";
            case "regeneration":
                return "Regeneration";
            case "damage_resistance":
                return "Resistance";
            case "fire_resistance":
                return "Fire Resistance";
            case "water_breathing":
                return "Water breathing";
            case "invisibility":
                return "Invisibility";
            case "blindness":
                return "Blindness";
            case "night_vision":
                return "Night Vision";
            case "hunger":
                return "Hunger";
            case "weakness":
                return "Weakness";
            case "poison":
                return "Poison";
            case "wither":
                return "Wither";
            case "health_boost":
                return "Health Boost";
            case "absorption":
                return "Absorption";
            case "saturation":
                return "Saturaion";
            default:
                return "?";
        }
    }

    /**
     * Gets the mob name.
     * @param type
     * @return
     */
    public String getMobName(EntityType type) {
        switch (type) {
            case ARMOR_STAND:
                return "Armor Stand";
            case ARROW:
                return "Arrow";
            case BAT:
                return "Bat";
            case BLAZE:
                return "Blaze";
            case BOAT:
                return "Boat";
            case CAVE_SPIDER:
                return "Cave Spider";
            case CHICKEN:
                return "Chicken";
            case COW:
                return "Cow";
            case CREEPER:
                return "Creeper";
            case DROPPED_ITEM:
                return "Dropped Item";
            case EGG:
                return "Egg";
            case ENDERMAN:
                return "Enderman";
            case ENDERMITE:
                return "Endermite";
            case ENDER_CRYSTAL:
                return "Ender Crystal";
            case ENDER_DRAGON:
                return "Ender Dragon";
            case ENDER_PEARL:
                return "Ender Pearl";
            case ENDER_SIGNAL:
                return "Ender Signal";
            case EXPERIENCE_ORB:
                return "Exp. Orb";
            case FALLING_BLOCK:
                return "Falling Block";
            case FIREBALL:
                return "Fireball";
            case FIREWORK:
                return "Firework";
            case FISHING_HOOK:
                return "Fish. Hook";
            case GHAST:
                return "Ghast";
            case GIANT:
                return "Giant";
            case GUARDIAN:
                return "Guardian";
            case HORSE:
                return "Horse";
            case IRON_GOLEM:
                return "Iron Golem";
            case ITEM_FRAME:
                return "Item Frame";
            case LEASH_HITCH:
                return "Leash";
            case LIGHTNING:
                return "Lightning";
            case MAGMA_CUBE:
                return "Magma Cube";
            case MINECART:
                return "Minecart";
            case MINECART_CHEST:
                return "Minecart Chest";
            case MINECART_COMMAND:
                return "Minecart CommandBlock";
            case MINECART_FURNACE:
                return "Minecart Furnace";
            case MINECART_HOPPER:
                return "Minecart Hopper";
            case MINECART_MOB_SPAWNER:
                return "Minecart Mob Spawner";
            case MINECART_TNT:
                return "Minecart";
            case MUSHROOM_COW:
                return "Mushroom Cow";
            case OCELOT:
                return "Ocelot";
            case PAINTING:
                return "Painting";
            case PIG:
                return "Pig";
            case PIG_ZOMBIE:
                return "Zombie Pigman";
            case PLAYER:
                return "Player";
            case PRIMED_TNT:
                return "TNT";
            case RABBIT:
                return "Rabbit";
            case SHEEP:
                return "Sheep";
            case SILVERFISH:
                return "Silverfish";
            case SKELETON:
                return "Skeleton";
            case SLIME:
                return "Slime";
            case SMALL_FIREBALL:
                return "Fireball";
            case SNOWBALL:
                return "Snowball";
            case SNOWMAN:
                return "Snow Golem";
            case SPIDER:
                return "Spider";
            case SPLASH_POTION:
                return "Potion";
            case SQUID:
                return "Squid";
            case THROWN_EXP_BOTTLE:
                return "Thrown Exp. Bottle";
            case VILLAGER:
                return "Villager";
            case WITCH:
                return "Witch";
            case WITHER:
                return "Wither";
            case WITHER_SKULL:
                return "Wither";
            case WOLF:
                return "Wolf";
            case ZOMBIE:
                return "Zombie";
            default:
                return "?";
        }
    }

    /**
     * Format numbers
     * @param value
     * @return
     */
    public String formatNumbers(long value) {
        if (value == Long.MIN_VALUE) return formatNumbers(Long.MIN_VALUE + 1);
        if (value < 0) return "-" + formatNumbers(-value);
        if (value < 1000) return Long.toString(value);

        Map.Entry<Long, String> e = suffixes.floorEntry(value);
        Long divideBy = e.getKey();
        String suffix = e.getValue();

        long truncated = value / (divideBy / 10);
        boolean hasDecimal = truncated < 100 && (truncated / 10d) != (truncated / 10);
        return hasDecimal ? (truncated / 10d) + suffix : (truncated / 10) + suffix;
    }

    /**
     * Convert numbers to Roman Numerals
     * @return
     */
    public String intToRomanNumeral(int number) {
        switch (number) {
            case 10:
                return "X";
            case 9:
                return "IX";
            case 8:
                return "VIII";
            case 7:
                return "VII";
            case 6:
                return "VI";
            case 5:
                return "V";
            case 4:
                return "IV";
            case 3:
                return "III";
            case 2:
                return "II";
            case 1:
                return "I";
        }

        return "";
    }
}
