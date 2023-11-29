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
}
