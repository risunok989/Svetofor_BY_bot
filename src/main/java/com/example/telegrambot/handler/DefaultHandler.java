package com.example.telegrambot.handler;

import com.example.telegrambot.Bot;
import org.telegram.telegrambots.meta.api.objects.Update;
import com.example.telegrambot.command.ParsedCommand;

public class DefaultHandler extends AbstractHandler {
    public DefaultHandler(Bot bot) {
        super(bot);
    }

    @Override
    public String operate(String chatID, ParsedCommand parsedCommand, Update update) {

        return "";
    }
}
