package com.bootcamp.todolist.repository;

import com.bootcamp.todolist.dao.entity.TodoItem;

import java.util.List;

public interface TodoRepository {
    void clearAll();
    List<TodoItem> findAll();
    TodoItem findById(long id);
    TodoItem save(TodoItem item);
    boolean deleteById(long id);
}
