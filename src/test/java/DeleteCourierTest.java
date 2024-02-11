import com.github.jsonzou.jmockdata.JMockData;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class DeleteCourierTest {
    private int idCourier;
    CreateCourier createCourier = new CreateCourier(JMockData.mock(String.class),JMockData.mock(String.class),JMockData.mock(String.class));
    LoginCourier loginCourier = new LoginCourier(JMockData.mock(String.class), JMockData.mock(String.class));
    @Before
    public void setUp() {
        RestAssured.baseURI = URL.HOST;
    }
    @Test
    @DisplayName("Check delete exists courier ans check status")
    public void testGoodDeleteCourierAndCheckingStatus() {
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
                        .body(createCourier) // заполни body
                        .when()
                        .post("api/v1/courier/login"); // отправь запрос на ручку
        response2.then().assertThat().statusCode(200)
                .and()
                .body("id", notNullValue());
        idCourier = response2.jsonPath().getInt("id");
        // Отправляем DELETE-запрос на удаление курьера
        given()
                .header("Content-type", "application/json")
                .when()
                .delete("/api/v1/courier/"+ idCourier);
        response.then().assertThat().statusCode(201)
                .and()
                .body("ok",equalTo(true));
    }
    @Test
    @DisplayName("Check delete courier which doesn't exist ans check status")
    public void testBadDeleteCourierWithDoesntExistIdAndCheckingStatus() {
        Response response =
        // Отправляем DELETE-запрос на удаление курьера
        given()
                .header("Content-type", "application/json")
                .when()
                .delete("/api/v1/courier/"+ idCourier);
        response.then().assertThat().statusCode(404)
                .and()
                .body("message",equalTo("Курьера с таким id нет."))
                .and()
                .body("code",equalTo(404));
    }
    @Test
    @DisplayName("Check delete courier without param ans check status")
    public void testBadDeleteCourierWithoutIdAndCheckingStatus() {
        Response response =
                // Отправляем DELETE-запрос на удаление курьера
                given()
                        .header("Content-type", "application/json")
                        .when()
                        .delete("/api/v1/courier/");
        response.then().assertThat().statusCode(404)
                .and()
                .body("message",equalTo("Not Found."))
                .and()
                .body("code",equalTo(404));
    }
}
