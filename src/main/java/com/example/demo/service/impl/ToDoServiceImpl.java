package com.example.demo.service.impl;

import com.example.demo.dto.ToDoDto;
import com.example.demo.entity.ToDo;
import com.example.demo.mapper.ToDoMapper;
import com.example.demo.repository.ToDoRepository;
import com.example.demo.service.ToDoService;
import jakarta.persistence.EntityNotFoundException;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Data
@Service
@RequiredArgsConstructor
public class ToDoServiceImpl implements ToDoService {
    private final ToDoMapper toDoMapper;
    private final ToDoRepository toDoRepository;

    @Override
    public ToDoDto createToDo(ToDoDto toDoDto) {
        return toDoMapper.toDto(
                toDoRepository.save(
                        toDoMapper.toEntity(toDoDto)
                )
        );
    }

    @Override
    public ToDoDto getToDoById(Long id) {
        return toDoMapper.toDto(
                toDoRepository.findById(id)
                        .orElseThrow(()-> new EntityNotFoundException(
                                        String.format("todo with id %s not found", id)
                                )
                        )
        );
    }

    @Override
    public ToDoDto updateToDo(ToDoDto updatedToDoDto) {
        ToDo toDo = toDoRepository.findById(updatedToDoDto.getId())
                .orElseThrow(()-> new EntityNotFoundException(
                                String.format("todo with id %s not found", updatedToDoDto.getId())
                        )
                );

        toDoMapper.updateEntityFromUserDto(updatedToDoDto, toDo);

        return toDoMapper.toDto(
                toDoRepository.save(toDo)
        );
    }

    @Override
    public void deleteToDoById(Long id) {
        ToDo toDo = toDoRepository.findById(id)
                .orElseThrow(()-> new EntityNotFoundException(
                                String.format("todo with id %s not found", id)
                        )
                );

        toDoRepository.delete(toDo);
    }
}
