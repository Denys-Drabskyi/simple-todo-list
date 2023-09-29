package com.example.demo.service;


import com.example.demo.dto.ToDoDto;

public interface ToDoService {

    ToDoDto createToDo(ToDoDto toDoDto);

    ToDoDto getToDoById(Long id);

    ToDoDto updateToDo(ToDoDto updatedToDoDto);

    void deleteToDoById(Long id);
}
