import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.List;

import static org.hamcrest.Matchers.any;

@RunWith(Parameterized.class)
public class OrderCreationTest {
    private List<String> color;
    private int expectedStatusCode = 201;

    public OrderCreationTest(List<String> color) {
        this.color = color;
    }

    @Parameterized.Parameters(name = "Тестовые данные:{0}")
    public static Object[][] getData() {
        return new Object[][]{
                {List.of("GRAY")},
                {List.of("BLACK")},
                {List.of("")},
                {List.of("BLACK", "GREY")},
        };
    }

    @Test
    @DisplayName("Тест метода создания заказа, с различным значением цвета, проверка статус-кода и тела ответа")
    @Description("Заказ может быть создан с передачей цвета BLACK, GREY, обоих одновременно или ни одного из них, возвращается код 201 и сообщение с номером заказа (track)")
    public void isOrderCanBeCreatedWithBlackGreyAnyOtherOrNoneColorAndReturnsExpectedStatusCodeTest() {
        Order order = new Order(color);
        BaseHTTPClient client = new BaseHTTPClient();
        Response response = client.doPostRequestOrderBody(client.getCreateOrderAPIMethod(), order);
        response.then().assertThat().statusCode(expectedStatusCode)
                .and().body("track", any(Integer.class));
        String track = response.then().extract().response().body().path("[0].track");
        client.cancelOrder(track);
    }
}
