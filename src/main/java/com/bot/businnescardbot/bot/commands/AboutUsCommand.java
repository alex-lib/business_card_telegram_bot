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
public class AboutUsCommand implements IBotCommand {

    @Override
    public String getCommandIdentifier() {
        return "about_us";
    }

    @Override
    public String getDescription() {
        return "send info about company to subscriber";
    }

    @Override
    public void processMessage(AbsSender absSender, Message message, String[] strings) {
        SendMessage answer = new SendMessage();
        answer.setChatId(message.getChatId());
        answer.setText("""
                СуперСервер оказывает полный спектр услуг в сфере ИТ и ИБ:
                от аудита и проектирования до внедрения и поставки.
                Наша команда имеет многолетний опыт реализации комплексных решений и работает с проектами,
                которые задают тон индустрии и влияют на жизни людей уже сегодня.
                """);
        try {
            absSender.execute(answer);
        } catch (TelegramApiException e) {
//            log.error("Error occurred in /about_us command", e);
        }
    }
}
