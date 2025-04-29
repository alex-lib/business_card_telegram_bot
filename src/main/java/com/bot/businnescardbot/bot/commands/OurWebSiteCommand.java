package com.bot.businnescardbot.bot.commands;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.IBotCommand;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Service
@AllArgsConstructor
@Slf4j
public class OurWebSiteCommand implements IBotCommand  {
    @Override
    public String getCommandIdentifier() {
        return "our_website";
    }

    @Override
    public String getDescription() {
        return "send website's link to subscriber";
    }

    @Override
    public void processMessage(AbsSender absSender, Message message, String[] strings) {
        SendMessage answer = new SendMessage();
        answer.setChatId(message.getChatId());
        answer.setText("https://imba-it.ru/");
        try {
            absSender.execute(answer);
        } catch (TelegramApiException e) {
//            log.error("Error occurred in /our_website command", e);
        }
    }
}
