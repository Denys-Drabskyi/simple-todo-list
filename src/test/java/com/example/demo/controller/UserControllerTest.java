package com.example.demo.controller;

import com.example.demo.dto.UserDto;
import com.example.demo.service.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @InjectMocks
    private UserController userController;

    @Test
    public void testGetUserByUsername_ExistingUser() throws Exception {
        String username = "john_doe";
        UserDto userDto = new UserDto();
        userDto.setUsername(username);
        userDto.setEmail("john.doe@example.com");

        when(userService.getUserByUsername(username)).thenReturn(userDto);

        mockMvc.perform(MockMvcRequestBuilders.get("/users/{username}", username)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value(username))
                .andExpect(jsonPath("$.email").value("john.doe@example.com"));
    }

    @Test
    public void testGetUserByUsername_NonExistingUser() throws Exception {
        String username = "non_existent_user";

        when(userService.getUserByUsername(username)).thenReturn(null);

        mockMvc.perform(MockMvcRequestBuilders.get("/users/{username}", username)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testCreateUser_ValidRequest() throws Exception {
        UserDto requestDto = new UserDto();
        requestDto.setUsername("john_doe");
        requestDto.setEmail("john.doe@example.com");
        requestDto.setPassword("secretpassword");

        UserDto responseDto = new UserDto();
        responseDto.setUsername("john_doe");
        responseDto.setEmail("john.doe@example.com");

        when(userService.createUser(requestDto)).thenReturn(responseDto);

        mockMvc.perform(MockMvcRequestBuilders.post("/users/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\":\"john_doe\",\"email\":\"john.doe@example.com\",\"password\":\"secretpassword\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.username").value("john_doe"))
                .andExpect(jsonPath("$.email").value("john.doe@example.com"));
    }

    @Test
    public void testEditUser_ExistingUser() throws Exception {
        String username = "john_doe";
        UserDto requestDto = new UserDto();
        requestDto.setUsername(username);
        requestDto.setPassword("newpassword");

        UserDto responseDto = new UserDto();
        responseDto.setUsername(username);
        responseDto.setEmail("john.doe@example.com");

        when(userService.updateUser(requestDto)).thenReturn(responseDto);

        mockMvc.perform(MockMvcRequestBuilders.put("/users/edit")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\":\"john_doe\",\"password\":\"newpassword\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("john_doe"))
                .andExpect(jsonPath("$.email").value("john.doe@example.com"));
    }

    @Test
    public void testEditUser_NonExistingUser() throws Exception {
        UserDto requestDto = new UserDto();
        requestDto.setUsername("non_existent_user");
        requestDto.setPassword("newpassword");

        when(userService.updateUser(requestDto)).thenReturn(null);

        mockMvc.perform(MockMvcRequestBuilders.put("/users/edit")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\":\"non_existent_user\",\"password\":\"newpassword\"}"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testDeleteUser_ValidRequest() throws Exception {
        String username = "john_doe";

        mockMvc.perform(MockMvcRequestBuilders.delete("/users/delete/{username}", username)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }
}
