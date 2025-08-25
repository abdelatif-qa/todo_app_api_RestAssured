package com.qacart.todo.api.steps;

import com.github.javafaker.Faker;
import com.qacart.todo.api.apis.TodoApi;
import com.qacart.todo.api.models.TodoPojo;
import io.restassured.response.Response;

public class TodoSteps {

    public static TodoPojo generateTodo() {
        Faker faker = new Faker();
        String item = faker.book().title();
        boolean isCompleted = false;
        return new TodoPojo(item, isCompleted);
    }

    public static String getTodoId(TodoPojo todoPojo, String token) {
        Response response = TodoApi.addTodo(todoPojo, token);
        return response.body().path("_id");
    }

}
