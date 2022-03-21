package com.task.task.controller;

import com.task.task.dto.ChatUserDto;
import com.task.task.exp.AppBadRequestException;
import com.task.task.exp.EntityNotFoundException;
import com.task.task.service.ChatUserService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Data
@RestController
@RequestMapping("/chat_user")
public class ChatUserController {

    @Autowired
    private ChatUserService chatUserService;

    @PostMapping
    public ResponseEntity<?> create(@RequestBody ChatUserDto dto) {
        try {
            ChatUserDto response = chatUserService.save(dto);
            return ResponseEntity.ok(response);
        } catch (AppBadRequestException | EntityNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<?> getAll() {
        List<ChatUserDto> dtoList = chatUserService.findAll();
        return ResponseEntity.ok(dtoList);
    }

    @GetMapping("/{user_id}")
    public ResponseEntity<?> getAll(@PathVariable("user_id") String user_id) {
        List<String> userChatList = chatUserService.findAllByUserId(user_id);
        return ResponseEntity.ok(userChatList);
    }
}
