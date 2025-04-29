package com.bot.businnescardbot.bot;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.extensions.bots.commandbot.TelegramLongPollingCommandBot;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.IBotCommand;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;

@Service
@Slf4j
public class BusinessCardBot extends TelegramLongPollingCommandBot {
    private final String botUsername;

    public BusinessCardBot(
            @Value("7887636596:AAEG6RUSPh71_SQTsfcj3L_5DPfFzSNabGk") String botToken,
            @Value("business_card_example_bot") String botUsername,
            List<IBotCommand> commandList) {
        super(botToken);
        this.botUsername = botUsername;
        commandList.forEach(this::register);
    }

    @Override
    public String getBotUsername() {
        return botUsername;
    }

    @Override
    public void processNonCommandUpdate(Update update) {
    }
}
