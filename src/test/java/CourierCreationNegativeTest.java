import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static org.hamcrest.Matchers.equalTo;

@RunWith(Parameterized.class)
public class CourierCreationNegativeTest {
    private String login;
    private String password;
    private int expectedStatusCode = 400;
    private final String errorMessage = "Недостаточно данных для создания учетной записи";

    public CourierCreationNegativeTest(String login, String password) {
        this.login = login;
        this.password = password;
    }

    @Parameterized.Parameters(name = "Тестовые данные:{0},{1}")
    public static Object[][] getData() {
        return new Object[][]{
                {RandomStringUtils.randomAlphabetic(10), ""},
                {"", RandomStringUtils.randomAlphabetic(10)},
        };
    }

    @Test
    @DisplayName("Негативный тест метода регистрации курьера проверка кода ответа")
    @Description("Регистрация курьера невозможна без логина и пароля, при попытке возвращается код 400")
    public void isRegistrationWithoutRegDataReturnsStatusCode400Test() {
        Courier courier = new Courier(login, password);
        BaseHTTPClient client = new BaseHTTPClient();
        Response response = client.doPostRequest(client.getCreateClientAPIMethod(), courier.getRegisterRequestBody());
        response.then().assertThat().statusCode(expectedStatusCode);
    }

    @Test
    @DisplayName("Негативный тест метода регистрации курьера, проверка тела ответа")
    @Description("Регистрация курьера невозможна без логина и пароля, при попытке возвращается сообщение \"Недостаточно данных для создания учетной записи\"")
    public void isRegistrationWithoutRegDataReturnsErrorMessageTest() {
        Courier courier = new Courier(login, password);
        BaseHTTPClient client = new BaseHTTPClient();
        Response response = client.doPostRequest(client.getCreateClientAPIMethod(), courier.getRegisterRequestBody());
        response.then().body("message", equalTo(errorMessage));
    }
}
