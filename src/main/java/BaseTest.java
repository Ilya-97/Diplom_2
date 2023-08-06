import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;

import static io.restassured.RestAssured.given;


public class BaseTest {

    DataUrl dataUrl = new DataUrl();
    MainData mainData = new MainData();
    // Метод регистрации пользака
    public Response createNewUser(UserData object) {
        Response response = given().header("Content-type", "application/json").and().body(object).post(dataUrl.CREATE_USER);
        return response;
    }

    // Метод авторизации
    public Response loginTest(UserData object) {
        Response loginResponse = given()
                .header("Content-type", "application/json")
                .body(object)
                .post(dataUrl.LOGIN_REST);
        return loginResponse;
    }

    // Метод изменения данных
    public Response changeUserData(UserData object, String tokenData) {
        Response changeResponse = given()
                .header("authorization", tokenData)
                .header("Content-type", "application/json")
                .body(object)
                .patch(dataUrl.CHANGE_USER);
        return changeResponse;
    }

    // Разлогин пользователя
    public Response logoutUser(String logoutToken) {
        Response logoutResponse = given()
                .header("Content-type", "application/json")
                .body("{\"token\": \"" + logoutToken + "\"}")
                .post(dataUrl.LOGOUT_USER);
        return logoutResponse;
    }

    // Получение списка ингредиентов
    public Response ingredientsListGet() {
        Response createResponse = given()
                .header("Content-type", "application/json")
                .get(dataUrl.INGREDIENTS_LIST_GET);
        return createResponse;
    }

    // Создание заказа без токена
    public Response orderCreate(Ingredients ingredients) {
        Response createResponse = given()
                .header("Content-type", "application/json")
                .body(ingredients)
                .post(dataUrl.ORDERS);
        return createResponse;
    }

    // Создание заказа с токеном
    public Response orderCreateToken(Ingredients ingredients, String token) {
        Response createResponse = given()
                .header("Content-type", "application/json")
                .header("authorization", token)
                .body(ingredients)
                .post(dataUrl.ORDERS);
        return createResponse;
    }

    // Получение списка заказов
    public Response getOrderList() {
        Response createResponse = given()
                .header("Content-type", "application/json")
                .get(dataUrl.ORDERS);
        return createResponse;
    }

    // Получение списка заказов с токеном
    public Response getOrderListAuth(String tokenData) {
        Response createResponse = given()
                .header("Content-type", "application/json")
                .header("authorization", tokenData)
                .get(dataUrl.ORDERS);
        return createResponse;
    }

    @Before
    public void setUp() {
        RestAssured.baseURI = dataUrl.BASE_URL;
    }

    @After
    // Удаление пользака после тестов если он был создан
    public void deleteUser() {
        TokenData accessData = loginTest(mainData.loginForm).as(TokenData.class);
        if (accessData.getAccessToken() != null) {
            given().header("authorization", accessData.getAccessToken()).and().body(mainData.userRandom).delete(dataUrl.CHANGE_USER);
        } else {
            accessData = loginTest(mainData.newLoginForm).as(TokenData.class);
            if (accessData.getAccessToken() != null) {
                given().header("authorization", accessData.getAccessToken()).and().body(mainData.newUserRandom).delete(dataUrl.CHANGE_USER);
            }
        }
    }
}
