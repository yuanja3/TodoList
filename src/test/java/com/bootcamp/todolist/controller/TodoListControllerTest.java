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

import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.DriverManager;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
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
    @Test
    void should_return_updated_todo_item_when_update_given_correct_param() throws Exception {
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
        String updatedRequestBody = """
                {
                    "text": "Buy snacks",
                    "done": 1
                }
                """;
        mockMvc.perform(put("/todos/%d".formatted(id))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updatedRequestBody))
                .andExpect(status().isOk())
                .andExpect(content().json("""
                        {
                            "id": %d,
                            "text": "Buy snacks",
                            "done": 1
                            }""".formatted(id)));
    }
    @Test
    void should_return_updated_todo_item_with_path_id_when_update_given_a_different_id_in_body() throws Exception {
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
        String requestBody01 = """
                {
                    "text": "Buy water"
                }
                """;
        ResultActions resultActions01 = mockMvc.perform(post("/todos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody01));
        MvcResult mvcResult01 = resultActions01.andReturn();
        String contentAsString01 = mvcResult01.getResponse().getContentAsString();
        long id01= new ObjectMapper().readTree(contentAsString01).get("id").asLong();
        String updatedRequestBody = """
                {
                    "id": %d,
                    "text": "Buy snacks",
                    "done": 1  
                    }
                """.formatted(id01);
        mockMvc.perform(put("/todos/%d".formatted(id))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updatedRequestBody))
                .andExpect(status().isOk())
                .andExpect(content().json("""
                        {
                            "id": %d,
                            "text": "Buy snacks",
                            "done": 1
                            }""".formatted(id)));
    }
    @Test
    void should_return_404_when_update_given_a_non_existing_id() throws Exception {
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
        String updatedRequestBody = """
                {
                    "text": "Buy snacks",
                    "done": 1
                }
                """;
        mockMvc.perform(put("/todos/%d".formatted(id+1))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updatedRequestBody))
                .andExpect(status().isNotFound());
    }
    @Test
    void should_return_422_when_update_given_a_incomplete_payload() throws Exception {
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
        String updatedRequestBody = """
                {
                    
                }
                """;
        mockMvc.perform(put("/todos/%d".formatted(id))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updatedRequestBody))
                .andExpect(status().isUnprocessableEntity());
    }
    @Test
    void should_return_204_when_delete_given_a_existing_id() throws Exception {
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
        mockMvc.perform(delete("/todos/%d".formatted(id)))
                .andExpect(status().isNoContent());
    }
    @Test
    void should_return_404_when_delete_given_a_non_existing_id() throws Exception {
        //test implement
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
        mockMvc.perform(delete("/todos/%d".formatted(id+1)))
                .andExpect(status().isNotFound());
    }
}
