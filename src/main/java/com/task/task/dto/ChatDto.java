package com.task.task.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ChatDto {
    private String id;
    private String chatName;
    private LocalDateTime createdAt;
}
