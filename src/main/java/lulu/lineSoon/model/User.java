package lulu.lineSoon.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "queue_user")
public class User {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable=false, length=40)
    private String nickname;

    @Column(nullable=false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(nullable=false)
    private String status = "WAITING"; // WAITING / SERVED / CANCELED

    public Long getId() { return id; }
    public String getNickname() { return nickname; }
    public void setNickname(String nickname) { this.nickname = nickname; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
