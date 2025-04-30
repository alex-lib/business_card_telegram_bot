package com.bot.businnescardbot.entities;
import jakarta.persistence.*;
import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "subscribers")
public class Subscriber {
    @Id
    @Column(name = "id", columnDefinition = "BIGINT")
    private Long subscriberId;
    @Column(name = "user_name", columnDefinition = "VARCHAR(50)")
    private String userName;
    @Column(name = "first_name", columnDefinition = "VARCHAR(50)", nullable = true)
    private String firstName;
    @Column(name = "last_name", columnDefinition = "VARCHAR(50)", nullable = true)
    private String lastName;
}
