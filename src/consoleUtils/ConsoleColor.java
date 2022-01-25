package consoleUtils;

public enum ConsoleColor {
    RESET("\u001B[0m"),

    /**
     * text colors
     */
    TEXT_BLUE_BRIGHT("\033[0;94m"),
    TEXT_RED_BRIGHT("\033[0;91m"),
    TEXT_CYAN_BRIGHT("\033[0;96m"),

    /**
     * background colors
     */
    BACKGROUND_BLACK("\u001b[40m"),
    BACKGROUND_RED("\u001b[41m"),
    BACKGROUND_GREEN("\u001b[42m"),
    BACKGROUND_YELLOW("\u001b[43m"),
    BACKGROUND_BLUE("\u001b[44m"),
    BACKGROUND_MAGENTA("\u001b[45m"),
    BACKGROUND_CYAN("\u001b[46m"),
    BACKGROUND_WHITE("\u001b[47m");
    ConsoleColor(String value) {}
}
