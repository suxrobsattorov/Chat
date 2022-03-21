package com.task.task.controller;

import com.task.task.dto.UserDto;
import com.task.task.entity.UserEntity;
import com.task.task.exp.AppBadRequestException;
import com.task.task.service.UserService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Data
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<?> create(@RequestBody UserDto dto) {
        try {
            UserDto response = userService.save(dto);
            return ResponseEntity.ok(response);
        } catch (AppBadRequestException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<?> getAll() {
        List<UserDto> dtoList = userService.findAll();
        return ResponseEntity.ok(dtoList);
    }
}
