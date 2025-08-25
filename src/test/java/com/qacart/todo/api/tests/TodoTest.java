package com.qacart.todo.api.tests;


import com.qacart.todo.api.apis.TodoApi;
import com.qacart.todo.api.data.ErrorMessages;
import com.qacart.todo.api.models.ErrorPojo;
import com.qacart.todo.api.models.TodoPojo;
import com.qacart.todo.api.steps.TodoSteps;
import com.qacart.todo.api.steps.UserSteps;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;


@Feature("Todo Feature")
public class TodoTest {

    @Story("User Should Be Able To Add Task")
    @Test(description = "User Should Be Able To Add Task")
    public void userShouldBeAbleToAddTask() {

        TodoPojo todoPojo = TodoSteps.generateTodo();

        String token = UserSteps.getUserToken();

         Response response = TodoApi.addTodo(todoPojo, token);

         TodoPojo returnedTodoPojo = response.body().as(TodoPojo.class);

        assertThat(response.statusCode(), equalTo(201));
        assertThat(returnedTodoPojo.getItem(), equalTo(todoPojo.getItem()));
        assertThat(returnedTodoPojo.getIsCompleted(), equalTo(todoPojo.getIsCompleted()));

    }

    @Story("User Should Not Be Able To Add Task If IsCompleted Is Missing")
    @Test(description = "User Should Not Be Able To Add Task If IsCompleted Is Missing")
    public void userShouldNotBeAbleToAddTaskIfIsCompletedIsMissing() {

        TodoPojo todoPojo = new TodoPojo("Learn RestAssured");

        String token = UserSteps.getUserToken();
        Response response = TodoApi.addTodo(todoPojo, token);

        ErrorPojo returnedTodoPojo = response.body().as(ErrorPojo.class);

        assertThat(response.statusCode(), equalTo(400));
        assertThat(returnedTodoPojo.getMessage(), equalTo(ErrorMessages.IS_COMPLETED_IS_REQUIRED));

    }

    @Story("User Should Be Able To Get Task By Id")
    @Test(description = "User Should Be Able To Get Task By Id")
    public void userShouldBeAbleToGetTaskById() {

        TodoPojo todoPojo = TodoSteps.generateTodo();
        String token = UserSteps.getUserToken();
        String taskId = TodoSteps.getTodoId(todoPojo, token);

        Response response = TodoApi.getTodo(token, taskId);

        TodoPojo returnedTodoPojo = response.body().as(TodoPojo.class);

        assertThat(response.statusCode(), equalTo(200));
        assertThat(returnedTodoPojo.getItem(), equalTo(todoPojo.getItem()));
        assertThat(returnedTodoPojo.getIsCompleted(), equalTo(false));

    }

    @Story("User Should Be Able To Delete Task")
    @Test(description = "User Should Be Able To Delete Task")
    public void userShouldBeAbleToDeleteTask() {

        TodoPojo todoPojo = TodoSteps.generateTodo();
        String token = UserSteps.getUserToken();
        String taskId = TodoSteps.getTodoId(todoPojo, token);

        Response response = TodoApi.deleteTodo(token, taskId);

        TodoPojo returnedTodoPojo = response.body().as(TodoPojo.class);

        assertThat(response.statusCode(), equalTo(200));
        assertThat(returnedTodoPojo.getItem(), equalTo(todoPojo.getItem()));
        assertThat(returnedTodoPojo.getIsCompleted(), equalTo(false));

    }

}
