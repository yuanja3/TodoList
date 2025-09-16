package com.bootcamp.todolist.repository.jpa;

import com.bootcamp.todolist.dao.entity.TodoItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TodoJPARepository extends JpaRepository<TodoItem, Long> {
}
