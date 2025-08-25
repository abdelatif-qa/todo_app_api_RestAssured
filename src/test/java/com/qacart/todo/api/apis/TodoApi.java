package com.qacart.todo.api.apis;

import com.qacart.todo.api.base.Specs;
import com.qacart.todo.api.data.Route;
import com.qacart.todo.api.models.TodoPojo;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class TodoApi {

    public static Response addTodo(TodoPojo todoPojo, String token) {

        return given()
                .spec(Specs.getRequestSpec())
                .body(todoPojo)
                .auth().oauth2(token)
                .when()
                .post(Route.TODOS_ROUTE)
                .then()
                .log().all()
                .extract().response();
    }

    public static Response getTodo(String token, String taskId) {
        return given()
                .spec(Specs.getRequestSpec())
                .auth().oauth2(token)
                .when()
                .get(Route.TODOS_ROUTE + taskId)
                .then()
                .log().all()
                .extract().response();
    }

    public static Response deleteTodo(String token, String taskId) {
        return given()
                .spec(Specs.getRequestSpec())
                .auth().oauth2(token)
                .when()
                .delete(Route.TODOS_ROUTE + taskId)
                .then()
                .log().all()
                .extract().response();
    }

}
