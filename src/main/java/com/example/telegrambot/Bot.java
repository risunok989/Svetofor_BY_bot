package com.example.telegrambot;

import com.example.telegrambot.token.Token;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;



public class Bot extends TelegramLongPollingBot {

    public static final Queue<Object> sendQueue = new ConcurrentLinkedQueue<>();
    public static final Queue<Object> receiveQueue = new ConcurrentLinkedQueue<>();


    private static final Logger log = LogManager.getLogger(Bot.class);

    public String getBotUsername() {
        return Token.getTokenBotUsername();
//        return "Svetofor_BY_bot";
    }

    public String getBotToken() {
        return Token.getTokenBotToken();
//        return "5384659247:AAG9CS7x4Dk_HRt6kDkzeuu9PWFRJAbhMDY";
    }

    @Override
    public void onUpdateReceived(Update update) {
        log.info("new update");
        receiveQueue.add(update);

    }

}


