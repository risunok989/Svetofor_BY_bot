package com.example.telegrambot;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import com.example.telegrambot.ENUM.EnumCheckCallbackQuery;
import com.example.telegrambot.ENUM.EnumCheckTypeMessage;
import com.example.telegrambot.keyboard.InlineKeyboardMarkupBuilder;

public class Command {


    public static EnumCheckTypeMessage checkTypeMessage(Update update) {
        EnumCheckTypeMessage enumCheckTypeMessage = null;
        if (update.getMessage().getText() != null) {
            if (update.getMessage().getText().contains("/")) {
                enumCheckTypeMessage = commandCheckType(update.getMessage().getText());

            } else {
                enumCheckTypeMessage = EnumCheckTypeMessage.TEXT;
            }

        } else if (update.getMessage().getPhoto() != null) {
            enumCheckTypeMessage = EnumCheckTypeMessage.IMAGE;
        } else if (update.getMessage().getVideo() != null) {
            enumCheckTypeMessage = EnumCheckTypeMessage.VIDEO;
        }
        return enumCheckTypeMessage;
    }

    public static EnumCheckCallbackQuery checkTypeCallbackQuery(Update update) {
        EnumCheckCallbackQuery enumCheckCallbackQuery = null;

        if (update.getCallbackQuery().getData().matches("kategories")) {
            enumCheckCallbackQuery = EnumCheckCallbackQuery.KATEGORIES;
        } else if (update.getCallbackQuery().getData().matches("dayproducts")) {
            enumCheckCallbackQuery = EnumCheckCallbackQuery.DAYPRODUCTS;
        } else if (update.getCallbackQuery().getData().matches("subscription")) {
            enumCheckCallbackQuery = EnumCheckCallbackQuery.SUBSCRIPTION;
        } else if (update.getCallbackQuery().getData().matches("all")) {
            enumCheckCallbackQuery = EnumCheckCallbackQuery.ALL;
        }
        return enumCheckCallbackQuery;
    }

    public static EnumCheckTypeMessage commandCheckType(String text) {
        EnumCheckTypeMessage enumCheckTypeMessage = null;
        if (text.matches("/start")) {
            enumCheckTypeMessage = EnumCheckTypeMessage.START;
        } else if (text.matches("/stop")) {
            enumCheckTypeMessage = EnumCheckTypeMessage.STOP;
        } else if (text.matches("/help")) {
            enumCheckTypeMessage = EnumCheckTypeMessage.HELP;
        } else if (text.matches("/check")) {
            enumCheckTypeMessage = EnumCheckTypeMessage.CHECK;
        }

        return enumCheckTypeMessage;
    }


    public static SendMessage startCommands(Long chatID) {
        return InlineKeyboardMarkupBuilder.create(chatID)
                .setText("Выберите из котегорий :")
                .row()
                .button("Все товары", "all")
                .button("Новые товары дня", "dayproducts" )
                .endRow()
                .row()
                .button("Категории товаров", "kategories")
                .endRow()
                .row()
                .button("Подписатся на обновления товаров", "subscription")
                .endRow()
                .build();

    }

    public static SendMessage stopCommands(Long chatID) {
        return InlineKeyboardMarkupBuilder.create(chatID)
                .setText("вопрос")
                .row()
                .button("ответ", "call")
                .endRow()
                .build();
    }



}
