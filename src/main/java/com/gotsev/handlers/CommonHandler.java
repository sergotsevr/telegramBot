//package com.gotsev.models.handlers;
//
//import com.gotsev.BotConfig;
//import com.gotsev.commands.StartCommand;
//import com.gotsev.commands.StopCommand;
//import org.telegram.telegrambots.bots.DefaultBotOptions;
//import org.telegram.telegrambots.extensions.bots.commandbot.TelegramLongPollingCommandBot;
//import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
//import org.telegram.telegrambots.meta.api.objects.Message;
//import org.telegram.telegrambots.meta.api.objects.Update;
//import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
//import org.telegram.telegrambots.meta.logging.BotLogger;
//
//public class CommonHandler extends TelegramLongPollingCommandBot {
//
//    public CommonHandler(String botUsername) {
//        super(botUsername);
//
//        register(new StartCommand());
//        register(new StopCommand());
//    }
//
//    @Override
//    public void processNonCommandUpdate(Update update) {
//        if (update.hasMessage()) {
//            Message message = update.getMessage();
//
//
//            if (message.hasText()) {
//                SendMessage echoMessage = new SendMessage();
//                echoMessage.setChatId(message.getChatId());
//                echoMessage.setText("Hey heres your message:\n" + message.getText());
//
//                try {
//                    execute(echoMessage);
//                } catch (TelegramApiException e) {
//                    BotLogger.error("Error", e);
//                }
//            }
//        }
//
//    }
//
//    @Override
//    public String getBotToken() {
//        return BotConfig.COMMANDS_TOKEN;
//    }
//}
