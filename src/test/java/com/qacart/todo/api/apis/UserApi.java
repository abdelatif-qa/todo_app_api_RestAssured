package com.qacart.todo.api.apis;

import com.qacart.todo.api.base.Specs;
import com.qacart.todo.api.data.Route;
import com.qacart.todo.api.models.UserPojo;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class UserApi {

    public static Response register(UserPojo userPojo) {
        return given()
                .spec(Specs.getRequestSpec())
                .body(userPojo)
                .when()
                .post(Route.REGISTER_ROUTE)
                .then()
                .log().all()
                .extract().response();

    }

    public static Response login(UserPojo userPojo) {
        return given()
                .spec(Specs.getRequestSpec())
                .body(userPojo)
                .when()
                .post(Route.LOGIN_ROUTE)
                .then()
                .log().all()
                .extract().response();
    }

}
