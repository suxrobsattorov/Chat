package com.task.task.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserDto {
    private String id;
    private String userName;
    private LocalDateTime createdAt;
}
