package com.task.task.controller;

import com.task.task.dto.ChatDto;
import com.task.task.entity.ChatEntity;
import com.task.task.entity.UserEntity;
import com.task.task.exp.AppBadRequestException;
import com.task.task.service.ChatService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Data
@RestController
@RequestMapping("/chat")
public class ChatController {

    @Autowired
    private ChatService chatService;

    @PostMapping
    public ResponseEntity<?> create(@RequestBody ChatDto dto) {
        try {
            ChatDto response = chatService.save(dto);
            return ResponseEntity.ok(response);
        } catch (AppBadRequestException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<?> getAll() {
        List<ChatDto> dtoList = chatService.findAll();
        return ResponseEntity.ok(dtoList);
    }
}
