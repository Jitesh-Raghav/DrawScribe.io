package com.example.drawscribeio.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Data
@Getter@Setter
public class ChatMessageDto {
    private Long messageId;
    private String messageText;
    private LocalDateTime timestamp;
    private Long userId;
    private Long gameSessionId;

    // Getters and Setters
}
