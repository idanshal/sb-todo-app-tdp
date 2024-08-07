package com.att.tdp.todo_app;

import com.att.tdp.todo_app.dal.TodoRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.DataAccessException;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class TodoControllerMvcDbErrorTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TodoRepository todoRepository;

    @Test
    void testDbError() throws Exception {
        // arrange
        when(todoRepository.findAll()).thenThrow(new DataAccessException("DB Error") {});
        // act
        mockMvc.perform(get("/api/todos"))
                // assert
                .andExpect(status().is5xxServerError());

    }

}