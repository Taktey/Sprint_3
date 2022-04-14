import org.apache.commons.lang3.RandomStringUtils;

public class Courier {
    private String courierLogin = RandomStringUtils.randomAlphabetic(10);
    private String courierPassword = RandomStringUtils.randomAlphabetic(10);
    private String courierFirstName = RandomStringUtils.randomAlphabetic(10);
    private String registerRequestBody;
    private String loginRequestBody;


    public Courier() {
        createRequestBody();
    }

    public Courier(String courierLogin, String courierPassword) {
        this.courierLogin = courierLogin;
        this.courierPassword = courierPassword;
        this.courierFirstName = "wqer";
        createRequestBody();
    }

    public Courier(String courierLogin, String courierPassword, String courierFirstName) {
        this.courierLogin = courierLogin;
        this.courierPassword = courierPassword;
        this.courierFirstName = courierFirstName;
        createRequestBody();
    }

    public void createRequestBody() {
        registerRequestBody = "{\"login\":\"" + courierLogin + "\","
                + "\"password\":\"" + courierPassword + "\","
                + "\"firstName\":\"" + courierFirstName + "\"}";
        loginRequestBody = "{\"login\":\"" + courierLogin + "\","
                + "\"password\":\"" + courierPassword + "\"}";
    }

    public String getCourierLogin() {
        return courierLogin;
    }

    public String getCourierPassword() {
        return courierPassword;
    }

    public String getCourierFirstName() {
        return courierFirstName;
    }

    public String getRegisterRequestBody() {
        return registerRequestBody;
    }

    public String getLoginRequestBody() {
        return loginRequestBody;
    }

    public void setCourierLogin(String courierLogin) {
        this.courierLogin = courierLogin;
        createRequestBody();
    }

    public void setCourierPassword(String courierPassword) {
        this.courierPassword = courierPassword;
        createRequestBody();
    }
}
