package com.task.task.service;

import com.task.task.dto.MessageDto;
import com.task.task.entity.ChatEntity;
import com.task.task.entity.ChatUserEntity;
import com.task.task.entity.MessageEntity;
import com.task.task.entity.UserEntity;
import com.task.task.exp.AppBadRequestException;
import com.task.task.repository.MessageRepository;
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
public class MessageService {

    @Autowired
    private MessageRepository messageRepository;
    @Autowired
    private ChatService chatService;
    @Autowired
    private UserService userService;
    @Autowired
    private ChatUserService chatUserService;

    public MessageDto save(MessageDto dto) {

        if (dto.getUserId() == null || dto.getChatId() == null) {
            throw new AppBadRequestException("userId or chatId cannot be null");
        }

        UserEntity userEntity = userService.get(dto.getUserId());
        ChatEntity chatEntity = chatService.get(dto.getChatId());

        boolean exists = false;

        for (ChatUserEntity ch : chatUserService.getChatUserRepository().findAll()) {
            if (userEntity.getId().equals(ch.getUser().getId()) &&
                    chatEntity.getId().equals(ch.getChat().getId())) {
                exists = true;
                break;
            }
        }

        if (!exists) {
            throw new AppBadRequestException("userId or chatId must be in chatUser");
        }

        MessageEntity entity = new MessageEntity();
        entity.setContent(dto.getContent());
        entity.setUser(userEntity);
        entity.setChat(chatEntity);
        entity.setCreatedAt(LocalDateTime.now());

        messageRepository.save(entity);
        dto.setId(entity.getId());
        return dto;
    }

    public List<MessageDto> findAll() {
        List<MessageEntity> entityList = messageRepository.findAll();

        List<MessageDto> dtoList = entityList.stream().map(messageEntity -> {
            MessageDto dto = new MessageDto();
            dto.setId(messageEntity.getId());
            dto.setContent(messageEntity.getContent());
            dto.setUserId(messageEntity.getUser().getId());
            dto.setChatId(messageEntity.getChat().getId());
            dto.setCreatedAt(messageEntity.getCreatedAt());
            return dto;
        }).collect(Collectors.toList());

        return dtoList;
    }

    public List<MessageDto> findAllByChatName(String chat_name) {

        List<MessageDto> chatMessages = new LinkedList<>();

        for (ChatEntity ch : chatService.getChatRepository().findAll()) {
            if (chat_name.equals(ch.getChatName())) {
                for (MessageEntity m : messageRepository.findAll()) {
                    if (m.getChat().getId().equals(ch.getId())) {
                        MessageDto dto = new MessageDto();
                        dto.setId(m.getId());
                        dto.setContent(m.getContent());
                        dto.setUserId(m.getUser().getId());
                        dto.setChatId(m.getChat().getId());
                        dto.setCreatedAt(m.getCreatedAt());
                        chatMessages.add(dto);
                    }
                }
            }
        }

        return chatMessages;
    }
}
