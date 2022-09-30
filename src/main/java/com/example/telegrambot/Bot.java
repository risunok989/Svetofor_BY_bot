package com.example.telegrambot;

import com.example.telegrambot.token.Token;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;


public class Bot extends TelegramLongPollingBot {
    private static final Logger log = LogManager.getLogger(Bot.class);

    //добавил 2 очереди, в которых будут хранится Object для "отправки" либо "получения" сообщений.
    public static final Queue<Object> sendQueue = new ConcurrentLinkedQueue<>();
    public static final Queue<Object> receiveQueue = new ConcurrentLinkedQueue<>();




    public String getBotUsername() {
        return Token.getTokenBotUsername();
    }

    public String getBotToken() {
        return Token.getTokenBotToken();
    }

    // Передаю обновления в очередь обработки входящих
    @Override
    public void onUpdateReceived(Update update) {

//        log.info("for update.getMessage().getChatId() : " + update.getMessage().getChatId());
//        log.info("for update.getMessage().getChat().getLastName() : " + update.getMessage().getChat().getLastName());
//        log.info("for update.getMessage().getChat().getFirstName() : " + update.getMessage().getChat().getFirstName());
//        log.info("for update.getMessage().getChat().getUserName() : " + update.getMessage().getChat().getUserName());
//        log.info("for update.getMessage().getChat().getType() : " + update.getMessage().getChat().getType());
        receiveQueue.add(update);


    }

}


