import io.restassured.response.Response;
import static io.restassured.RestAssured.given;

public class BaseHTTPClient {
    private final String JSON = "application/json";
    private String baseUrl = "https://qa-scooter.praktikum-services.ru/";
    private String createClientAPIMethod = "api/v1/courier";
    private String loginAPIMethod = "api/v1/courier/login";
    private String deleteCourierAPIMethod = "api/v1/courier/:id";
    private String createOrderAPIMethod = "api/v1/orders";
    private String orderListAPIMethod = "api/v1/orders";
    private String orderCancelAPIMethod = "api/v1/orders/cancel";

    protected Response doGetRequest(String apiMethod) {
        return given().header("Content-type", JSON).get(baseUrl + apiMethod);
    }
    protected Response doPostRequest(String apiMethod, String requestBody) {
        return given().header("Content-type", JSON).and().body(requestBody).when().post(baseUrl + apiMethod);
    }
    protected Response doPostRequestOrderBody(String apiMethod, Order requestBody) {
        return given().header("Content-type", JSON).and().body(requestBody).when().post(baseUrl + apiMethod);
    }

    protected void deleteCourier(Courier courier) {
        String courierId = given().header("Content-type", JSON).and().body(courier.getLoginRequestBody()).when().post(baseUrl + loginAPIMethod).then().extract().response().body().path("[0].id");
        given().header("Content-type", JSON).and().body("{\"id\":" + courierId + "}").when().delete(baseUrl + deleteCourierAPIMethod);
    }
    protected void cancelOrder(String track) {
        given().header("Content-type", JSON).and().body("{\"track\":" + track + "}").when().put(baseUrl + orderCancelAPIMethod);
    }

    public String getLoginAPIMethod() {
        return loginAPIMethod;
    }

    public String getCreateClientAPIMethod() {
        return createClientAPIMethod;
    }
    public String getCreateOrderAPIMethod() {
        return createOrderAPIMethod;
    }

    public String getOrderListAPIMethod() {
        return orderListAPIMethod;
    }
}
