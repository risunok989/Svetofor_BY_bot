package com.example.telegrambot.command;

import com.vdurmont.emoji.EmojiParser;
import org.glassfish.grizzly.utils.Pair;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class Parser {
    private static final Logger log = LogManager.getLogger(Parser.class);
    private final String PREFIX_FOR_COMMAND = "/";
    private final String DELIMITER_COMMAND_BOTNAME = "@";
    private String botName;

    public Parser(String botName) {
        this.botName = botName;
    }



    // Метод для обработки текста и получения экземляра ParsedCommand с командой пользователя.
    public ParsedCommand getParsedCommand(String text) {
        String trimText = "";
        // методом trim() убираем пробелы в тексте (в начале и конце)
        if (text != null) trimText = text.trim();
        // Создаю "болванку" ParsedCommand, с текстом пользователя.
        ParsedCommand result = new ParsedCommand(Command.NONE, trimText);

        switch (text) {
            case "ALL":
                result = new ParsedCommand(Command.ALL, trimText);
                return result;
            case "CATALOG":
                result = new ParsedCommand(Command.CATALOG, trimText);
                return result;
            case "NEW":
                result = new ParsedCommand(Command.NEW, trimText);
                return result;
            case "NOTIFY":
                result = new ParsedCommand(Command.NOTIFY, trimText);
                return result;
                // КОМАНДЫ ВВЕДЁННЫЕ С КЛАВИАТУРЫ
            case "категории товаров." :
                result = new ParsedCommand(Command.CATEGORY, Command.CATEGORY.toString());
                return result;


        }

        if ("".equals(trimText)) return result;

        Pair<String, String> commandAndText = getDelimitedCommandFromText(trimText);
        if (isCommand(commandAndText.getFirst())) {
            if (isCommandForMe(commandAndText.getFirst())) {
                String commandForParse = cutCommandFromFullText(commandAndText.getFirst());
                Command commandFromText = getCommandFromText(commandForParse);
                result.setText(commandAndText.getFirst());
                result.setCommand(commandFromText);
            } else {
                result.setCommand(Command.NOTFORME);
                result.setText(commandAndText.getFirst());
            }

        }
        if (result.getCommand() == Command.NONE) {
            List<String> emojiContainsInText = EmojiParser.extractEmojis(result.getText());
            if (emojiContainsInText.size() > 0) result.setCommand(Command.TEXT_CONTAIN_EMOJI);
        }
        return result;
    }

    private String cutCommandFromFullText(String text) {
        return text.contains(DELIMITER_COMMAND_BOTNAME) ?
                text.substring(1, text.indexOf(DELIMITER_COMMAND_BOTNAME)) :
                text.substring(1);
    }

    private Command getCommandFromText(String text) {
        String upperCaseText = text.toUpperCase().trim();
        Command command = Command.NONE;
        try {
            command = Command.valueOf(upperCaseText);
        } catch (IllegalArgumentException e) {
            log.debug("Can't parse command: " + text);
        }
        return command;
    }

    private Pair<String, String> getDelimitedCommandFromText(String trimText) {
        Pair<String, String> commandText;

        if (trimText.contains(" ")) {
            int indexOfSpace = trimText.indexOf(" ");
            commandText = new Pair<>(trimText.substring(0, indexOfSpace), trimText.substring(indexOfSpace + 1));
        } else commandText = new Pair<>(trimText, "");
        return commandText;
    }

    private boolean isCommandForMe(String command) {
        if (command.contains(DELIMITER_COMMAND_BOTNAME)) {
            String botNameForEqual = command.substring(command.indexOf(DELIMITER_COMMAND_BOTNAME) + 1);
            return botName.equals(botNameForEqual);
        }
        return true;
    }

    private boolean isCommand(String text) {
        return text.startsWith(PREFIX_FOR_COMMAND);
    }

}
