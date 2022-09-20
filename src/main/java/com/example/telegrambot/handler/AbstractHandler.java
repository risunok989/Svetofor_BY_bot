package com.example.telegrambot.handler;

import com.example.telegrambot.Bot;
import com.example.telegrambot.command.ParsedCommand;
import org.telegram.telegrambots.meta.api.objects.Update;

public abstract class AbstractHandler {
    Bot bot;

    public AbstractHandler(Bot bot) {
        this.bot = bot;
    }

    public abstract String operate(String chatID, ParsedCommand parsedCommand, Update update);

}
