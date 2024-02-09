import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.Matchers.equalTo;


public class OrderReceiveByIdTest {
    private int getIdOrder;

    @Before
    public void setUp() {
        RestAssured.baseURI = "http://qa-scooter.praktikum-services.ru/";
    }

    @Test
    @DisplayName("Check status order by id")
    public void testOrderGetByIdAndCheckingStatus() {
        CreateOrder createOrder = new CreateOrder("Регина","Зиннатуллина","Москва, ул. Жукова, дом 20","Теплый Стан", "89656612081",2,"2", "после 18-00", new String[]{"BLACK"});
        Response response =
                given()
                        .header("Content-type", "application/json") // заполни header
                        .and()
                        .body(createOrder) // заполни body
                        .when()
                        .post("/api/v1/orders"); // отправь запрос на ручку
        response.then().assertThat().statusCode(201)
                .and()
                .body("track", notNullValue());
        getIdOrder = response.jsonPath().getInt("track");
        System.out.println(getIdOrder);
        Response response2 =
                given()
                        .header("Content-type", "application/json") // заполни header
                        .and()
                        .queryParam("t", getIdOrder)
                        .when()
                        .get("/api/v1/orders/track"); // отправь запрос на ручку
        response2.then().assertThat().statusCode(200)
                .and()
                .body("order", notNullValue());
    }
    @Test
    @DisplayName("Check status order without id")
    public void testOrderGetWithoutIdAndCheckingStatus() {
        Response response =
                given()
                        .header("Content-type", "application/json") // заполни header
                        .and()
                        .queryParam("t")
                        .when()
                        .get("/api/v1/orders/track"); // отправь запрос на ручку
        response.then().assertThat().statusCode(400)
                .and()
                .body("message",equalTo("Недостаточно данных для поиска"));
    }
    @Test
    @DisplayName("Check status doesn't exist order")
    public void testOrderGetByDesntExistIdAndCheckingStatus() {
        Response response =
                given()
                        .header("Content-type", "application/json") // заполни header
                        .and()
                        .queryParam("t", "123456789")
                        .when()
                        .get("/api/v1/orders/track"); // отправь запрос на ручку
        response.then().assertThat().statusCode(404)
                .and()
                .body("message",equalTo("Заказ не найден"));
    }
    @After
    public void orderDeletion() {
        RestAssured.baseURI = "https://qa-scooter.praktikum-services.ru";
        // Отправляем DELETE-запрос на удаление курьера
        given()
                .header("Content-type", "application/json")
                .and()
                .queryParam("track", getIdOrder)
                .when()
                .put("/api/v1/orders/cancel/");
    }
}
