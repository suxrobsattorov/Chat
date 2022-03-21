package com.task.task.service;

import com.task.task.dto.UserDto;
import com.task.task.entity.UserEntity;
import com.task.task.exp.AppBadRequestException;
import com.task.task.exp.EntityNotFoundException;
import com.task.task.repository.UserRepository;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Data
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public UserDto save(UserDto dto) {

        if (dto.getUserName() == null || dto.getUserName().isEmpty()) {
            throw new AppBadRequestException("UserName can not be null or empty");
        }

        for (UserEntity u : userRepository.findAll()) {
            if (u.getUserName().equals(dto.getUserName())) {
                throw new AppBadRequestException("it has a userName");
            }
        }

        UserEntity entity = new UserEntity();
        entity.setUserName(dto.getUserName());
        entity.setCreatedAt(LocalDateTime.now());

        userRepository.save(entity);
        dto.setId(entity.getId());
        return dto;
    }

    public List<UserDto> findAll() {
        List<UserEntity> entityList = userRepository.findAll();

        List<UserDto> dtoList = entityList.stream().map(userEntity -> {
            UserDto dto = new UserDto();
            dto.setId(userEntity.getId());
            dto.setUserName(userEntity.getUserName());
            dto.setCreatedAt(userEntity.getCreatedAt());
            return dto;
        }).collect(Collectors.toList());

        return dtoList;
    }

    public UserEntity get(String id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User Not Found: " + id));
    }
}
