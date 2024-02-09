import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class CourierTest {
    private int idCourier;

    @Before
    public void setUp() {
        RestAssured.baseURI = "http://qa-scooter.praktikum-services.ru/";
    }

    @Test
    @DisplayName("Check creation courier and check status")
    public void testCourierCreationAndChekingStatus() {
        CreateCourier createCourier = new CreateCourier("Log1","12345","Ivan");
        LoginCourier loginCourier = new LoginCourier("Log1", "12345");
        Response response =
                given()
                        .header("Content-type", "application/json") // заполни header
                        .and()
                        .body(createCourier) // заполни body
                        .when()
                        .post("/api/v1/courier"); // отправь запрос на ручку
        response.then().assertThat().statusCode(201)
                .and()
                .body("ok",equalTo(true));
        Response response2 =
                given()
                        .header("Content-type", "application/json") // заполни header
                        .and()
                        .body(loginCourier) // заполни body
                        .when()
                        .post("api/v1/courier/login"); // отправь запрос на ручку
        response2.then().assertThat().statusCode(200)
                .and()
                .body("id", notNullValue());
        idCourier = response2.jsonPath().getInt("id");
    }
    @Test
    @DisplayName("Check don't creation two same courier and check status")
    public void testCantCreateTwoSameCouriers() {
        CreateCourier createCourier = new CreateCourier("Log2","12345","Ivan");
        CreateCourier createCourier2 = new CreateCourier("Log2","12345","Ivan");
        LoginCourier loginCourier = new LoginCourier("Log2", "12345");
        Response response =
                given()
                        .header("Content-type", "application/json") // заполни header
                        .and()
                        .body(createCourier) // заполни body
                        .when()
                        .post("/api/v1/courier"); // отправь запрос на ручку
        response.then().assertThat().statusCode(201)
                .and()
                .body("ok",equalTo(true));
        Response response2 =
                given()
                        .header("Content-type", "application/json") // заполни header
                        .and()
                        .body(loginCourier) // заполни body
                        .when()
                        .post("api/v1/courier/login"); // отправь запрос на ручку
        response2.then().assertThat().statusCode(200)
                .and()
                .body("id", notNullValue());
        idCourier = response2.jsonPath().getInt("id");
        Response response3 =
                given()
                        .header("Content-type", "application/json") // заполни header
                        .and()
                        .body(createCourier2) // заполни body
                        .when()
                        .post("/api/v1/courier"); // отправь запрос на ручку
        response3.then().assertThat().statusCode(409)
                .and()
                .body("message", equalTo("Этот логин уже используется. Попробуйте другой."));
    }
    @Test
    @DisplayName("Check bad creation courier and check status")
    public void testCreateBadCourierAndChekingStatus() {
        CreateBadCourier createBadCourier = new CreateBadCourier("Log3");
        Response response =
                given()
                        .header("Content-type", "application/json") // заполни header
                        .and()
                        .body(createBadCourier) // заполни body
                        .when()
                        .post("/api/v1/courier"); // отправь запрос на ручку
        response.then().assertThat().statusCode(400)
                .and()
                .body("message",equalTo("Недостаточно данных для создания учетной записи"));
    }
    @Test
    @DisplayName("Check getting doesn't exist login courier and check status")
    public void testLoginDoesntExistsCourierAndChekingStatus() {
        LoginCourier loginCourier = new LoginCourier("Log44", "12345");
        Response response =
                given()
                        .header("Content-type", "application/json") // заполни header
                        .and()
                        .body(loginCourier) // заполни body
                        .when()
                        .post("api/v1/courier/login"); // отправь запрос на ручку
        response.then().assertThat().statusCode(404)
                .and()
                .body("message",equalTo("Учетная запись не найдена"));
    }
    @Test
    @DisplayName("Check getting login couierier without param and check status")
    public void testLoginDoesntHaveParamCourierAndChekingStatus() {
        LoginCourier loginCourier = new LoginCourier("Log5", "");
        Response response =
                given()
                        .header("Content-type", "application/json") // заполни header
                        .and()
                        .body(loginCourier) // заполни body
                        .when()
                        .post("api/v1/courier/login"); // отправь запрос на ручку
        response.then().assertThat().statusCode(400)
                .and()
                .body("message",equalTo("Недостаточно данных для входа"));
    }
    @After
    public void courierDeletion() {
        RestAssured.baseURI = "https://qa-scooter.praktikum-services.ru";
        // Отправляем DELETE-запрос на удаление курьера
        given()
                .header("Content-type", "application/json")
                .when()
                .delete("/api/v1/courier/"+ idCourier);
    }
}

