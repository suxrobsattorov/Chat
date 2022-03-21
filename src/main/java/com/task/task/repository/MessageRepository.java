package com.task.task.repository;

import com.task.task.entity.ChatEntity;
import com.task.task.entity.MessageEntity;
import com.task.task.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MessageRepository extends JpaRepository<MessageEntity, String> {
    Optional<MessageEntity> findByUserAndChat(UserEntity user, ChatEntity chatEntity);
}
