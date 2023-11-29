package com.example.todolist;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ToDoService {

    private final ToDoRepo repo;
    public List<ToDo> getAllToDos() {
        return repo.findAll();
    }

    public ToDo saveToDo(ToDo toDo) {
        return repo.save(toDo);
    }
}
