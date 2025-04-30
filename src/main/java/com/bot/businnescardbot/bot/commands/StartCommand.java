package com.bot.businnescardbot.bot.commands;
import com.bot.businnescardbot.services.SubscriberServiceImpl;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.IBotCommand;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class StartCommand implements IBotCommand {
    private final SubscriberServiceImpl subscriberServiceImpl;

    @Override
    public String getCommandIdentifier() {
        return "start";
    }

    @Override
    public String getDescription() {
        return "Launch bot and save user's data to database";
    }

    @Override
    public void processMessage(AbsSender absSender, Message message, String[] strings) {
        User user = message.getFrom();
        subscriberServiceImpl.updateOrSaveSubscriber(user);
        SendMessage answer = new SendMessage();
        answer.setChatId(message.getChatId());
        answer.setText("""
               Привет!\s
               Мы компания СуперСервер🗄️
               Для того, чтобы перейти сразу к делу давайте познакомимся и узнаем как мы можем помочь Вашему бизнесу 💼
               Доступные команды:
               /about_us - краткая информация о нашей компании
               /our_services - наши услуги и товары
               /our_projects - наши последние проекты(будет реализованан по потребности)
               /our_website - наш сайт
               /get_contacts - получить контакты менеджера
               /get_feedback - заказать обратную связь
               \s""");

        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
        List<KeyboardRow> keyboard = new ArrayList<>();
        KeyboardRow row = new KeyboardRow();
        keyboard.add(row);
        keyboardMarkup.setKeyboard(keyboard);
        keyboardMarkup.setResizeKeyboard(true);
        keyboardMarkup.setOneTimeKeyboard(true);

        answer.setReplyMarkup(keyboardMarkup);

        try {
            absSender.execute(answer);
        } catch (TelegramApiException e) {
            log.error("Error occurred in /start command", e);
        }
    }
}