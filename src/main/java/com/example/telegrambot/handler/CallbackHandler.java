package com.example.telegrambot.handler;

import com.example.telegrambot.Bot;
import com.example.telegrambot.command.Command;
import com.example.telegrambot.command.ParsedCommand;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

public class CallbackHandler extends AbstractHandler {
    public CallbackHandler(Bot bot) {
        super(bot);
    }



//    @Override
//    public String operate() {
//        return null;
//    }

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

        }
        return null;
    }


    private SendMessage getCallbackMessageALL(String chatIDCallback){

        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);
        sendMessage.setText("Callback : ALL ");
        sendMessage.setChatId(chatIDCallback);
        return sendMessage;
    }
    private SendMessage getCallbackMessageNEW(String chatIDCallback){
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);
        sendMessage.setText("Callback : NEW ");

        sendMessage.setChatId(chatIDCallback);
        return sendMessage;
    }
    private SendMessage getCallbackMessageNOTIFY(String chatIDCallback){
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);
        sendMessage.setText("Callback : NOTIFY ");
        sendMessage.setChatId(chatIDCallback);
        return sendMessage;
    }
    private SendMessage getCallbackMessageCATALOG(String chatIDCallback){
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);
        sendMessage.setText("Callback : CATALOG ");
        sendMessage.setChatId(chatIDCallback);
        return sendMessage;
    }
}
