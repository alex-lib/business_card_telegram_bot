package com.bot.businnescardbot.entities;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "managers")
public class Manager {
    @Id
    @Column(name = "id", columnDefinition = "BIGINT")
    private Long managerId;
    @Column(name = "user_name", columnDefinition = "VARCHAR(50)")
    private String userName;
    @Column(name = "first_name", columnDefinition = "VARCHAR(50)", nullable = true)
    private String firstName;
    @Column(name = "last_name", columnDefinition = "VARCHAR(50)", nullable = true)
    private String lastName;
}