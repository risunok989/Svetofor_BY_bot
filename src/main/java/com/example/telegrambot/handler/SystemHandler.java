package com.example.telegrambot.handler;

import com.example.telegrambot.Bot;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import com.example.telegrambot.command.Command;
import com.example.telegrambot.command.ParsedCommand;
import com.example.telegrambot.keyboard.InlineKeyboardMarkupBuilder;


public class SystemHandler extends AbstractHandler {
    private final static Logger log = LogManager.getLogger(SystemHandler.class);

    private final String END_LINE = "\n";

    public SystemHandler(Bot bot) {
        super(bot);
    }

    @Override
    public String operate(String chatID, ParsedCommand parsedCommand, Update update) {
        Command command = parsedCommand.getCommand();
        switch (command) {
            case START:
                Bot.sendQueue.add(getMessageStart(chatID));
                break;
            case HELP:
                Bot.sendQueue.add(getMessageHelp(chatID));
                break;
            case STOP:
                Bot.sendQueue.add(getMessageStop(chatID));
                break;
        }

        return "";
    }

    private SendMessage getMessageStart(String chatID) {
//        log.info("Run SystemHandler for /start");

        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatID);
        sendMessage.enableMarkdown(true);
        StringBuilder text = new StringBuilder();
        text.append("Привет. Я  *").append(bot.getBotUsername()).append("*").append(END_LINE);
        text.append("Я создан для помощи в поиске товаров, а так же" +
                " извещения об новых поступлениях товара в сети магазинов" +
                " *\"Светофор\"* Республики Беларусь.").append(END_LINE);
        text.append("Узнать что я могу - Вы можете нажав на [/help](/help)");
        sendMessage.setText(text.toString());
        return sendMessage;
    }

    private SendMessage getMessageHelp(String chatID) {
//        log.info("Run SystemHandler for /help");

        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatID);
        sendMessage.enableMarkdown(true);
        return InlineKeyboardMarkupBuilder.create(Long.valueOf(chatID))

                .setText("Выберите интересующее Вас :")
                .row()
                .button("Все товары", "ALL")
                .button("Новые товары", "NEW")
                .endRow()
                .row()
                .button("Каталог товаров", "CATALOG")
                .endRow()
                .row()
                .button("Подписаться на обновление товаров", "NOTIFY")
                .endRow().build();

    }
    private SendMessage getMessageStop(String chatID){
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatID);
        sendMessage.setText("Soon");
        return sendMessage;
    }
}
