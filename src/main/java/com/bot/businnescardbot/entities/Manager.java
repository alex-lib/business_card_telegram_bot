package com.bot.businnescardbot.entities;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@AllArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "managers")
public class Manager {
    @Id
    @Column(name = "id", columnDefinition = "BIGINT")
    private Long subscriberId;
    @Column(name = "user_name", columnDefinition = "VARCHAR(50)")
    private String userName;
    @Column(name = "first_name", columnDefinition = "VARCHAR(50)", nullable = true)
    private String firstName;
    @Column(name = "first_name", columnDefinition = "VARCHAR(50)", nullable = true)
    private String lastName;
}
