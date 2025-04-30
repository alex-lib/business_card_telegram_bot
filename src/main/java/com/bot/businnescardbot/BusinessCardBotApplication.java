package com.bot.businnescardbot;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = "com.bot.businnescardbot.repositories")
@EntityScan(basePackages = "com.bot.businnescardbot.entities")
public class BusinessCardBotApplication {
	public static void main(String[] args) {
		SpringApplication.run(BusinessCardBotApplication.class, args);
	}
}
