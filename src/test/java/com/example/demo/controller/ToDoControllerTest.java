package com.example.demo.controller;

import com.example.demo.dto.ToDoDto;
import com.example.demo.service.ToDoService;
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

@WebMvcTest(ToDoController.class)
public class ToDoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ToDoService toDoService;

    @InjectMocks
    private ToDoController toDoController;

    @Test
    public void testGetToDoById_ExistingToDo() throws Exception {
        Long id = 1L;
        ToDoDto toDoDto = new ToDoDto();
        toDoDto.setId(id);
        toDoDto.setTitle("Test ToDo");
        toDoDto.setDescription("Test Description");

        when(toDoService.getToDoById(id)).thenReturn(toDoDto);

        mockMvc.perform(MockMvcRequestBuilders.get("/todos/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.title").value("Test ToDo"))
                .andExpect(jsonPath("$.description").value("Test Description"));
    }

    @Test
    public void testGetToDoById_NonExistingToDo() throws Exception {
        Long id = 1L;

        when(toDoService.getToDoById(id)).thenReturn(null);

        mockMvc.perform(MockMvcRequestBuilders.get("/todos/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testCreateToDo_ValidRequest() throws Exception {
        ToDoDto requestDto = new ToDoDto();
        requestDto.setTitle("Test ToDo");
        requestDto.setDescription("Test Description");

        ToDoDto responseDto = new ToDoDto();
        responseDto.setId(1L);
        responseDto.setTitle("Test ToDo");
        responseDto.setDescription("Test Description");

        when(toDoService.createToDo(requestDto)).thenReturn(responseDto);

        mockMvc.perform(MockMvcRequestBuilders.post("/todos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\":\"Test ToDo\",\"description\":\"Test Description\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.title").value("Test ToDo"))
                .andExpect(jsonPath("$.description").value("Test Description"));
    }

    @Test
    public void testUpdateToDo_ExistingToDo() throws Exception {
        Long id = 1L;
        ToDoDto requestDto = new ToDoDto();
        requestDto.setId(id);
        requestDto.setTitle("Updated ToDo");
        requestDto.setDescription("Updated Description");

        ToDoDto responseDto = new ToDoDto();
        responseDto.setId(id);
        responseDto.setTitle("Updated ToDo");
        responseDto.setDescription("Updated Description");

        when(toDoService.updateToDo(requestDto)).thenReturn(responseDto);

        mockMvc.perform(MockMvcRequestBuilders.put("/todos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\":1,\"title\":\"Updated ToDo\",\"description\":\"Updated Description\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.title").value("Updated ToDo"))
                .andExpect(jsonPath("$.description").value("Updated Description"));
    }

    @Test
    public void testUpdateToDo_NonExistingToDo() throws Exception {
        ToDoDto requestDto = new ToDoDto();
        requestDto.setId(1L);
        requestDto.setTitle("Updated ToDo");
        requestDto.setDescription("Updated Description");

        when(toDoService.updateToDo(requestDto)).thenReturn(null);

        mockMvc.perform(MockMvcRequestBuilders.put("/todos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\":1,\"title\":\"Updated ToDo\",\"description\":\"Updated Description\"}"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testDeleteToDo_ValidRequest() throws Exception {
        Long id = 1L;

        mockMvc.perform(MockMvcRequestBuilders.delete("/todos/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }
}
