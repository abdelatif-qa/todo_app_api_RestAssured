package com.qacart.todo.api.base;

import com.qacart.todo.utils.ConfigReader;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

public class Specs {

    public static String getEnv() {
        String env = System.getProperty("env", "PRODUCTION");
        String baseURL;
        switch(env) {
            case "PRODUCTION":
                baseURL = ConfigReader.getPropertiesValue("production.url");
                break;
            case "LOCAL":
                baseURL = ConfigReader.getPropertiesValue("local.url");
                break;
            default:
                throw new RuntimeException("Environment is not supported");
        }
        return baseURL;
    }

    public static RequestSpecification getRequestSpec() {
        System.getProperty("env");
        return given()
                .baseUri(getEnv())
                .contentType(ContentType.JSON)
                .log().all();
    }

}
