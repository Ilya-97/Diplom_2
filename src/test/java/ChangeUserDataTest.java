import com.google.gson.Gson;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.Test;
import org.junit.Assert;

import static org.hamcrest.Matchers.equalTo;
public class ChangeUserDataTest extends BaseTest {
    @Test
    @DisplayName("Изменение данных пользователя")
    @Description("Успешная замена данных пользователя")
    public void changeUserDataTest() {
        createNewUser(mainData.newUser);
        TokenData accessData = loginRestTest(mainData.loginForm).as(TokenData.class);
        UserResponse userResponseTemplate = changeUserData(mainData.userNewData, accessData.getAccessToken()).as(UserResponse.class);
        accessData = loginRestTest(mainData.newLoginForm).as(TokenData.class);
        Assert.assertEquals(accessData.getUser().getEmail(), userResponseTemplate.getUser().getEmail());
        Assert.assertEquals(accessData.getUser().getName(), userResponseTemplate.getUser().getName());


    }

    @Test
    @DisplayName("Попытка изменения почты на существующую")
    @Description("Отказ. Пользователь с такой почтой уже существует")
    public void repeatChangeUserDataTest() {
        createNewUser(mainData.newUser);
        createNewUser(mainData.userNewData);
        TokenData accessData = loginRestTest(mainData.loginForm).as(TokenData.class);
        changeUserData(mainData.userNewData, accessData.getAccessToken())
                .then()
                .statusCode(403).body("message",equalTo("User with such email already exists"));
    }

    @Test
    @DisplayName("Попытка редактирования в неавторизованном канале")
    @Description("Попытка редактирования без авторизации пользователем")
    public void changeUserDataWithoutLoginTest() {
        createNewUser(mainData.newUser);
        changeUserData(mainData.userNewData, "")
                .then()
                .statusCode(401).body("message",equalTo("You should be authorised"));
    }
}
