import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static org.hamcrest.Matchers.equalTo;

@RunWith(Parameterized.class)
public class CourierLoginNegativeTest {
    private String login;
    private String password;
    private BaseHTTPClient client = new BaseHTTPClient();
    private int expectedStatusCode = 400;
    private String expectedErrorMessage = "Недостаточно данных для входа";

    public CourierLoginNegativeTest(String login, String password) {
        this.login = login;
        this.password = password;

    }

    @Parameterized.Parameters(name = "Тестовые данные:{0},{1}")
    public static Object[][] getData() {
        return new Object[][]{
                {"", "SomePassword"},
                {"SomeLogin", ""},
        };
    }

    @Test
    @DisplayName("Негативный тест метода логирования в системе, проверка возвращаемого статус-кода")
    @Description("При передаче неполных данных возвращается статус-код 400")
    public void isLoginWithoutPartLoginDataForbiddenAndReturnsExpectedStatusCodeTest() {
        Courier courier = new Courier(login, password);
        Response response = client.doPostRequest(client.getLoginAPIMethod(), courier.getLoginRequestBody());
        response.then().assertThat().statusCode(expectedStatusCode);
    }

    @Test
    @DisplayName("Негативный тест метода логирования в системе, проверка тела ответа")
    @Description("При передаче неполных данных возвращается сообщение об ошибке")
    public void isLoginWithoutPartLoginDataForbiddenAndReturnsErrorMessageTest() {
        Courier courier = new Courier(login, password);
        Response response = client.doPostRequest(client.getLoginAPIMethod(), courier.getLoginRequestBody());
        response.then().body("message", equalTo(expectedErrorMessage));
    }

}
