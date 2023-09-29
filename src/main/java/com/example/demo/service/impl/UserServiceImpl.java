package com.example.demo.service.impl;

import com.example.demo.dto.UserDto;

import com.example.demo.entity.User;
import com.example.demo.mapper.UserMapper;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public UserDto getUserByUsername(String username) {
        return userMapper.toDto(
                userRepository.findByUsername(username)
                        .orElseThrow(()-> new EntityNotFoundException(
                        String.format("user with username %s not found", username)
                                )
                        )
        );
    }

    @Override
    public UserDto createUser(UserDto userDto){
        return userMapper.toDto(
                userRepository.save(
                        userMapper.toEntity(userDto)
                )
        );
    }

    @Override
    public UserDto updateUser(UserDto userDto){
        User user = userRepository.findByUsername(userDto.getUsername())
                .orElseThrow(()-> new EntityNotFoundException(
                String.format("user with username %s not found", userDto.getUsername())
        ));

        user.setPassword(userDto.getPassword());

        return userMapper.toDto(
                userRepository.save(user)
        );
    }

    @Override
    public void deleteUser(String username){
        User user = userRepository.findByUsername(username)
                .orElseThrow(()-> new EntityNotFoundException(
                        String.format("user with username %s not found", username)
                ));

        userRepository.delete(user);
    }

}

