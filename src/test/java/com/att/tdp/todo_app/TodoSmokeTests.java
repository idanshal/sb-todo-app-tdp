package com.att.tdp.todo_app;

import com.att.tdp.todo_app.controllers.MetaController;
import com.att.tdp.todo_app.controllers.TodoController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
class TodoSmokeTests {

	@Autowired
	TodoController todoController;

	@Autowired
	ApplicationContext applicationContext;

	@Test
	void contextLoads() {
		assertThat(todoController).isNotNull();
		assertThat(applicationContext.getBean("metaController")).isNotNull();
		assertThatThrownBy(() -> applicationContext.getBean("bogusController"))
				.isInstanceOf(NoSuchBeanDefinitionException.class);
	}
}
