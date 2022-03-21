package com.task.task.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class MessageDto {
    private String id;
    private String content;
    private String chatId;
    private String userId;
    private LocalDateTime createdAt;
}
