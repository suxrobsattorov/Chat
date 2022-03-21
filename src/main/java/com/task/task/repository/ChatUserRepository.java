package com.task.task.repository;

import com.task.task.entity.ChatEntity;
import com.task.task.entity.ChatUserEntity;
import com.task.task.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ChatUserRepository extends JpaRepository<ChatUserEntity, Long> {
    Optional<ChatUserEntity> findByUserAndChat(UserEntity user, ChatEntity chatEntity);

    List<ChatUserEntity> findByUser(UserEntity userEntity);
}
