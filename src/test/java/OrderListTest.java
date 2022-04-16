import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.hamcrest.Matchers.notNullValue;

public class OrderListTest {
    BaseHTTPClient client;
    String orderTrack;
    int expectedStatusCode = 200;

    @Before
    public void OrderListPreparations() {
        Order order = new Order(List.of("BLACK"));
        BaseHTTPClient client = new BaseHTTPClient();
        Response response = client.doPostRequestOrderBody(client.getCreateOrderAPIMethod(), order);
        orderTrack = response.then().extract().response().body().path("[0].track");
        this.client = client;
    }

    @After
    public void cancelTestOrder() {
        client.cancelOrder(orderTrack);
    }

    @Test
    @DisplayName("Тест метода запроса списка заказов, проверка кода и тела ответа")
    @Description("Запрос списка заказов возвращает код 200 и список заказов в качестве значения для \"orders\"")
    public void isOrderListRequestReturnsExpectedStatusCodeTest() {
        Response response = client.doGetRequest(client.getOrderListAPIMethod());
        response.then().assertThat().statusCode(expectedStatusCode)
                .and().body("orders", notNullValue());
    }
}
