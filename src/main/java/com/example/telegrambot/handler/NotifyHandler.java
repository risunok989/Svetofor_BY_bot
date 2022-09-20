package com.example.telegrambot.handler;

import com.example.telegrambot.Bot;
import com.example.telegrambot.command.ParsedCommand;
import org.telegram.telegrambots.meta.api.objects.Update;

public class NotifyHandler extends AbstractHandler {
    public NotifyHandler(Bot bot) {
        super(bot);
    }

    @Override
    public String operate(String chatID, ParsedCommand parsedCommand, Update update) {

        return "NotifyHandler soon";
    }
}
