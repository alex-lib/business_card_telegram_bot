package com.bot.businnescardbot.services;

public interface NotificationService {
    void sendMessage(Long chatId, String text);
}
