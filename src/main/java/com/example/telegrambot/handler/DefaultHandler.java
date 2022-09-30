package com.example.telegrambot.handler;

import com.example.telegrambot.Bot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import com.example.telegrambot.command.ParsedCommand;

public class DefaultHandler extends AbstractHandler {
    public DefaultHandler(Bot bot) {
        super(bot);
    }
    private String chatID;

    @Override
    public String operate(String chatID, ParsedCommand parsedCommand, Update update) {
//
//        Bot.sendQueue.add(defaultHandlerMessage(chatID));
        return "";
    }
//    private SendMessage defaultHandlerMessage (String chatID){
//        this.chatID = chatID;
//        SendMessage sendMessage = new SendMessage();
//        sendMessage.enableMarkdown(true);
//        sendMessage.setChatId(this.chatID);
//        sendMessage.setText("я Вас не понимать(( Выберите что хотите.");
//        return sendMessage ;
//    }
}
