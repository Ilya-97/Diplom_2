import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.Test;
import java.util.ArrayList;

import static org.hamcrest.Matchers.equalTo;
public class CreateOrderTest extends BaseTest {

    @Test
    @DisplayName("Создание заказа в авторизованном канале")
    @Description("Создание заказа в авторизованном канале в системе")
    public void createOrderWithAuthTest() {
        createNewUser(mainData.newUser);
        TokenData accessData = loginRestTest(mainData.loginForm).as(TokenData.class);
        OrderJson orderJsonTemplate = ingredientsListGet().as(OrderJson.class);
        Ingredients ingredients = new Ingredients(new ArrayList<>());
        ingredients.setIngredients(0, orderJsonTemplate.getData().get(0).get_id());
        ingredients.setIngredients(1, orderJsonTemplate.getData().get(1).get_id());
        ingredients.setIngredients(2, orderJsonTemplate.getData().get(2).get_id());
        orderCreateToken(ingredients, accessData.getAccessToken())
                .then()
                .statusCode(200)
                .body("success",equalTo(true));
    }

    @Test
    @DisplayName("Создание заказа в неавторизованном канале")
    @Description("Создание заказа в неавторизованном канале в системе")
    public void createOrderWithoutAuthTest() {
        OrderJson orderJsonTemplate = ingredientsListGet().as(OrderJson.class);
        Ingredients ingredients = new Ingredients(new ArrayList<>());
        ingredients.setIngredients(0, orderJsonTemplate.getData().get(0).get_id());
        ingredients.setIngredients(1, orderJsonTemplate.getData().get(1).get_id());
        ingredients.setIngredients(2, orderJsonTemplate.getData().get(2).get_id());
        orderCreate(ingredients)
                .then()
                .statusCode(200)
                .body("success",equalTo(true));;
    }

    @Test
    @DisplayName("Без авторизаци и ингредиентов")
    @Description("Создание заказа без автоиризации и ингредиентов в системе")
    public void createOrderWithoutAuthAndRegsTest() {
        OrderJson orderJsonTemplate = ingredientsListGet().as(OrderJson.class);
        Ingredients ingredients = new Ingredients(new ArrayList<>());
        orderCreate(ingredients)
                .then()
                .statusCode(400)
                .body("message",equalTo("Ingredient ids must be provided"));
    }

    @Test
    @DisplayName("С авторизацией и без ингредиентов")
    @Description("Создание заказа с автоиризацией и без ингредиентов")
    public void createOrderWithAuthAndWithoutRegsTest() {
        createNewUser(mainData.newUser);
        TokenData accessData = loginRestTest(mainData.loginForm).as(TokenData.class);
        Ingredients ingredients = new Ingredients(new ArrayList<>());
        orderCreateToken(ingredients, accessData.getAccessToken())
                .then()
                .statusCode(400)
                .body("message",equalTo("Ingredient ids must be provided"));
    }

    @Test
    @DisplayName("Создание заказа с некорректным ингредиентом")
    @Description("Создание с некорректным id ингредиента")
    public void createOrderWithIncorrectHash() {
        Ingredients ingredients = new Ingredients(new ArrayList<>());
        String incorrectReg = "incorrect reg";
        ingredients.setIngredients(0, incorrectReg);
        orderCreate(ingredients)
                .then()
                .statusCode(500);
    }
}
