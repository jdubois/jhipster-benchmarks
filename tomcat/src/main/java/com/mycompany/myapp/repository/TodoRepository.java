package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Todo;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

@Component
public class TodoRepository {

    Collection<Todo> todos = new ArrayList<>();

    @PostConstruct
    public void init() {
        todos.addAll(Arrays.asList(
            new Todo("description 1", "details 1", true),
            new Todo("description 2", "details 2", true),
            new Todo("description 3", "details 3", true),
            new Todo("description 4", "details 4", true),
            new Todo("description 5", "details 5", true),
            new Todo("description 6", "details 6", true),
            new Todo("description 7", "details 7", true),
            new Todo("description 8", "details 8", true),
            new Todo("description 9", "details 9", true),
            new Todo("description 10", "details 10", true)));
    }

    public Todo save(Todo todo) {
        todo.setDescription("saved!");
        return todo;
    }

    public Collection<Todo> findAll() {
        return todos;
    }
}
