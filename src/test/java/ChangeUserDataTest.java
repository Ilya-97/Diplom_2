import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.Test;
import org.junit.Assert;

import static org.hamcrest.Matchers.equalTo;
public class ChangeUserDataTest extends BaseTest {
    @Test
    @DisplayName("Изменение данных пользователя")
    @Description("Успешная замена данных пользователя")
    public void changeUserDataTest() {
        createNewUser(mainData.userRandom);
        TokenData accessData = loginTest(mainData.loginForm).as(TokenData.class);
        UserResponse userResponseTemplate = changeUserData(mainData.newUserRandom, accessData.getAccessToken()).as(UserResponse.class);
        accessData = loginTest(mainData.newLoginForm).as(TokenData.class);
        Assert.assertEquals(accessData.getUser().getEmail(), userResponseTemplate.getUser().getEmail());
        Assert.assertEquals(accessData.getUser().getName(), userResponseTemplate.getUser().getName());


    }

    @Test
    @DisplayName("Попытка изменения почты на существующую")
    @Description("Отказ. Пользователь с такой почтой уже существует")
    public void repeatChangeUserDataTest() {
        createNewUser(mainData.userRandom);
        createNewUser(mainData.newUserRandom);
        TokenData accessData = loginTest(mainData.loginForm).as(TokenData.class);
        changeUserData(mainData.newUserRandom, accessData.getAccessToken())
                .then()
                .statusCode(403).body("message",equalTo("User with such email already exists"));
    }

    @Test
    @DisplayName("Попытка редактирования в неавторизованном канале")
    @Description("Попытка редактирования без авторизации пользователем")
    public void changeUserDataWithoutLoginTest() {
        createNewUser(mainData.userRandom);
        changeUserData(mainData.newUserRandom, "")
                .then()
                .statusCode(401).body("message",equalTo("You should be authorised"));
    }
}
