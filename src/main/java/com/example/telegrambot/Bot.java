package com.example.telegrambot;

import com.example.telegrambot.token.Token;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;



public class Bot extends TelegramLongPollingBot {

    //добавил 2 очереди
    public static final Queue<Object> sendQueue = new ConcurrentLinkedQueue<>();
    public static final Queue<Object> receiveQueue = new ConcurrentLinkedQueue<>();


    private static final Logger log = LogManager.getLogger(Bot.class);

    public String getBotUsername() {
        return Token.getTokenBotUsername();
    }

    public String getBotToken() {
        return Token.getTokenBotToken();
    }

    // Передаю обновления в очередь обработки входящих
    @Override
    public void onUpdateReceived(Update update) {
        log.info("new update");
        receiveQueue.add(update);

    }

}


