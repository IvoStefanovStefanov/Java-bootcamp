package api.user;

import io.restassured.http.ContentType;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.http.HttpStatus;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.equalTo;

public class UserApiFindByIdTests {

    private final static String URI = "/users/{id}";

    private final static JwtTokenGenerator jwtTokenGenerator = new JwtTokenGenerator();

    private final static JsonResourcesReader jsonResourcesReader = new JsonResourcesReader();

    private static int savedId;

    @BeforeClass
    public static void createUser() {

        String user = jsonResourcesReader.readFile("classpath:user.json");

        savedId = given()
                .contentType(ContentType.JSON)
                .body(user)
                .when()
                .post("/users")
                .then()
                .assertThat()
                .statusCode(HttpStatus.CREATED.value())
                .extract()
                .path("id");
    }

    @Test
    public void getUserByIdWithoutTokenShouldReturnUnauthorized() {

        given().pathParams("id", savedId)
                .when()
                .get(URI)
                .then()
                .assertThat()
                .statusCode(HttpStatus.UNAUTHORIZED.value());
    }

    @Test
    public void getUserByIdWithAdminTokenShouldReturnUser() {
        String jwt = jwtTokenGenerator.generateAdminToken();
        given()
                .header("Authorization", "Bearer " + jwt)
                .pathParams("id", savedId)
                .when()
                .get(URI)
                .then()
                .assertThat()
                .statusCode(HttpStatus.OK.value())
                .body("id", equalTo(savedId),
                        "email", equalTo("new_test@abv.bg"),
                        "name", equalTo("Viktor"),
                        "surname", equalTo("Todorov"),
                        "phone", equalTo("0987575757"),
                        "roles", containsInAnyOrder("client"));
    }

    @Test
    public void getUserByIdWithUserTokenShouldReturnForbidden() {
        String jwt = jwtTokenGenerator.generateUserToken();
        given()
                .header("Authorization", "Bearer " + jwt)
                .pathParams("id", savedId)
                .when()
                .get(URI)
                .then()
                .assertThat()
                .statusCode(HttpStatus.FORBIDDEN.value());
    }

    @Test
    public void getNonExistingUserByIdWithTokenShouldReturnNotFound() {
        String jwt = jwtTokenGenerator.generateAdminToken();
        given()
                .header("Authorization", "Bearer " + jwt)
                .pathParams("id", (savedId + 1))
                .when()
                .get(URI)
                .then()
                .assertThat()
                .statusCode(HttpStatus.NOT_FOUND.value());
    }

    @AfterClass
    public static void deleteUser() {
        String jwt = jwtTokenGenerator.generateAdminToken();

        given()
                .header("Authorization", "Bearer " + jwt)
                .pathParams("id", savedId)
                .when()
                .delete(URI)
                .then()
                .assertThat()
                .statusCode(HttpStatus.NO_CONTENT.value());
    }
}
