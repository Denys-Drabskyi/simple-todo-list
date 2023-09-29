package com.example.demo.service.impl;

import com.example.demo.dto.UserDto;
import com.example.demo.entity.User;
import com.example.demo.mapper.UserMapper;
import com.example.demo.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private UserMapper userMapper;
    @InjectMocks
    private UserServiceImpl userService;

    @Test
    public void testGetUserByUsername_ExistingUser() {
        // Arrange
        String username = "john_doe";
        User user = new User();
        user.setId(1L);
        user.setUsername(username);

        when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));

        UserDto userDto = new UserDto();
        userDto.setId(1L);
        userDto.setUsername(username);
        when(userMapper.toDto(user)).thenReturn(userDto);

        // Act
        UserDto result = userService.getUserByUsername(username);

        // Assert
        assertNotNull(result);
        assertEquals(user.getId(), result.getId());
        assertEquals(user.getUsername(), result.getUsername());
    }

    @Test(expected = EntityNotFoundException.class)
    public void testGetUserByUsername_NonExistingUser() {
        // Arrange
        String username = "non_existing_user";

        when(userRepository.findByUsername(username)).thenReturn(Optional.empty());

        // Act
        userService.getUserByUsername(username);

        // Assert
        // Expecting EntityNotFoundException
    }

    @Test
    public void testCreateUser() {
        // Arrange
        UserDto userDto = new UserDto();
        userDto.setId(1L);
        userDto.setUsername("john_doe");
        userDto.setEmail("john.doe@example.com");
        userDto.setPassword("secretpassword");

        User user = new User();
        user.setId(1L);
        user.setUsername("john_doe");
        user.setEmail("john.doe@example.com");
        user.setPassword("secretpassword");

        when(userMapper.toEntity(userDto)).thenReturn(user);
        when(userMapper.toDto(user)).thenReturn(userDto);

        when(userRepository.save(user)).thenReturn(user);

        // Act
        UserDto result = userService.createUser(userDto);

        // Assert
        assertNotNull(result);
        assertEquals(userDto.getId(), result.getId());
        assertEquals(userDto.getUsername(), result.getUsername());
    }

    @Test
    public void testUpdateUser() {
        // Arrange
        String username = "john_doe";
        UserDto userDto = new UserDto();
        userDto.setId(1L);
        userDto.setUsername(username);
        userDto.setPassword("newpassword");

        User existingUser = new User();
        existingUser.setUsername(username);
        existingUser.setId(1L);
        existingUser.setUsername(username);
        existingUser.setPassword("newpassword");

        when(userRepository.findByUsername(username)).thenReturn(Optional.of(existingUser));
        when(userMapper.toEntity(userDto)).thenReturn(existingUser);
        when(userMapper.toDto(existingUser)).thenReturn(userDto);

        when(userRepository.save(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        UserDto result = userService.updateUser(userDto);

        // Assert
        assertNotNull(result);
        assertEquals(userDto.getUsername(), result.getUsername());
        assertEquals(userDto.getPassword(), result.getPassword());
    }

    @Test(expected = EntityNotFoundException.class)
    public void testUpdateUser_NonExistingUser() {
        // Arrange
        String username = "non_existing_user";
        UserDto userDto = new UserDto();
        userDto.setUsername(username);
        userDto.setPassword("newpassword");

        when(userRepository.findByUsername(username)).thenReturn(Optional.empty());

        // Act
        userService.updateUser(userDto);

        // Assert
        // Expecting EntityNotFoundException
    }

    @Test
    public void testDeleteUser() {
        // Arrange
        String username = "john_doe";
        User user = new User();

        when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));

        // Act
        userService.deleteUser(username);

        // Assert
        verify(userRepository, times(1)).delete(user);
    }

    @Test(expected = EntityNotFoundException.class)
    public void testDeleteUser_NonExistingUser() {
        // Arrange
        String username = "non_existing_user";

        when(userRepository.findByUsername(username)).thenReturn(Optional.empty());

        // Act
        userService.deleteUser(username);

        // Assert
        // Expecting EntityNotFoundException
    }
}
