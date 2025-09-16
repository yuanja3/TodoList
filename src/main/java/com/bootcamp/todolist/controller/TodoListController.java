package com.bootcamp.todolist.controller;

import com.bootcamp.todolist.dao.dto.CreateTodoItemReq;
import com.bootcamp.todolist.dao.dto.UpdateTodoItemResp;
import com.bootcamp.todolist.dao.entity.TodoItem;
import com.bootcamp.todolist.exception.IncompletePayloadException;
import com.bootcamp.todolist.service.TodoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class TodoListController {
    private final TodoService todoService;
    public void clearAll() {
        todoService.clearAll();
    }
    @GetMapping("/todos")
    public ResponseEntity<List<TodoItem>> findAll() {
        return todoService.findAll()
                .map(todoItems -> ResponseEntity.ok().body(todoItems))
                .orElse(ResponseEntity.ok().body(List.of()));
    }
    @GetMapping(value = "/todos/{id}")
    public ResponseEntity<TodoItem> findById(@PathVariable long id) {
        return todoService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    @PostMapping("/todos")
    public ResponseEntity<TodoItem> create(@RequestBody CreateTodoItemReq item) {
        return todoService.create(item)
                .map(todoItem -> ResponseEntity.status(201).body(todoItem))
                .orElse(ResponseEntity.status(500).build());
    }
    @PutMapping(value = "/todos/{id}")
    public ResponseEntity<TodoItem> update(@PathVariable long id, @RequestBody UpdateTodoItemResp item) {
        if (item==null || item.getText() == null ) {
            throw new IncompletePayloadException("Payload is incomplete");
        }
        return todoService.findById(id)
                .map(existingItem -> {
                    existingItem.setText(item.getText());
                    existingItem.setDone(item.getDone());
                    return todoService.update(id,existingItem)
                            .map(todoItem -> ResponseEntity.ok().body(todoItem))
                            .orElse(ResponseEntity.status(500).build());
                })
                .orElse(ResponseEntity.notFound().build());
    }
    @DeleteMapping(value = "/todos/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable long id) {
        if (todoService.deleteById(id)) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
