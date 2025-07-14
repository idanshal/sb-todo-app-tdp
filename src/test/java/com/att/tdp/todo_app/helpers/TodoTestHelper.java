package com.att.tdp.todo_app.helpers;


import com.att.tdp.todo_app.dto.TodoEntity;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TodoTestHelper {

    public static TodoEntity createTodoEntity(String title, String description) {
        return TodoEntity.builder()
                .title(title)
                .description(description)
                .isCompleted(false)
                .build();
    }
}
