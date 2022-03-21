package com.task.task.service;

import com.task.task.dto.ChatUserDto;
import com.task.task.entity.ChatEntity;
import com.task.task.entity.ChatUserEntity;
import com.task.task.entity.UserEntity;
import com.task.task.exp.AppBadRequestException;
import com.task.task.repository.ChatUserRepository;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Data
public class ChatUserService {

    @Autowired
    private ChatUserRepository chatUserRepository;
    @Autowired
    private ChatService chatService;
    @Autowired
    private UserService userService;

    public ChatUserDto save(ChatUserDto dto) {

        if (dto.getUserId() == null || dto.getChatId() == null) {
            throw new AppBadRequestException("userId or chatId cannot be null");
        }

        UserEntity userEntity = userService.get(dto.getUserId());
        ChatEntity chatEntity = chatService.get(dto.getChatId());

        Optional<ChatUserEntity> chatUserEntityOptional = chatUserRepository.findByUserAndChat(userEntity, chatEntity);
        if (chatUserEntityOptional.isPresent()) {
            throw new AppBadRequestException("User already in chat.");
        }

        ChatUserEntity entity = new ChatUserEntity();
        entity.setUser(userEntity);
        entity.setChat(chatEntity);
        entity.setCreatedAt(LocalDateTime.now());

        chatUserRepository.save(entity);

        dto.setId(entity.getId());
        return dto;
    }

    public List<ChatUserDto> findAll() {
        List<ChatUserEntity> entityList = chatUserRepository.findAll();

        List<ChatUserDto> dtoList = entityList.stream().map(chatUserEntity -> {
            ChatUserDto dto = new ChatUserDto();
            dto.setId(chatUserEntity.getId());
            dto.setUserId(chatUserEntity.getUser().getId());
            dto.setChatId(chatUserEntity.getChat().getId());
            dto.setCreatedAt(chatUserEntity.getCreatedAt());
            return dto;
        }).collect(Collectors.toList());

        return dtoList;
    }

    public List<String> findAllByUserId(String userId) {
        UserEntity userEntity = userService.get(userId);
        List<ChatUserEntity> userEntityList = chatUserRepository.findByUser(userEntity);

        List<String> userChatList = new LinkedList<>();

        for (ChatUserEntity chu : userEntityList) {
            ChatEntity chatEntity = chatService.get(chu.getChat().getId());
            userChatList.add(chatEntity.getChatName());
        }
        return userChatList;
    }
}
