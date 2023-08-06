import org.apache.commons.lang3.RandomStringUtils;


public class MainData {
    UserData userRandom = new UserData(RandomStringUtils.randomAlphabetic(8) + "@mail.ru", RandomStringUtils.randomAlphabetic(8), "Vanya");
    UserData newUserRandom = new UserData(RandomStringUtils.randomAlphabetic(8) + "@mail.ru", RandomStringUtils.randomAlphabetic(8), "dasfdsffgfdgsdfgfdgdsfg");
    UserData newUserInvalid = new UserData("IvAn1990@mail.ru", "Qwer1234", null);
    UserData loginForm = new UserData(userRandom.getEmail(), userRandom.getPassword());
    UserData newLoginForm = new UserData(newUserRandom.getEmail(), newUserRandom.getPassword());
    UserData loginFormInvalid = new UserData("Qwer1234", "Vanya");

}
