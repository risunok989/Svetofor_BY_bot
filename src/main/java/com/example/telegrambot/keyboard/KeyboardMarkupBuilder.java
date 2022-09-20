package com.example.telegrambot.keyboard;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

public interface KeyboardMarkupBuilder {

    //методы для имплеминтирования, а так же ШАГИ для СОЗДАНИЯ клавиатуры.
    void setChatID(Long chatID);    // установка chatID

    KeyboardMarkupBuilder setText(String text);    // что писать

    KeyboardMarkupBuilder row();   // создание ArrayList

    KeyboardMarkupBuilder endRow(); // добавление в keyboard row ( ArrayList)

    SendMessage build();

}
