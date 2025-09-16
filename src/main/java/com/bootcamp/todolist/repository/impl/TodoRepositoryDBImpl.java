package com.bootcamp.todolist.repository.impl;

import com.bootcamp.todolist.dao.entity.TodoItem;
import com.bootcamp.todolist.repository.TodoRepository;
import com.bootcamp.todolist.repository.jpa.TodoJPARepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class TodoRepositoryDBImpl implements TodoRepository {
    private final TodoJPARepository todoJPARepository;

    @Override
    public void clearAll() {

    }

    @Override
    public List<TodoItem> findAll() {
        return List.of();
    }

    @Override
    public TodoItem findById(long id) {
        return null;
    }

    @Override
    public TodoItem save(TodoItem item) {
        return null;
    }

    @Override
    public boolean deleteById(long id) {
        return false;
    }
}
