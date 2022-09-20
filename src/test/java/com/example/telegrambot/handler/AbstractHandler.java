package com.example.telegrambot.handler;

import org.telegram.telegrambots.meta.api.objects.Update;
import com.example.telegrambot.Bot;
import com.example.telegrambot.command.ParsedCommand;

public abstract class AbstractHandler {
    Bot bot;

    AbstractHandler(Bot bot) {
        this.bot = bot;
    }

    public abstract String operate(String chatId, ParsedCommand parsedCommand, Update update);
}
