import com.github.jsonzou.jmockdata.JMockData;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.apache.http.HttpHeaders.HOST;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class CourierTest {
    private int idCourier;
    CreateCourier createCourier = new CreateCourier(JMockData.mock(String.class),JMockData.mock(String.class),JMockData.mock(String.class));
    LoginCourier loginCourier = new LoginCourier(JMockData.mock(String.class), JMockData.mock(String.class));
    CreateBadCourier createBadCourier = new CreateBadCourier(JMockData.mock(String.class));
    LoginCourier loginCourier4 = new LoginCourier(JMockData.mock(String.class), JMockData.mock(String.class));
    LoginCourier loginCourier5 = new LoginCourier(JMockData.mock(String.class), "");

    @Before
    public void setUp() {
        RestAssured.baseURI = URL.HOST;
    }

    @Test
    @DisplayName("Check creation courier and check status")
    public void testCourierCreationAndChekingStatus() {
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
        System.out.println(createCourier);
    }
    @Test
    @DisplayName("Check don't creation two same courier and check status")
    public void testCantCreateTwoSameCouriers() {
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
        Response response3 =
                given()
                        .header("Content-type", "application/json") // заполни header
                        .and()
                        .body(createCourier) // заполни body
                        .when()
                        .post("/api/v1/courier"); // отправь запрос на ручку
        response3.then().assertThat().statusCode(409)
                .and()
                .body("message", equalTo("Этот логин уже используется. Попробуйте другой."));
        System.out.println(createCourier);
    }
    @Test
    @DisplayName("Check bad creation courier and check status")
    public void testCreateBadCourierAndChekingStatus() {
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
        System.out.println(createCourier);
    }
    @Test
    @DisplayName("Check getting doesn't exist login courier and check status")
    public void testLoginDoesntExistsCourierAndChekingStatus() {
        Response response =
                given()
                        .header("Content-type", "application/json") // заполни header
                        .and()
                        .body(loginCourier4) // заполни body
                        .when()
                        .post("api/v1/courier/login"); // отправь запрос на ручку
        response.then().assertThat().statusCode(404)
                .and()
                .body("message",equalTo("Учетная запись не найдена"));
        System.out.println(loginCourier);
    }
    @Test
    @DisplayName("Check getting login couierier without param and check status")
    public void testLoginDoesntHaveParamCourierAndChekingStatus() {
        Response response =
                given()
                        .header("Content-type", "application/json") // заполни header
                        .and()
                        .body(loginCourier5) // заполни body
                        .when()
                        .post("api/v1/courier/login"); // отправь запрос на ручку
        response.then().assertThat().statusCode(400)
                .and()
                .body("message",equalTo("Недостаточно данных для входа"));
        System.out.println(loginCourier);
    }
    @After
    public void courierDeletion() {
        RestAssured.baseURI = URL.HOST;
        // Отправляем DELETE-запрос на удаление курьера
        given()
                .header("Content-type", "application/json")
                .when()
                .delete("/api/v1/courier/"+ idCourier);
    }
}

