package com.example.telegrambot.handler;

import com.example.telegrambot.Bot;
import com.example.telegrambot.command.ParsedCommand;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

public class UserTextHandler extends AbstractHandler {

    public UserTextHandler(Bot bot) {
        super(bot);
    }

    @Override
    public String operate(String chatID, ParsedCommand parsedCommand, Update update) {
        Bot.sendQueue.add(getCategory(chatID));
        return "";
    }
    public SendMessage getCategory(String chatID){
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatID);
        sendMessage.setText("Вы выбрали 'категории товаров..'");
        return sendMessage;
    }
}

