package com.example.telegrambot.service;

import com.example.telegrambot.Bot;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.methods.send.SendSticker;
import org.telegram.telegrambots.meta.api.objects.Message;

// обработчик очереди сообщений, которые нужно отправить пользователю.
public class MessageSender implements Runnable {
    enum MessageType {
        EXECUTE, STICKER, NOT_DETECTED, PHOTO
    }

    private Bot bot;

    // передаём в конструктор обьект класса Bot, что б брать из него обьекты для анализа.
    public MessageSender(Bot bot) {
        this.bot = bot;
    }

    private static final Logger log = LogManager.getLogger(MessageSender.class);
    private final int SENDER_SLEEP_TIME = 1000;


    @Override
    public void run() {
        log.info("[STARTED THREAD] MsgSender.  Bot class: " + bot);
        try {
            while (true) {
                // Проверяю есть ли в очереди на отправку, если да, передаю send().
                for (Object object = bot.sendQueue.poll(); object != null; object = bot.sendQueue.poll()) {
                    log.debug("Get new msg to send " + object);
                    send(object);
                }
                try {
                    Thread.sleep(SENDER_SLEEP_TIME); // Усыпляю поток 1с
                } catch (InterruptedException e) {
                    log.error("Take interrupt while operate msg list", e);
                }
            }
        } catch (Exception e) {
            log.error(e);
        }
    }

    private void send(Object object) {
        //Проверяю тип сообщения в методе checkMessageType и возрвращаю ENUM.
        try {
            MessageType messageType = checkMessageType(object);

            switch (messageType) {
                case EXECUTE:
                    BotApiMethod<Message> message = (BotApiMethod<Message>) object;
                    log.debug("send method use Execute for " + object);
                    bot.execute(message);
                    break;
                case STICKER:
                    SendSticker sendSticker = (SendSticker) object;
                    log.debug("send method use SendSticker for " + object);
                    bot.execute(sendSticker);
                    break;
                case PHOTO:
                    log.debug("send method use SendPhoto for " + object);
                default:
                    log.warn("Cant detect type of object for send method. " + object);
            }

        } catch (Exception e) {
            log.error(e.getMessage(), e);

        }
    }

    private MessageType checkMessageType(Object object) {
        if (object instanceof SendSticker) return MessageType.STICKER;
        if (object instanceof BotApiMethod) return MessageType.EXECUTE;
        if (object instanceof SendPhoto) return MessageType.PHOTO;
        return MessageType.NOT_DETECTED;
    }


}
