import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.Test;
import org.junit.Assert;

import static org.hamcrest.Matchers.equalTo;
public class ChangeUserDataTest extends MainData {
    @Test
    @DisplayName("Изменение данных пользователя")
    @Description("Успешная замена данных пользователя")
    public void changeUserDataTest() {
        createNewUser(newUser);
        TokenData accessData = loginRestTest(loginForm).as(TokenData.class);
        UserResponse userResponseTemplate = changeUserData(userNewData, accessData.getAccessToken()).as(UserResponse.class);
        accessData = loginRestTest(newLoginForm).as(TokenData.class);
        Assert.assertEquals(accessData.getUser().getEmail(), userResponseTemplate.getUser().getEmail());
        Assert.assertEquals(accessData.getUser().getName(), userResponseTemplate.getUser().getName());
    }

    @Test
    @DisplayName("Попытка изменения почты на существующую")
    @Description("Отказ. Пользователь с такой почтой уже существует")
    public void repeatChangeUserDataTest() {
        createNewUser(newUser);
        createNewUser(userNewData);
        TokenData accessData = loginRestTest(loginForm).as(TokenData.class);
        changeUserData(userNewData, accessData.getAccessToken())
                .then()
                .statusCode(403).body("message",equalTo("User with such email already exists"));
    }

    @Test
    @DisplayName("Попытка редактирования в неавторизованном канале")
    @Description("Попытка редактирования без авторизации пользователем")
    public void changeUserDataWithoutLoginTest() {
        createNewUser(newUser);
        changeUserData(userNewData, "")
                .then()
                .statusCode(401).body("message",equalTo("You should be authorised"));
    }
}
