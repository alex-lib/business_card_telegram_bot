package com.bot.businnescardbot.repositories;
import com.bot.businnescardbot.entities.Subscriber;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubscriberRepository extends JpaRepository<Subscriber, Integer> {
    Subscriber findSubscriberBySubscriberId(Long subscriberId);
}
