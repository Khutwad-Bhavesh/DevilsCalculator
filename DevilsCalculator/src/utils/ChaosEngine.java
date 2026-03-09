package utils;

import java.util.Random;

public class ChaosEngine {

    public enum ChaosType {
        DELETE_DIGIT,
        FULL_RESET,
        TROLL_MESSAGE
    }

    private static final Random random = new Random();

    public static ChaosType rollChaos() {
        int roll = random.nextInt(3);
        return ChaosType.values()[roll];
    }

    public static boolean flipCoin() {
        return random.nextBoolean(); // true = HEADS (safe), false = TAILS (chaos)
    }
}
