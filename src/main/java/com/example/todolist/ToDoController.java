package com.example.todolist;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("api/todo")
@RequiredArgsConstructor
public class ToDoController {

    private final ToDoService service;

    @PostMapping
    public ResponseEntity<ToDo> saveToDo(@RequestBody ToDo toDo){
        ToDo createdToDo = service.saveToDo(toDo);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(createdToDo.id()).toUri();
        return ResponseEntity.created(location).body(createdToDo);
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
    public ResponseEntity<ToDo> editDescription(@PathVariable String id, @RequestBody ToDo toDo){
        service.editDescription(id, toDo.description());
        service.updateStatus(id, toDo.status());
        ToDo updatedToDo = service.getToDoById(id);
        return ResponseEntity.ok(updatedToDo);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteToDoById(@PathVariable String id){
        service.deleteToDoById(id);
        return ResponseEntity.noContent().build();
    }
}
