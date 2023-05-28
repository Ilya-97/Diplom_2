import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.Test;

import java.util.ArrayList;

import static org.hamcrest.Matchers.equalTo;
public class TakeOrderDataTest extends MainData {

    @Test
    @DisplayName("Получение списка заказов пользователя")
    @Description("Проверка ответа на получение списка заказов в авторизованном канале")
    public void getOrderListWithAuthText() {
        createNewUser(newUser);
        TokenData accessData = loginRestTest(loginForm).as(TokenData.class);
        OrderJson orderJsonTemplate = ingredientsListGet().as(OrderJson.class);
        Ingredients ingredients = new Ingredients(new ArrayList<>());
        ingredients.setIngredients(0, orderJsonTemplate.getData().get(0).get_id());
        ingredients.setIngredients(1, orderJsonTemplate.getData().get(1).get_id());
        ingredients.setIngredients(2, orderJsonTemplate.getData().get(2).get_id());
        orderCreateToken(ingredients, accessData.getAccessToken());
        getOrderListAuth(accessData.getAccessToken())
                .then()
                .statusCode(200)
                .body("success",equalTo(true));

    }

    @Test
    @DisplayName("Получение списка заказов без авторизации")
    @Description("Проверка ответа на получение списка заказов в неавторизованном канале")
    public void getOrderListWithoutAuthText() {
        getOrderList()
                .then()
                .statusCode(401)
                .body("message",equalTo("You should be authorised"));

    }
}
