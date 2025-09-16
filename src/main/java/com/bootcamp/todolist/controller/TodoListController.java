package com.bootcamp.todolist.controller;

import com.bootcamp.todolist.dao.entity.TodoItem;
import com.bootcamp.todolist.service.TodoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

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

    public ResponseEntity<TodoItem> create(TodoItem item) {
        return todoService.save(item)
                .map(todoItem -> ResponseEntity.status(201).body(todoItem))
                .orElse(ResponseEntity.status(500).build());
    }
    public ResponseEntity<TodoItem> update(long id, TodoItem item) {
        return todoService.findById(id)
                .map(existingItem -> {
                    existingItem.setText(item.getText());
                    existingItem.setDone(item.getDone());
                    return todoService.save(existingItem)
                            .map(ResponseEntity::ok)
                            .orElse(ResponseEntity.status(500).build());
                })
                .orElse(ResponseEntity.notFound().build());
    }
    public ResponseEntity<Void> deleteById(long id) {
        if (todoService.deleteById(id)) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
