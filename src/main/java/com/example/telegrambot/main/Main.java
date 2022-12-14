package com.example.telegrambot.main;

import com.example.telegrambot.Bot;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.parser.ParseException;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendSticker;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import com.example.telegrambot.service.MessageReciever;
import com.example.telegrambot.service.MessageSender;

import java.io.IOException;


public class Main {
    private static final Logger log = LogManager.getLogger(Main.class);
    static final int RECONNECT_PAUSE = 10000;
    private static final String PATH_TO_PROPERTIES = "src/main/resources/config.properties";

    private static final int PRIORITY_FOR_SENDER = 1;
    private static final int PRIORITY_FOR_RECEIVER = 3;
    private static final String BOT_ADMIN = "249438024";

    public static void main(String[] args) throws TelegramApiException, IOException, ParseException, InterruptedException {

        Bot bot = new Bot();

        try {
            TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
            telegramBotsApi.registerBot(new Bot());
            log.debug("TelegramAPI запущены. Бот работает.");

        } catch (TelegramApiException e) {
            log.fatal("Не может запустится.");
            e.printStackTrace();
        }

        // Запускаю 2 потока, указав, что они "демоны".
        MessageReciever messageReciever = new MessageReciever(bot);
        MessageSender messageSender = new MessageSender(bot);

        Thread receiver = new Thread(messageReciever);
        receiver.setDaemon(true);
        receiver.setName("MsgReciever");
        receiver.setPriority(PRIORITY_FOR_RECEIVER);
        receiver.start();

        Thread sender = new Thread(messageSender);
        sender.setDaemon(true);
        sender.setName("MsgSender");
        sender.setPriority(PRIORITY_FOR_SENDER);
        sender.start();

        sendStartReport(bot);
//        sendStartReportSticker(bot);
    }

    private static void sendStartReport(Bot bot) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(BOT_ADMIN);
        sendMessage.setText("Запустился");

        bot.sendQueue.add(sendMessage);

    }private static void sendStartReportSticker(Bot bot) {
        SendSticker sendSticker = new SendSticker();
        sendSticker.setChatId(BOT_ADMIN);
        InputFile inputFile = new InputFile("CAACAgIAAxkBAAEF569jLL92Q9-VQU4fw01KYpUyT3se2AACfxAAAlYRUEtZGn4ltGEdSykE");
        sendSticker.setSticker(inputFile);


        bot.sendQueue.add(sendSticker);

    }

    }

