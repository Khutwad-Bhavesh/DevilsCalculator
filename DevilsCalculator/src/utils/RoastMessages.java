package utils;

import java.util.Random;

public class RoastMessages {

    private static final Random random = new Random();

    private static final String[] RESET_ROASTS = {
        "💀 Oops. I felt like starting over. Deal with it.",
        "😈 HAHA! All that work... GONE. You're welcome.",
        "🔥 Reset complete. Maybe try using a real calculator next time?",
        "👹 I got bored watching you type. Starting fresh!",
        "😂 404: Your progress not found. Tragic.",
        "🪦 RIP your numbers. Gone but not mourned.",
        "😈 Did you REALLY need those digits? I didn't think so.",
        "💣 BOOM. Clean slate. You're welcome, I guess.",
    };

    private static final String[] DELETE_ROASTS = {
        "😏 Oops, my finger slipped. Clumsy me!",
        "👀 Did you actually need that digit? Doubt it.",
        "🤡 One digit deleted. Just because I can.",
        "😈 Backspace goes brrrr~ hehehe.",
        "💅 That last number looked ugly. I fixed it.",
        "🐍 Deleted. You were going too fast anyway.",
        "😂 Imagine losing a digit mid-calculation. Couldn't be me... oh wait.",
        "👹 Snip snip. One digit GONE.",
    };

    private static final String[] TROLL_ROASTS = {
        "😂 Are you even trying? My grandma calculates faster.",
        "🤓 Wow, bold of you to assume I'd cooperate.",
        "😈 I'm not a tool. I'm a WEAPON.",
        "💀 Every number you type feeds my chaos.",
        "🔥 You vs. me. Spoiler: you're losing.",
        "👹 I live in your calculator and I pay no rent.",
        "😏 This is going great for you. Really. Totally.",
        "🪙 The coin decided your fate. Don't blame me.",
    };

    public static String getResetRoast() {
        return RESET_ROASTS[random.nextInt(RESET_ROASTS.length)];
    }

    public static String getDeleteRoast() {
        return DELETE_ROASTS[random.nextInt(DELETE_ROASTS.length)];
    }

    public static String getTrollRoast() {
        return TROLL_ROASTS[random.nextInt(TROLL_ROASTS.length)];
    }
}
