package com.example.demo.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description = "To do Data Transfer Object")
@Data
public class ToDoDto {

    @Schema(description = "Unique identifier for the ToDo task", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long id;

    @Schema(description = "Title of the ToDo task", example = "Complete project report", requiredMode = Schema.RequiredMode.REQUIRED)
    private String title;

    @Schema(description = "Description of the ToDo task", example = "Write a detailed report about the project progress.", requiredMode = Schema.RequiredMode.REQUIRED)
    private String description;

}