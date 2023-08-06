import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.Test;

import static org.hamcrest.Matchers.equalTo;
public class LoginUserTest extends BaseTest {

    @Test
    @DisplayName("Авторизация")
    @Description("Успешная авторизация пользователя")
    public void loginTestValid() {
        createNewUser(mainData.userRandom);
        loginTest(mainData.loginForm)
                .then()
                .statusCode(200).body("success",equalTo(true));
    }

    @Test
    @DisplayName("Неуспешная авторизация")
    @Description("Прерывание авторизации в случае некорректных данных")
    public void loginTestInvalidLoginData() {
        createNewUser(mainData.userRandom);
        loginTest(mainData.loginFormInvalid)
                .then()
                .statusCode(401).body("message",equalTo("email or password are incorrect"));
        loginTest(mainData.loginForm);
    }
}
