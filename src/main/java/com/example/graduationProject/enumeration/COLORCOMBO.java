package com.example.graduationProject.enumeration;

public enum COLORCOMBO {
    RED("red"),
    YELLOW("yellow"),
    PINK("pink"),
    GREEN("green"),
    SKY("sky"),
    BROWN("brown"),
    VIOLET("violet"),
    DARK_GREEN("darkGreen"),
    PURPLE("purple"),
    BLUE("blue"),
    CREAM("cream");

    private final String command;

    COLORCOMBO(String command) {
        this.command = command;
    }

    public String getCommand() {
        return command;
    }

    // Метод для получения соответствующего enum по строке команды
    public static COLORCOMBO fromCommand(String command) {
        for (COLORCOMBO color : values()) {
            if (color.getCommand().equals(command)) {
                return color;
            }
        }
        return null; // Или можно выбросить исключение, если команда не найдена
    }
}
