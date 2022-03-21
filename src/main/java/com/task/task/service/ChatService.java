package com.task.task.service;

import com.task.task.dto.ChatDto;
import com.task.task.entity.ChatEntity;
import com.task.task.entity.UserEntity;
import com.task.task.exp.AppBadRequestException;
import com.task.task.exp.EntityNotFoundException;
import com.task.task.repository.ChatRepository;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Data
public class ChatService {

    @Autowired
    private ChatRepository chatRepository;

    public ChatDto save(ChatDto dto) {

        if (dto.getChatName() == null || dto.getChatName().isEmpty()) {
            throw new AppBadRequestException("ChatName can not be null or empty");
        }

        for (ChatEntity ch: chatRepository.findAll()){
            if(ch.getChatName().equals(dto.getChatName())){
                throw new AppBadRequestException("it has a uchatName");
            }
        }

        ChatEntity entity = new ChatEntity();
        entity.setChatName(dto.getChatName());
        entity.setCreatedAt(LocalDateTime.now());

        chatRepository.save(entity);
        dto.setId(entity.getId());
        return dto;
    }

    public List<ChatDto> findAll() {
        List<ChatEntity> entityList = chatRepository.findAll();

        List<ChatDto> dtoList = entityList.stream().map(chatEntity -> {
            ChatDto dto = new ChatDto();
            dto.setId(chatEntity.getId());
            dto.setChatName(chatEntity.getChatName());
            dto.setCreatedAt(chatEntity.getCreatedAt());
            return dto;
        }).collect(Collectors.toList());

        return dtoList;
    }

    public ChatEntity get(String id) {
        return chatRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Chat Not Found: " + id));
    }
}
