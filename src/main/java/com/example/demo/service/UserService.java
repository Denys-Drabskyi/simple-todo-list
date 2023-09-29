package com.example.demo.service;

import com.example.demo.dto.UserDto;

public interface UserService {
    UserDto getUserByUsername(String username);

    UserDto createUser(UserDto userDto);

    UserDto updateUser(UserDto userDto);

    void deleteUser(String username);
}
