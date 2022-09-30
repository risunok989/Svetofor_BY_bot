package com.example.telegrambot.handler;

import com.example.telegrambot.Bot;
import com.example.telegrambot.command.Command;
import com.example.telegrambot.command.ParsedCommand;
import com.example.telegrambot.service.MessageReciever;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

public class CallbackHandler extends AbstractHandler {
    private static final Logger log = LogManager.getLogger(CallbackHandler.class);

    public CallbackHandler(Bot bot) {
        super(bot);
    }


    private String chatIDCallback;

    @Override
    public String operate(String chatID, ParsedCommand parsedCommand, Update update) {
        this.chatIDCallback = chatID;
        Command command = parsedCommand.getCommand();
        switch (command){
            case ALL:
                Bot.sendQueue.add(getCallbackMessageALL(chatIDCallback));
                break;
            case NEW:
                Bot.sendQueue.add(getCallbackMessageNEW(chatIDCallback));
                break;
            case NOTIFY:
                Bot.sendQueue.add(getCallbackMessageNOTIFY(chatIDCallback));
                break;
            case CATALOG:
                Bot.sendQueue.add(getCallbackMessageCATALOG(chatIDCallback));
                break;
            case CATEGORY:
                Bot.sendQueue.add(getCallbackMessageCATEGORY(chatIDCallback));
                break;

        }
        return null;
    }


    private SendMessage getCallbackMessageALL(String chatIDCallback){

        log.info("Return callback ALL");
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);
        sendMessage.setText("Callback : ALL ");
        sendMessage.setChatId(chatIDCallback);
        return sendMessage;
    }
    private SendMessage getCallbackMessageNEW(String chatIDCallback){
        log.info("Return callback NEW");
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);
        sendMessage.setText("Callback : NEW ");

        sendMessage.setChatId(chatIDCallback);
        return sendMessage;
    }
    private SendMessage getCallbackMessageNOTIFY(String chatIDCallback){
        log.info("Return callback NOTIFY");
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);
        sendMessage.setText("Callback : NOTIFY ");
        sendMessage.setChatId(chatIDCallback);
        return sendMessage;
    }
    private SendMessage getCallbackMessageCATALOG(String chatIDCallback){
        log.info("Return callback CATALOG");
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);
        sendMessage.setText("Callback : CATALOG ");
        sendMessage.setChatId(chatIDCallback);
        return sendMessage;
    }
    private SendMessage getCallbackMessageCATEGORY(String chatIDCallback){
        log.info("Return callback CATALOG");
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);
        sendMessage.setText("Вы выбрали : CATEGORY ");
        sendMessage.setChatId(chatIDCallback);
        return sendMessage;
    }
}
