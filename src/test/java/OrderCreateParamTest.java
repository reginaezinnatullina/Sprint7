import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.notNullValue;

@RunWith(Parameterized.class)
public class OrderCreateParamTest {
    private int getIdOrder;
    private String[] selectColor;

    public OrderCreateParamTest(String[] selectColor) {
        this.selectColor = selectColor;
    }

    @Parameterized.Parameters
    public static Object[][] getColor() {
        return new Object[][]{
                {new String[]{"BLACK"}},
                {new String[]{"GREY"}},
                {new String[]{"BLACK", "GREY"}},
                {new String[]{}}
        };
    }

    @Before
    public void setUp() {
        RestAssured.baseURI = "http://qa-scooter.praktikum-services.ru/";
    }

    @Test
    @DisplayName("Check status order")
    public void testOrderCreationAndCheckingStatus() {
        CreateOrder createOrder =  new CreateOrder("Регина","Зиннатуллина","Москва, 142", "3","+7 965 355 35 35",5,"2020-06-06","saske, come back to konoha",selectColor);
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
