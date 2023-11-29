package com.example.todolist;

import org.springframework.data.mongodb.core.mapping.Document;

@Document("todos")
public record ToDo(String id, String description, Status status) {
}
