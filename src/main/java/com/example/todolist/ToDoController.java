package com.example.todolist;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/todo")
@RequiredArgsConstructor
public class ToDoController {

    private final ToDoService service;

    @PostMapping
    public ToDo saveToDo(@RequestBody ToDo toDo){
        return service.saveToDo(toDo);
    }

    @GetMapping
    public List<ToDo> getAllToDos(){
        return service.getAllToDos();
    }

    @GetMapping("/{id}")
    public ToDo getToDoById(@PathVariable String id){
        return service.getToDoById(id);
    }

    @PutMapping("/{id}/status")
    public void updateStatus(@PathVariable String id, @RequestBody Status status){
        service.updateStatus(id, status);
    }

    @PutMapping("/{id}")
    public void editDescription(@PathVariable String id, @RequestBody ToDo toDo){
        service.editDescription(id, toDo.description());
        service.updateStatus(id, toDo.status());
    }

    @DeleteMapping("/{id}")
    public void deleteToDoById(@PathVariable String id){
        service.deleteToDoById(id);
    }
}
