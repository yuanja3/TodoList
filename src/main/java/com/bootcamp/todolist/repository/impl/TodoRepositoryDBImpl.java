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
        todoJPARepository.deleteAll();
    }

    @Override
    public List<TodoItem> findAll() {
        return todoJPARepository.findAll();
    }

    @Override
    public TodoItem findById(long id) {
        return todoJPARepository.findById(id).orElse(null);
    }

    @Override
    public TodoItem save(TodoItem item) {
        return todoJPARepository.save(item);
    }

    @Override
    public boolean deleteById(long id) {
        if (findById(id) != null) {
            todoJPARepository.deleteById(id);
            return true;
        }
        return false;
    }
}
