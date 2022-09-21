package com.example.telegrambot.service;

import com.example.telegrambot.Bot;
import com.example.telegrambot.command.ParsedCommand;
import com.example.telegrambot.command.Parser;
import com.example.telegrambot.handler.*;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import com.example.telegrambot.command.Command;


// обработчик полученных сообщений
public class MessageReciever implements Runnable {
    // наследуемся от Runnable
    private static final Logger log = LogManager.getLogger(MessageReciever.class);
    private final int WAIT_FOR_NEW_MESSAGE_DELAY = 1000;

    private Bot bot;

    private Parser parser;
                     // передаём в конструктор обьект класса Bot, что б брать из него обьекты для анализа.
    public MessageReciever(Bot bot) {
        this.bot = bot;
        parser = new Parser(bot.getBotUsername());
    }

    // Реализуем поток приёма
    @Override
    public void run() {
        log.info("[STARTED THREAD] MsgReciever.  Bot class: " + bot);
        while (true) {   //в вечном цикле проверяем наличие новых сообщений
            for (Object object = bot.receiveQueue.poll(); object != null; object = bot.receiveQueue.poll()) {
                log.debug("New object for analyze in queue " + object.toString());

                try {  // отправляем на анализ object ( фото, видео, текст)
                    analyze(object);
                } catch (Exception e) {
                    log.error("Exception in update analyze", e);
                }
            }
            try {
                Thread.sleep(WAIT_FOR_NEW_MESSAGE_DELAY);  // задержка потока на 1с
            } catch (InterruptedException e) {
                log.error("Catch interrupt. Exit", e);
                return;
            }

        }
    }

    private void analyze(Object object) {
        // Методом instanceof проверяю принадлежность object к классу Update (был ли создан на основе
        // его класса (Update).
        if (object instanceof Update) {
            Update update = (Update) object;  // Object обратно в Update
            log.debug("Update recieved: " + update.toString());
            analyzeForCallBackOrText(update);
        } else log.warn("Cant method 'analyze'  of object: " + object.toString());


    }

    // Проверяем явлеется ли Update текстом, либо CallBack от пользователя.
    private void analyzeForCallBackOrText(Update update) {
        if (update.hasMessage()) {
            analyzeForUpdateType(update);
        } else if (update.hasCallbackQuery()) {
            String messageCallback = update.getCallbackQuery().getData();

            Long chatID = update.getCallbackQuery().getMessage().getChatId();


            ParsedCommand parsedCommandForCallback = new ParsedCommand(Command.CALLBACK, messageCallback);
            AbstractHandler handlerForCallback = getHandlerForCommand(parsedCommandForCallback.getCommand());

            parsedCommandForCallback = parser.getParsedCommand(messageCallback);
            String operationResult = handlerForCallback.operate(chatID.toString(),parsedCommandForCallback, update);

            try {
                SendMessage sendMessageOut = new SendMessage();
                sendMessageOut.setChatId(chatID.toString());
                sendMessageOut.setText(operationResult);
                bot.sendQueue.add(sendMessageOut);
            }catch (Exception e){
//                System.out.println(e.getMessage() + " for analyzeForCallBackOrText");
            }


        }
    }

         void analyzeForUpdateType (Update update){
            Message message = update.getMessage();
            Long chatId = message.getChatId();

            ParsedCommand parsedCommand = new ParsedCommand(Command.NONE, "");

            if (update.getMessage().hasText()) {

                parsedCommand = parser.getParsedCommand(message.getText());
            }

            AbstractHandler handlerForCommand = getHandlerForCommand(parsedCommand.getCommand());
            String operationResult = handlerForCommand.operate(chatId.toString(), parsedCommand, update);

            if (!"".equals(operationResult)) {
                SendMessage messageOut = new SendMessage();
                messageOut.setChatId(chatId.toString());
                messageOut.setText(operationResult);
                bot.sendQueue.add(messageOut);
            }
        }

        private AbstractHandler getHandlerForCommand (Command command){
            if (command == null) { ;
                log.warn("Null command accepted for 'getHandlerForCommand' method.");
                return new DefaultHandler(bot);
            }
            switch (command) {
                case CALLBACK:
                    log.info("Handler for command[" + command.toString() + "]. Return CallbackHandler");
                    CallbackHandler callbackHandler = new CallbackHandler(bot);
                    return callbackHandler;
                case START:
                    log.info("Handler for command[" + command.toString() + "]. Return SystemHandler");
                    SystemHandler systemHandlerStart = new SystemHandler(bot);
                    return systemHandlerStart;
                case HELP:
                    log.info("Handler for command[" + command.toString() + "]. Return SystemHandler");
                    SystemHandler systemHandlerHelp = new SystemHandler(bot);
                    return systemHandlerHelp;
                case STOP:
                    log.info("Handler for command[" + command.toString() + "]. Return SystemHandler");
                    SystemHandler systemHandlerStop = new SystemHandler(bot);
                    return systemHandlerStop;
//                case ID:
//                case STICKER:
//                    SystemHandler systemHandler = new SystemHandler(bot);
//                    log.info("Handler for command[" + command.toString() + "] is: " + systemHandler);
//                    return systemHandler;
            case NOTIFY:
                NotifyHandler notifyHandler = new NotifyHandler(bot);
                log.info("Handler for command[" + command.toString() + "] is: " + notifyHandler);
                return notifyHandler;
//            case TEXT_CONTAIN_EMOJI:
//                EmojiHandler emojiHandler = new EmojiHandler(bot);
//                log.info("Handler for command[" + command.toString() + "] is: " + emojiHandler);
//                return emojiHandler;
                default:
                    log.info("Handler for command[" + command.toString() + "] not Set. Return DefaultHandler");
                    return new DefaultHandler(bot);
            }
        }

    }


