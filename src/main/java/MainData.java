import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.After;
import org.junit.Before;

import static io.restassured.RestAssured.given;

public class MainData {
    UserData newUser = new UserData(RandomStringUtils.randomAlphabetic(8) + "@mail.ru", RandomStringUtils.randomAlphabetic(8), "Vanya");
    UserData userNewData = new UserData(RandomStringUtils.randomAlphabetic(8) + "@mail.ru", RandomStringUtils.randomAlphabetic(8), "dasfdsffgfdgsdfgfdgdsfg");
    UserData newUserInvalid = new UserData("IvAn1990@mail.ru", "Qwer1234", null);
    UserData loginForm = new UserData(newUser.getEmail(), newUser.getPassword());
    UserData newLoginForm = new UserData(userNewData.getEmail(), userNewData.getPassword());
    UserData loginFormInvalid = new UserData("Qwer1234", "Vanya");

}
