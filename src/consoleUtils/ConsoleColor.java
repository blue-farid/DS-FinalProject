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
    BACKGROUND_BLACK("\033[40m"),
    BACKGROUND_RED("\033[41m"),
    BACKGROUND_GREEN("\033[42m"),
    BACKGROUND_YELLOW("\033[43m"),
    BACKGROUND_BLUE("\033[44m"),
    BACKGROUND_PURPLE("\033[45m"),
    BACKGROUND_CYAN("\033[46m"),
    BACKGROUND_WHITE("\033[47m");

    private final String value;
    ConsoleColor(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
