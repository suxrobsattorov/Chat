package com.task.task.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ChatUserDto {
    private Long id;
    private String userId;
    private String chatId;
    private LocalDateTime createdAt;
}
