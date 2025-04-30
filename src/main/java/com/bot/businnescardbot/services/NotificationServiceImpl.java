package com.bot.businnescardbot.services;
import com.bot.businnescardbot.bot.BusinessCardBot;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Slf4j
@Service
public class NotificationServiceImpl implements NotificationService {
    private final BusinessCardBot bot;

    public NotificationServiceImpl(@Lazy BusinessCardBot bot) {
        this.bot = bot;
    }

    @Override
    public void sendMessage(Long chatId, String text) {
        try {
            bot.execute(new SendMessage(chatId.toString(), text));
            log.info("Notification was sent to {}", chatId);
        } catch (TelegramApiException e) {
            log.error("Failed to send notification to {}", chatId, e);
        }
    }
}
