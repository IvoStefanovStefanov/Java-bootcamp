package api.user;

import io.restassured.http.ContentType;
import org.junit.Test;
import org.springframework.http.HttpStatus;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

public class UserApiJwtTokenTests {

    private final String URI = "/users/token";

    private final JsonResourcesReader jsonResourcesReader = new JsonResourcesReader();

    @Test
    public void createJwtTokenShouldReturnOk() {

        String user = jsonResourcesReader.readFile("classpath:jwt.json");

        given()
                .contentType(ContentType.JSON)
                .body(user)
                .when()
                .post(URI)
                .then()
                .statusCode(HttpStatus.OK.value())
                .body("token", notNullValue(),
                        "user.email", is("test@abv.bg"),
                        "user.name", notNullValue(),
                        "user.surname", notNullValue(),
                        "user.phone", notNullValue(),
                        "user.roles", notNullValue(),
                        "user.created", notNullValue());
    }

    @Test
    public void createJwtTokenWithWrongInputShouldReturnBadRequest() {

        String user = jsonResourcesReader.readFile("classpath:jwtWrongInput.json");

        given()
                .contentType(ContentType.JSON)
                .body(user)
                .when()
                .post(URI)
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .body("message", notNullValue(),
                        "errors", notNullValue());
    }
}

