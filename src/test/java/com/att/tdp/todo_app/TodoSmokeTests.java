package com.att.tdp.todo_app;

import com.att.tdp.todo_app.controllers.MetaController;
import com.att.tdp.todo_app.controllers.TodoController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class TodoSmokeTests {

	@Autowired
	TodoController todoController;

	@Autowired
	MetaController metaController;

	@Test
	void contextLoads() {
		assertThat(todoController).isNotNull();
		assertThat(metaController).isNotNull();
	}
}
