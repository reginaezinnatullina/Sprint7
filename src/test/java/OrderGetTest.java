import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.notNullValue;

public class OrderGetTest {
    @Before
    public void setUp() {
        RestAssured.baseURI = "http://qa-scooter.praktikum-services.ru/";
    }

    @Test
    @DisplayName("Check all order in base")
    public void testGettingAllOrderAndCheckingStatus() {
        Response response =
                given()
                        .header("Content-type", "application/json") // заполни header
                        .get("/api/v1/orders"); // отправь запрос на ручку
        response.then().assertThat().statusCode(200)
                .and()
                .body(notNullValue());
    }
    @Test
    @DisplayName("Check only one order")
    public void testGettingOnlyOrderAndCheckingStatus() {
        GettingOrderWithParam gettingOrderWithParam = new GettingOrderWithParam(1,"2", 10, 1);
        Response response =
                given()
                        .header("Content-type", "application/json") // заполни header
                        .get("/api/v1/orders"); // отправь запрос на ручку
        response.then().assertThat().statusCode(200)
                .and()
                .body(notNullValue());
    }
}
