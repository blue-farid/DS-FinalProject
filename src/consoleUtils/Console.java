package consoleUtils;

import java.io.IOException;

public class Console {
    public static void clearScreen() {
        try {
            if (System.getProperty("os.name").contains("Windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            }
            else {
                System.out.print("\033\143");
            }
        } catch (IOException | InterruptedException ex) {}
    }

    public static void setColor(ConsoleColor color) {
        System.out.print(color.getValue());
    }

    public static void initializeConsole() {
        try {
            int display = new ProcessBuilder("cmd", "/c", "color", "00").inheritIO().start().waitFor();
            System.out.print(display);
            clearScreen();
        } catch (InterruptedException | IOException e) {
            e.printStackTrace();
        }
    }

}
