package com.bootcamp.todolist.dao.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdateTodoItemResp {
    private String text;
    private int done; // 0: not done, 1: done
}
