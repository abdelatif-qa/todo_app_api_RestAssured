package com.qacart.todo.api.tests;

import com.qacart.todo.api.apis.UserApi;
import com.qacart.todo.api.data.ErrorMessages;
import com.qacart.todo.api.models.ErrorPojo;
import com.qacart.todo.api.models.UserPojo;
import com.qacart.todo.api.steps.UserSteps;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.not;

@Feature("User Feature")
public class UserTest {

    @Story("User Should Be Able To Register")
    @Test(description =  "User Should Be Able To Register")
    public void userShouldBeAbleToRegister() {

//        String registerBody = "{\n" +
//                "    \"email\": \"jimlarry1@example.io\",\n" +
//                "    \"password\": \"Test123!\",\n" +
//                "    \"firstName\": \"Jim\",\n" +
//                "    \"lastName\": \"Larry\"\n" +
//                "}";

//        UserPojo userPojo = new UserPojo("Jim", "Larry", "jimlarry7@example.io", "Test123!");

        UserPojo userPojo = UserSteps.generateUser();

        Response response = UserApi.register(userPojo);

        //deserialization
        UserPojo returnedUserPojo = response.body().as(UserPojo.class);

        assertThat(response.statusCode(), equalTo(201));
        assertThat(returnedUserPojo.getFirstName(),equalTo(userPojo.getFirstName()));

//                .assertThat().statusCode(201)
//                .assertThat().body("firstName", equalTo("Jim"));

    }

    @Story("User Should Not Be Able To Register With Same Email")
    @Test(description =  "User Should Not Be Able To Register With Same Email")
    public void userShouldNotBeAbleToRegisterWithSameEmail() {

        UserPojo userPojo = UserSteps.getRegisteredUser();

        Response response = UserApi.register(userPojo);

        ErrorPojo returnedErrorPojo = response.body().as(ErrorPojo.class);

        assertThat(response.statusCode(), equalTo(400));
        assertThat(returnedErrorPojo.getMessage(), equalTo(ErrorMessages.EMAIL_IS_ALREADY_REGISTERED));

    }

    @Story("User Should Be Able To Login")
    @Test(description = "User Should Be Able To Login")
    public void userShouldBeAbleToLogin() {

        UserPojo userPojo = UserSteps.getRegisteredUser();
        UserPojo loginData = new UserPojo(userPojo.getEmail(), userPojo.getPassword());

        Response response = UserApi.login(loginData);

        UserPojo returnedUserPojo = response.body().as(UserPojo.class);

        assertThat(response.statusCode(), equalTo(200));
        assertThat(returnedUserPojo.getFirstName(), equalTo(userPojo.getFirstName()));
        assertThat(returnedUserPojo.getAccessToken(), not(equalTo(null)));

    }

    @Story("User Should Not Be Able To Login With Wrong Password")
    @Test(description = "User Should Not Be Able To Login With Wrong Password")
    public void userShouldNotBeAbleToLoginWithWrongPassword() {

        UserPojo userPojo = UserSteps.getRegisteredUser();
        UserPojo loginData = new UserPojo(userPojo.getEmail(), "wrongPassword");

        Response response = UserApi.login(loginData);

        ErrorPojo returnedErrorPojo = response.body().as(ErrorPojo.class);

        assertThat(response.statusCode(), equalTo(401));
        assertThat(returnedErrorPojo.getMessage(), equalTo(ErrorMessages.EMAIL_OR_PASSWORD_IS_WRONG));

    }

}
