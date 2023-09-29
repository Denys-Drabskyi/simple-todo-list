package com.example.demo.mapper;

import com.example.demo.dto.ToDoDto;
import com.example.demo.entity.ToDo;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface ToDoMapper {

    ToDoDto toDto(ToDo toDo);

    ToDo toEntity(ToDoDto toDoDto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromUserDto(ToDoDto dto, @MappingTarget ToDo entity);
}
