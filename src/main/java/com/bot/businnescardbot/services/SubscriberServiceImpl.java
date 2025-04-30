package com.bot.businnescardbot.services;
import com.bot.businnescardbot.entities.Subscriber;
import com.bot.businnescardbot.repositories.SubscriberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.User;

@RequiredArgsConstructor
@Service
@Slf4j
public class SubscriberServiceImpl {
    private final SubscriberRepository subscriberRepository;

    public void updateOrSaveSubscriber(User user) {
        Subscriber persistantSubscriber = subscriberRepository.findSubscriberBySubscriberId(user.getId());
        if (persistantSubscriber != null) {
            persistantSubscriber = Subscriber.builder().userName(user.getUserName()).build();
            log.info("User is found and updated - {}", user.getId());
        }

        if (persistantSubscriber == null) {
            Subscriber transientSubscriber = Subscriber.builder()
                    .userName(user.getUserName())
                    .subscriberId(user.getId())
                    .firstName(user.getFirstName())
                    .lastName(user.getLastName())
                    .build();
            subscriberRepository.save(transientSubscriber);
            log.info("New user is saved - {}", user.getId());
        }
    }
}