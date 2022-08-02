package api.user;

import io.restassured.http.ContentType;
import org.junit.AfterClass;
import org.junit.Test;
import org.springframework.http.HttpStatus;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

public class UserApiSaveTests {

    private final String URI = "/users";

    private final static JwtTokenGenerator jwtTokenGenerator = new JwtTokenGenerator();

    private final JsonResourcesReader jsonResourcesReader = new JsonResourcesReader();

    private static int savedId;

    @Test
    public void saveUserShouldReturnCreated() {

        String user = jsonResourcesReader.readFile("classpath:user.json");

        savedId = given()
                .contentType(ContentType.JSON)
                .body(user)
                .when()
                .post(URI)
                .then()
                .statusCode(HttpStatus.CREATED.value())
                .body("id", notNullValue(),
                        "email", is("new_test@abv.bg"),
                        "name", is("Viktor"),
                        "surname", is("Todorov"),
                        "phone", is("0987575757"),
                        "roles", notNullValue(),
                        "created", notNullValue())
                .extract()
                .path("id");
    }

    @Test
    public void saveUsersWithEmptyBodyShouldReturnBadRequest() {

        String user = jsonResourcesReader.readFile("classpath:user.json");

        given()
                .contentType(ContentType.JSON)
                .body(user)
                .when()
                .post(URI)
                .then()
                .assertThat()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @AfterClass
    public static void deleteUser() {
        String jwt = jwtTokenGenerator.generateAdminToken();

        given()
                .header("Authorization", "Bearer " + jwt)
                .pathParams("id", savedId)
                .when()
                .delete("/users/{id}")
                .then()
                .assertThat()
                .statusCode(HttpStatus.NO_CONTENT.value());
    }
}
