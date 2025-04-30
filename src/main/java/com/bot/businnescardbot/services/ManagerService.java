package com.bot.businnescardbot.services;
import org.telegram.telegrambots.meta.api.objects.User;

public interface ManagerService {
    void notifyManagerToCallSubscriber(String contact, Long chatId, User user);
    StringBuilder collectManagerContacts();
}
