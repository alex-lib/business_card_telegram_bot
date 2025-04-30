package com.bot.businnescardbot.bot.commands;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.IBotCommand;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class GetFeedBackCommand implements IBotCommand {
    @Override
    public String getCommandIdentifier() {
        return "get_feedback";
    }

    @Override
    public String getDescription() {
        return "call/text back to subscriber";
    }

    @Override
    public void processMessage(AbsSender absSender, Message message, String[] strings) {
        SendMessage answer = new SendMessage();
        answer.setChatId(message.getChatId());
        answer.setText("📩 Как вы хотите, что бы мы с Вами связались?");

        InlineKeyboardMarkup keyboard = new InlineKeyboardMarkup();
        List<InlineKeyboardButton> choice1 = List.of(
                InlineKeyboardButton.builder()
                        .text("Telegram")
                        .callbackData("feedback_tg")
                        .build()
        );

        List<InlineKeyboardButton> choice2 = List.of(
                InlineKeyboardButton.builder()
                        .text("Phone")
                        .callbackData("feedback_phone")
                        .build()
        );

        List<InlineKeyboardButton> choice3 = List.of(
                InlineKeyboardButton.builder()
                        .text("Email")
                        .callbackData("feedback_email")
                        .build()
        );

        keyboard.setKeyboard(List.of(choice1, choice2, choice3));
        answer.setReplyMarkup(keyboard);

        try {
            absSender.execute(answer);
        } catch (TelegramApiException e) {
            log.error("Error occurred in /get_feedback command", e);
        }
    }
}