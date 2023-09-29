package com.example.demo.service.impl;

import com.example.demo.dto.ToDoDto;
import com.example.demo.entity.ToDo;
import com.example.demo.mapper.ToDoMapper;
import com.example.demo.repository.ToDoRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ToDoServiceImplTest {

    @Mock
    private ToDoRepository toDoRepository;

    @Mock
    private ToDoMapper toDoMapper;

    @InjectMocks
    private ToDoServiceImpl toDoService;

    @Test
    public void testCreateToDo() {
        // Arrange
        ToDoDto toDoDto = new ToDoDto();
        toDoDto.setId(1L);
        toDoDto.setTitle("Complete project report");
        toDoDto.setDescription("Write a detailed report about the project progress.");

        ToDo toDo = new ToDo();
        when(toDoMapper.toEntity(toDoDto)).thenReturn(toDo);
        when(toDoMapper.toDto(toDo)).thenReturn(toDoDto);
        when(toDoRepository.save(toDo)).thenReturn(toDo);

        // Act
        ToDoDto result = toDoService.createToDo(toDoDto);

        // Assert
        assertNotNull(result);
        assertEquals(toDoDto.getId(), result.getId());
        assertEquals(toDoDto.getTitle(), result.getTitle());
        assertEquals(toDoDto.getDescription(), result.getDescription());
    }

    @Test
    public void testGetToDoById_ExistingToDo() {
        // Arrange
        Long id = 1L;
        ToDo toDo = new ToDo();
        toDo.setId(id);
        toDo.setTitle("Complete project report");
        toDo.setDescription("Write a detailed report about the project progress.");

        when(toDoRepository.findById(id)).thenReturn(Optional.of(toDo));

        ToDoDto toDoDto = new ToDoDto();
        toDoDto.setId(id);
        toDoDto.setTitle("Complete project report");
        toDoDto.setDescription("Write a detailed report about the project progress.");
        when(toDoMapper.toDto(toDo)).thenReturn(toDoDto);

        // Act
        ToDoDto result = toDoService.getToDoById(id);

        // Assert
        assertNotNull(result);
        assertEquals(id, result.getId());
        assertEquals(toDo.getTitle(), result.getTitle());
        assertEquals(toDo.getDescription(), result.getDescription());
    }

    @Test(expected = EntityNotFoundException.class)
    public void testGetToDoById_NonExistingToDo() {
        // Arrange
        Long id = 1L;

        when(toDoRepository.findById(id)).thenReturn(Optional.empty());

        // Act
        toDoService.getToDoById(id);

        // Assert
        // Expecting EntityNotFoundException
    }

    @Test
    public void testUpdateToDo() {
        // Arrange
        Long id = 1L;
        ToDoDto updatedToDoDto = new ToDoDto();
        updatedToDoDto.setId(id);
        updatedToDoDto.setTitle("Updated project report");
        updatedToDoDto.setDescription("Updated report about the project progress.");

        ToDo existingToDo = new ToDo();
        existingToDo.setId(id);
        existingToDo.setTitle("Complete project report");
        existingToDo.setDescription("Write a detailed report about the project progress.");

        when(toDoRepository.findById(id)).thenReturn(Optional.of(existingToDo));

        // Provide custom behavior for the toDoMapper's toEntity and toDto methods
        when(toDoMapper.toDto(existingToDo)).thenReturn(updatedToDoDto);

        when(toDoRepository.save(existingToDo)).thenReturn(existingToDo);

        // Act
        ToDoDto result = toDoService.updateToDo(updatedToDoDto);

        // Assert
        assertNotNull(result);
        assertEquals(id, result.getId());
        assertEquals(updatedToDoDto.getTitle(), result.getTitle());
        assertEquals(updatedToDoDto.getDescription(), result.getDescription());
    }

    @Test(expected = EntityNotFoundException.class)
    public void testUpdateToDo_NonExistingToDo() {
        // Arrange
        Long id = 1L;
        ToDoDto updatedToDoDto = new ToDoDto();
        updatedToDoDto.setId(id);
        updatedToDoDto.setTitle("Updated project report");
        updatedToDoDto.setDescription("Updated report about the project progress.");

        when(toDoRepository.findById(id)).thenReturn(Optional.empty());

        // Act
        toDoService.updateToDo(updatedToDoDto);

        // Assert
        // Expecting EntityNotFoundException
    }

    @Test
    public void testDeleteToDoById() {
        // Arrange
        Long id = 1L;
        ToDo toDo = new ToDo();

        when(toDoRepository.findById(id)).thenReturn(Optional.of(toDo));

        // Act
        toDoService.deleteToDoById(id);

        // Assert
        verify(toDoRepository, times(1)).delete(toDo);
    }

    @Test(expected = EntityNotFoundException.class)
    public void testDeleteToDoById_NonExistingToDo() {
        // Arrange
        Long id = 1L;

        when(toDoRepository.findById(id)).thenReturn(Optional.empty());

        // Act
        toDoService.deleteToDoById(id);

        // Assert
        // Expecting EntityNotFoundException
    }
}
