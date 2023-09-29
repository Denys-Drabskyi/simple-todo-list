package com.example.demo.controller;
import com.example.demo.dto.ToDoDto;
import com.example.demo.service.ToDoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/todos")
@RequiredArgsConstructor
public class ToDoController {

    private final ToDoService toDoService;

    @GetMapping("/{id}")
    @Operation(summary = "Get a To-Do item by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "To-Do item found"),
            @ApiResponse(responseCode = "404", description = "To-Do item not found")
    })
    public ResponseEntity<ToDoDto> getToDoById(@PathVariable Long id) {
        ToDoDto toDo = toDoService.getToDoById(id);
        if (toDo != null) {
            return ResponseEntity.ok(toDo);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    @Operation(summary = "Create a new To-Do item")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "To-Do item created"),
            @ApiResponse(responseCode = "400", description = "Bad request")
    })
    public ResponseEntity<ToDoDto> createToDo(@RequestBody ToDoDto toDoDto) {
        ToDoDto createdToDo = toDoService.createToDo(toDoDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdToDo);
    }

    @PutMapping
    @Operation(summary = "Update a To-Do item by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "To-Do item updated"),
            @ApiResponse(responseCode = "404", description = "To-Do item not found")
    })
    public ResponseEntity<ToDoDto> updateToDo(@RequestBody ToDoDto toDoDto) {
        ToDoDto updatedToDo = toDoService.updateToDo(toDoDto);
        if (updatedToDo != null) {
            return ResponseEntity.ok(updatedToDo);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a To-Do item by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "To-Do item deleted"),
            @ApiResponse(responseCode = "404", description = "To-Do item not found")
    })
    public ResponseEntity<Void> deleteToDo(@PathVariable Long id) {
        toDoService.deleteToDoById(id);
        return ResponseEntity.noContent().build();
    }
}
