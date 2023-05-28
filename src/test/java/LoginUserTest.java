import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.Test;

import static org.hamcrest.Matchers.equalTo;
public class LoginUserTest extends MainData {

    @Test
    @DisplayName("Авторизация")
    @Description("Успешная авторизация пользователя")
    public void loginTestValid() {
        createNewUser(newUser);
        loginRestTest(loginForm)
                .then()
                .statusCode(200).body("success",equalTo(true));
    }

    @Test
    @DisplayName("Неуспешная авторизация")
    @Description("Прерывание авторизации в случае некорректных данных")
    public void loginTestInvalidLoginData() {
        createNewUser(newUser);
        loginRestTest(loginFormInvalid)
                .then()
                .statusCode(401).body("message",equalTo("email or password are incorrect"));
        loginRestTest(loginForm);
    }
}
