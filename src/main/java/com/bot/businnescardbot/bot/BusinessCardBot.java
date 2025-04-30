package com.bot.businnescardbot.bot;
import com.bot.businnescardbot.services.ManagerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.extensions.bots.commandbot.TelegramLongPollingCommandBot;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.IBotCommand;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
@Slf4j
public class BusinessCardBot extends TelegramLongPollingCommandBot {
    private final String botUsername;
    private final Map<Long, String> feedbackStateMap = new ConcurrentHashMap<>();
    private final ManagerService managerService;

    public BusinessCardBot(
            @Value("${telegram.bot.token}") String botToken,
            @Value("${telegram.bot.username}") String botUsername,
            List<IBotCommand> commandList, ManagerService managerService) {
        super(botToken);
        this.botUsername = botUsername;
        this.managerService = managerService;
        commandList.forEach(this::register);
    }

    @Override
    public String getBotUsername() {
        return botUsername;
    }

    @Override
    public void processNonCommandUpdate(Update update) {
        if (update.hasCallbackQuery()) {
            handleCallbackQuery(update.getCallbackQuery());
        } else if (update.hasMessage()) {
            Message message = update.getMessage();
            if (message.hasText()) {
                handleUserResponse(message);
            }
        }
    }

    private void handleCallbackQuery(CallbackQuery callbackQuery) {
        Long chatId = callbackQuery.getMessage().getChatId();
        String callbackData = callbackQuery.getData();

        feedbackStateMap.put(chatId, callbackData);

        String responseText = switch (callbackData) {
            case "feedback_tg" -> "Напишите Ваше имя пользователя в Telegram (например, @username):";
            case "feedback_phone" -> "Напишите Ваш номер телефона (например, +71234567890):";
            case "feedback_email" -> "Напишите Ваш email (например, user@example.com):";
            default -> "Неизвестный выбор";
        };

        SendMessage message = new SendMessage();
        message.setChatId(chatId.toString());
        message.setText(responseText);

        try {
            execute(message);
        } catch (TelegramApiException e) {
            log.error("Error sending message", e);
        }
    }

    private void handleUserResponse(Message message) {
        User user = message.getFrom();
        Long chatId = message.getChatId();
        String userInput = message.getText();
        String feedbackType = feedbackStateMap.get(chatId);

        if (feedbackType == null) return;

        switch (feedbackType) {
            case "feedback_tg":
                if (userInput.startsWith("@")) {
                    processTelegramContact(chatId, userInput, user);
                } else {
                    sendValidationError(chatId, "Имя пользователя должно начинаться с @");
                }
                break;

            case "feedback_phone":
                if (userInput.matches("^\\+?[0-9\\s-]{10,}$")) {
                    processPhoneContact(chatId, userInput, user);
                } else {
                    sendValidationError(chatId, "Неверный формат телефона. Используйте международный формат");
                }
                break;

            case "feedback_email":
                if (userInput.matches("^[\\w-.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
                    processEmailContact(chatId, userInput, user);
                } else {
                    sendValidationError(chatId, "Неверный формат email. Пример: user@example.com");
                }
                break;
        }

        feedbackStateMap.remove(chatId);
    }

    private void processTelegramContact(Long chatId, String username, User user) {
        managerService.notifyManagerToCallSubscriber(username, chatId, user);
        sendConfirmation(chatId, "✅ Мы свяжемся с вами в Telegram: " + username);
    }

    private void processPhoneContact(Long chatId, String phone, User user) {
        managerService.notifyManagerToCallSubscriber(phone, chatId, user);
        sendConfirmation(chatId, "✅ Мы позвоним вам на номер: " + phone);
    }

    private void processEmailContact(Long chatId, String email, User user) {
        managerService.notifyManagerToCallSubscriber(email, chatId, user);
        sendConfirmation(chatId, "✅ Мы отправим письмо на: " + email);
    }

    private void sendConfirmation(Long chatId, String text) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId.toString());
        message.setText(text);
        try {
            execute(message);
        } catch (TelegramApiException e) {
            log.error("Error sending confirmation", e);
        }
    }

    private void sendValidationError(Long chatId, String errorText) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId.toString());
        message.setText("❌ " + errorText + "\nПожалуйста, попробуйте еще раз:");
        try {
            execute(message);
        } catch (TelegramApiException e) {
            log.error("Error sending validation error", e);
        }
    }
}
