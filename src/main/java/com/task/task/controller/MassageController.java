package com.task.task.controller;

import com.task.task.dto.MessageDto;
import com.task.task.entity.MessageEntity;
import com.task.task.exp.AppBadRequestException;
import com.task.task.exp.EntityNotFoundException;
import com.task.task.service.MessageService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Data
@RestController
@RequestMapping("/message")
public class MassageController {

    @Autowired
    private MessageService messageService;

    @PostMapping
    public ResponseEntity<?> create(@RequestBody MessageDto dto) {
        try {
            MessageDto response = messageService.save(dto);
            return ResponseEntity.ok(response);
        } catch (AppBadRequestException | EntityNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<?> getAll() {
        List<MessageDto> dtoList = messageService.findAll();
        return ResponseEntity.ok(dtoList);
    }

    @GetMapping("/{chat_name}")
    public ResponseEntity<?> getAll(@PathVariable("chat_name") String chat_name) {
        List<MessageDto> messageEntityList = messageService.findAllByChatName(chat_name);
        return ResponseEntity.ok(messageEntityList);
    }
}
