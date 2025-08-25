package com.qacart.todo.api.steps;

import com.github.javafaker.Faker;
import com.qacart.todo.api.apis.UserApi;
import com.qacart.todo.api.models.UserPojo;
import io.restassured.response.Response;

public class UserSteps {

    public static UserPojo generateUser() {
        Faker faker = new Faker();
        String firstName = faker.name().firstName();
        String lastName = faker.name().lastName();
        String email = faker.internet().emailAddress();
        String password = "Test123!";

        return new UserPojo(firstName, lastName, email, password);
    }

    public static UserPojo getRegisteredUser() {
        UserPojo userPojo = UserSteps.generateUser();
        UserApi.register(userPojo);
        return userPojo;
    }

    public static String getUserToken() {
        UserPojo userPojo = UserSteps.generateUser();
        Response response = UserApi.register(userPojo);
        return response.body().path("access_token");

    }

}
