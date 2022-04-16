import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Test;

import static org.hamcrest.Matchers.equalTo;

public class CourierCreationTests {
    Courier courier = new Courier();
    BaseHTTPClient client = new BaseHTTPClient();
    boolean expectedOkValue = true;
    String expectedSameLoginCourierCreationErrorMessage = "Этот логин уже используется";

    @After
    public void CourierCreationTestDataDelete() {
        client.deleteCourier(courier);
    }

    @Test
    @DisplayName("Тест метода создания курьера, проверка возвращаемого кода и тела ответа")
    @Description("Метод создания курьера возвращает код 201 и сообщение \"ok\": true")
    public void isCreateCourierReturnsStatusCode200AndOkValueIsTrueTest() {
        Response response = client.doPostRequest(client.getCreateClientAPIMethod(), courier.getRegisterRequestBody());
        response.then().assertThat().statusCode(201).and().body("ok", equalTo(expectedOkValue));
    }

    @Test
    @DisplayName("Негативный тест метода регистрации курьера, регистрация курьера с повторным использованием занятого логина")
    @Description("Повторное использование логина при создании курьера невозможно, при попытке возвращается код 409 и сообщение \"message\": \"Этот логин уже используется\"")
    public void isCreateCourierWithSameLoginForbiddenAndReturnsStatusCode409AndErrorMessageTest() {
        client.doPostRequest(client.getCreateClientAPIMethod(), courier.getRegisterRequestBody());
        Response response = client.doPostRequest(client.getCreateClientAPIMethod(), courier.getRegisterRequestBody());
        response.then().assertThat().statusCode(409).and().body("message", equalTo(expectedSameLoginCourierCreationErrorMessage));
    }
}
