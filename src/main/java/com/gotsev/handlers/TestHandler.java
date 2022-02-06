package com.gotsev.handlers;

import com.gotsev.models.interfaces.Question;
import com.gotsev.models.interfaces.Test;
import com.gotsev.services.TestService;
import com.gotsev.services.TestServiceHardCodedForTest;
import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.List;

import static com.google.common.base.Strings.isNullOrEmpty;
import static com.gotsev.BotConfig.TEST_TOKEN;
import static com.gotsev.BotConfig.TEST_USER;
import static com.gotsev.utils.MenuConstants.*;
import static com.gotsev.utils.Messages.*;

@Slf4j
public class TestHandler extends TelegramLongPollingBot {

    public TestHandler() {

    }


    @Override
    public void onUpdateReceived(Update update) {
        log.debug("update");

        if (update.hasMessage()) {
            Message message = update.getMessage();

            if (message.hasText()) {

                if (message.getText().equals("/start")) {
                    prepareAndSendMenuMessage(message.getChatId(), GREETING + "\n" + CHOOSE_SECTION);
                } else {
                    log.debug("message command to start - " + message.getText());
                }
                log.info("get update");
            } else {
                log.debug("message has no text");
            }

        }
        if (update.hasCallbackQuery()) {
            CallbackQuery callbackQuery = update.getCallbackQuery();
            String[] data = callbackQuery.getData().split(":");
            if (data.length < 3) {
                throw new RuntimeException("Проверить заполнение callbackData");
            }
            if (FIRST_QUESTION.toString().equals(data[0])) {
                sendFirstQuestion(callbackQuery, data);
            }
            if (TEST.toString().equals(data[0])) {
                checkAndSendNextQuestion(callbackQuery, data);
            }
            if (MENU.toString().equals(data[0])) {
                prepareAndSendMenuMessage(callbackQuery.getMessage().getChatId(), WELCOME_BACK + "\n" + CHOOSE_SECTION);
            }
        }
    }
    private void sendFirstQuestion(CallbackQuery callbackQuery, String[] data){
        TestService service = new TestServiceHardCodedForTest();
        Test test = service.getTestsFromSection(data[1]).get(0);
        Integer questionNumber = Integer.parseInt(data[2]);
        Long chatId = callbackQuery.getMessage().getChatId();

        if (test.getQuestions().size() > questionNumber) {
            sendQuestion(0, test, chatId);
        }
    }
    private void checkAndSendNextQuestion(CallbackQuery callbackQuery, String[] data) {


        TestService service = new TestServiceHardCodedForTest();
        Test test = service.getTestsFromSection(data[1]).get(0);
        Integer questionNumber = Integer.parseInt(data[3]);
        Long chatId = callbackQuery.getMessage().getChatId();
        Integer questionsSize =test.getQuestions().size();
        questionsSize--;
        if (questionsSize.equals(questionNumber)) {
            Question question = test.getQuestions().get(questionNumber);
            if (question.checkAnswer(data[2])) {
                sendTextMessage(chatId, "Success!");
                sendMenuButton(chatId);
            }
            else {
                sendTextMessage(chatId, "Wrong answer!");
                sendQuestion(questionNumber, test, chatId);
            }
        }
        if (questionsSize > questionNumber) {
            Question question = test.getQuestions().get(questionNumber);

            if (!isNullOrEmpty(callbackQuery.getMessage().getText()) && question.checkAnswer(data[2])) {
                sendTextMessage(chatId, "Success!");
                sendQuestion(++questionNumber, test, chatId);
            } else if (isNullOrEmpty(callbackQuery.getMessage().getText())){
                sendQuestion(questionNumber, test, chatId);
            }
            else {
                sendTextMessage(chatId, "Wrong answer!");
                sendQuestion(questionNumber, test, chatId);
            }
        }

    }

    private void sendQuestion(Integer questionNumber, Test test, Long chatId) {

        Question question = test.getQuestions().get(questionNumber);
        SendMessage questionMessage = new SendMessage();
        questionMessage.setChatId(chatId.toString());
        questionMessage.setReplyMarkup(buttonOptions(question, test));
        questionMessage.setText(question.getStatement());
        questionMessage.enableMarkdown(true);
        try {
            execute(questionMessage);
        } catch (TelegramApiException e) {
            log.error("Error" + e);
        }
    }

    private void sendTextMessage(Long chatId, String text) {
        SendMessage questionMessage = new SendMessage();
        questionMessage.setChatId(chatId.toString());
        questionMessage.setText(text);
        try {
            execute(questionMessage);
        } catch (TelegramApiException e) {
            log.error(e.toString());

        }
    }

    private void sendMenuButton(Long chatId) {
        SendMessage menuMessage = new SendMessage();
        menuMessage.setChatId(chatId.toString());
        menuMessage.setText("Go back to main menu");
        menuMessage.setReplyMarkup(prepareMainMenuButton());
        menuMessage.enableMarkdown(true);
        try {
            execute(menuMessage);
        } catch (TelegramApiException e) {
            log.error(e.toString());

        }
    }

    private void prepareAndSendMenuMessage(Long chatId, String text){
        SendMessage greetingMessage = new SendMessage();
        greetingMessage.setChatId(chatId.toString());
        greetingMessage.setReplyMarkup(mainOptions());
        greetingMessage.setText(text);
        greetingMessage.enableMarkdown(true);
        try {
            execute(greetingMessage);
        } catch (TelegramApiException e) {
            log.error("Error" + e);
        }
    }

    @Override
    public String getBotUsername() {
        return TEST_USER;
    }

    @Override
    public String getBotToken() {
        return TEST_TOKEN;
    }

    private InlineKeyboardMarkup mainOptions() {
        TestService testService = new TestServiceHardCodedForTest();
        InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();

        for (String section : testService.getSections()) {
            List<InlineKeyboardButton> rowInline = new ArrayList<>();
            InlineKeyboardButton button = new InlineKeyboardButton();
            button.setText(section);
            button.setCallbackData(FIRST_QUESTION + ":" + section + ":" + 0);
            rowInline.add(button);
            rowsInline.add(rowInline);
        }
        markupInline.setKeyboard(rowsInline);
        return markupInline;
    }

    private InlineKeyboardMarkup prepareMainMenuButton() {
        InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
        List<InlineKeyboardButton> rowInline = new ArrayList<>();
        InlineKeyboardButton button = new InlineKeyboardButton();
        button.setText("MAIN MENU");
        button.setCallbackData(MENU.toString() + ":testFinished:1" );
        rowInline.add(button);
        rowsInline.add(rowInline);
        markupInline.setKeyboard(rowsInline);
        return markupInline;
    }

    private InlineKeyboardMarkup buttonOptions(Question question, Test test) {

        InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
        List<InlineKeyboardButton> rowInline = new ArrayList<>();
        for (String option : question.getOptions()) {
            InlineKeyboardButton button = new InlineKeyboardButton();
            button.setText(option);
            button.setCallbackData(TEST + ":" + test.getSection() + ":" + option + ":" + question.getNumber());
            log.debug(question.getNumber().toString());
            rowInline.add(button);
        }
        rowsInline.add(rowInline);
        markupInline.setKeyboard(rowsInline);

        return markupInline;
    }


}
