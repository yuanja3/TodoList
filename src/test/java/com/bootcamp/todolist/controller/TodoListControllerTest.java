package com.bootcamp.todolist.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;

import java.sql.Connection;
import java.sql.DriverManager;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class TodoListControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private TodoListController todoListController;
    @BeforeEach
    void setUp() throws Exception {
       //clean the data before each test
        todoListController.clearAll();
    }
    @Test
    void should_return_empty_json_array_when_none_exist_given_void() throws Exception {
        //test implement

        mockMvc.perform(get("/todos")).andExpect(status().isOk()).andExpect(content().json("[]"));

    }
    @Test
    void should_return__one_todo_item_when_one_record_exist_given_void() throws Exception {
        //test implement
        String requestBody = """
                {
                    "text": "Buy milk"
                }
                """;
        ResultActions resultActions = mockMvc.perform(post("/todos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody));
        MvcResult mvcResult = resultActions.andReturn();
        String contentAsString = mvcResult.getResponse().getContentAsString();
        long id= new ObjectMapper().readTree(contentAsString).get("id").asLong();
        mockMvc.perform(get("/todos")).andExpect(status().isOk()).andExpect(content().json("""
                [
                    {
                        "id": %d,
                        "text": "Buy milk",
                        "done": 0
                    }
                ]
                """.formatted(id)));
    }
}
