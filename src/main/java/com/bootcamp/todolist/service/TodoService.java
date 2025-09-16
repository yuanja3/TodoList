package com.bootcamp.todolist.service;

import com.bootcamp.todolist.dao.dto.CreateTodoItemReq;
import com.bootcamp.todolist.dao.entity.TodoItem;
import com.bootcamp.todolist.repository.TodoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TodoService {
    private final TodoRepository todoRepository;
    public void clearAll() {
        todoRepository.clearAll();
    }
    public Optional<List<TodoItem>> findAll() {
        List<TodoItem> items = todoRepository.findAll();
        return items.isEmpty() ? Optional.empty() : Optional.of(items);
    }
    public Optional<TodoItem> findById(long id) {
        return Optional.ofNullable(todoRepository.findById(id));
    }
    public Optional<TodoItem> save(TodoItem item) {
        return Optional.ofNullable(todoRepository.save(item));
    }
    public Optional<TodoItem> create(CreateTodoItemReq req) {
        String text = req.getText();
        TodoItem item = new TodoItem();
        item.setText(text==null ? "" : text);
        item.setDone(0);
        return Optional.ofNullable(todoRepository.save(item));
    }
    public Optional<TodoItem> update(long id, TodoItem item) {
        return findById(id)
                .map(existingItem -> {
                    existingItem.setText(item.getText());
                    existingItem.setDone(item.getDone());
                    return todoRepository.save(existingItem);
                });
    }
    public boolean deleteById(long id) {
        return todoRepository.deleteById(id);
    }
}
