package com.qaautomation.tests.api;

import com.qaautomation.config.ConfigManager;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@Epic("API Tests")
@Feature("Users")
public class UsersApiTest {

    @BeforeAll
    static void setup() {
        RestAssured.baseURI = ConfigManager.getApiBaseUrl();
        RestAssured.filters(new AllureRestAssured());
    }

    @Test
    @DisplayName("GET /users/2 debe devolver el usuario con id=2")
    @Description("Verifica que el endpoint de obtener usuario por ID retorne datos válidos")
    void getUserById_shouldReturnUser() {
        given()
            .when()
                .get("/users/2")
            .then()
                .statusCode(200)
                .body("id", equalTo(2))
                .body("email", notNullValue())
                .body("firstName", not(emptyString()));
    }

    @Test
    @DisplayName("GET /users debe devolver una lista de usuarios")
    @Description("Verifica que el endpoint de listar usuarios retorne una lista no vacía")
    void getUsers_shouldReturnList() {
        given()
            .queryParam("limit", 10)
            .when()
                .get("/users")
            .then()
                .statusCode(200)
                .body("users", not(empty()))
                .body("total", greaterThan(0));
    }
}