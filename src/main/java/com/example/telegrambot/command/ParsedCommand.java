package com.example.telegrambot.command;

// Класс, помогающий установить флаг на команде, запрошенной пользователем, а так же текст его.
public class ParsedCommand {
    Command command = Command.NONE;
    String text = "";

    public ParsedCommand(Command command, String text) {
        this.command = command;
        this.text = text;
    }

    public Command getCommand() {
        return command;
    }

    public void setCommand(Command command) {
        this.command = command;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
