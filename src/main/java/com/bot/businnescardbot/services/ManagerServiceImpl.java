package com.bot.businnescardbot.services;
import com.bot.businnescardbot.entities.Manager;
import com.bot.businnescardbot.entities.Subscriber;
import com.bot.businnescardbot.repositories.ManagerRepository;
import com.bot.businnescardbot.repositories.SubscriberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.User;
import java.util.Optional;

@RequiredArgsConstructor
@Service
@Slf4j
public class ManagerServiceImpl implements ManagerService {
    private final ManagerRepository managerRepository;
    private final SubscriberRepository subscriberRepository;
    private final NotificationService notificationService;

    @Override
    public StringBuilder collectManagerContacts() {
        Optional<Manager> manager = managerRepository.findAll().stream().findFirst();
        return new StringBuilder().append("Контакты:\n")
                .append(manager.get().getFirstName()).append(" ").append(manager.get().getLastName())
                .append("\n")
                .append("tg: @")
                .append(manager.get().getUserName())
                .append("\n");
    }

    private Optional<Manager> findManager() {
        return managerRepository.findAll().stream().findFirst();
    }

    public void notifyManagerToCallSubscriber(String contact, Long chatId, User user) {
        Subscriber subscriber = subscriberRepository.findSubscriberBySubscriberId(user.getId());
        Optional<Manager> manager = findManager();

        StringBuilder message = new StringBuilder();
        message.append("Пользователь:\n")
                .append("Имя в tg: ")
                .append(subscriber.getFirstName())
                .append("\n")
                .append("Фамилия в tg: ")
                .append(subscriber.getLastName())
                .append("\n")
                .append("Юзернейм в tg: ")
                .append(subscriber.getUserName())
                .append("\n")
                .append("Просит связаться с ним: ")
                .append(contact);
        notificationService.sendMessage(manager.get().getManagerId(), message.toString());
    }
}