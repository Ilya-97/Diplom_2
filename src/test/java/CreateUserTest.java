import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.Test;
import static org.hamcrest.Matchers.equalTo;
public class CreateUserTest extends MainData {

    @Test
    @DisplayName("Успешное создание пользователя")
    @Description("Успешное создание пользователя, код ответа 200")
    public void createCourierValidTest() {
        createNewUser(newUser)
                .then()
                .statusCode(200);
    }

    @Test
    @DisplayName("Попытка создания существующего пользователя")
    @Description("Попытка создать пользователя с уже существующими данными")
    public void createCourierDuplicateTest() {
        createNewUser(newUser);
        createNewUser(newUser)
                .then()
                .statusCode(403)
                .body("message",equalTo("User already exists"));
    }

    @Test
    @DisplayName("Создание без полей")
    @Description("Пользователя нельзя создать. Отсутсвуют данные для успешного создания")
    public void createCourierInvalidTest() {
        createNewUser(newUserInvalid)
                .then()
                .statusCode(403)
                .body("message",equalTo("Email, password and name are required fields"));
    }

}
