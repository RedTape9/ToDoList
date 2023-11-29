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

    public ToDo getToDoById(String id) {
        return repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Not Found"));
    }

    public void updateStatus(String id, Status status) {
        ToDo toDo = getToDoById(id);
        toDo = toDo.withStatus(status);
        repo.save(toDo);
    }

    public void editDescription(String id, String description) {
        ToDo toDo = getToDoById(id);
        toDo = toDo.withDescription(description);
        repo.save(toDo);
    }

    public void deleteToDoById(String id) {
        repo.deleteById(id);
    }
}
