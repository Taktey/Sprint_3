import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.any;
import static org.hamcrest.Matchers.equalTo;

public class CourierLoginTest {
    Courier courier;
    BaseHTTPClient client;
    private String expectedErrorMessage = "Учетная запись не найдена";

    @Before
    public void CourierLoginPreparations() {
        BaseHTTPClient clientPreparation = new BaseHTTPClient();
        Courier courierPreparation = new Courier();
        clientPreparation.doPostRequest(clientPreparation.getCreateClientAPIMethod(), courierPreparation.getRegisterRequestBody());
        courier = courierPreparation;
        client = clientPreparation;
    }

    @After
    public void CourierLoginTestDataDelete() {
        client.deleteCourier(courier);
    }

    @Test
    @DisplayName("Тест метода логирования в системе с использованием корректной пары логин/ пароль проверка возвращаемого кода и тела возвращаемого сообщения")
    @Description("При использовании метода логирования в системе с существующей парой логин/ пароль возвращается код 200 и id курьера")
    public void isLoginWithCorrectLoginDataPossibleAndReturnsStatusCode200AndIdValueTest() {
        Response response = client.doPostRequest(client.getLoginAPIMethod(), courier.getLoginRequestBody());
        response.then().assertThat().statusCode(200)
                .and().body("id", any(Integer.class));
    }

    @Test
    @DisplayName("Негативный тест метода логирования в системе, неверный логин, проверка кода и тела ответа")
    @Description("При отправке запроса на логин с неправильным логином (пароль соответствует другому логину) возвращается код 404 и \"message\": \"Учетная запись не найдена\"")
    public void isLoginWithWrongLoginAndCorrectPasswordForbiddenTest() {
        BaseHTTPClient client = new BaseHTTPClient();
        String originalLogin = courier.getCourierLogin();
        courier.setCourierLogin("WrongLogin");
        Response response = client.doPostRequest(client.getLoginAPIMethod(), courier.getLoginRequestBody());
        response.then().assertThat().statusCode(404).and().body("message", equalTo(expectedErrorMessage));
        courier.setCourierLogin(originalLogin);
    }

    @Test
    @DisplayName("Негативный тест метода логирования в системе, неверный пароль, проверка кода и тела ответа")
    @Description("При отправке запроса на логин с неправильным паролем (логину соответствует другой пароль) возвращается код 404 и \"message\": \"Учетная запись не найдена\"")
    public void isLoginWithWrongLoginDataForbiddenTest() {
        BaseHTTPClient client = new BaseHTTPClient();
        String originalPassword = courier.getCourierPassword();
        courier.setCourierPassword("WrongPassword");
        Response response = client.doPostRequest(client.getLoginAPIMethod(), courier.getLoginRequestBody());
        response.then().assertThat().statusCode(404).and().body("message", equalTo(expectedErrorMessage));
        courier.setCourierPassword(originalPassword);
    }

}
