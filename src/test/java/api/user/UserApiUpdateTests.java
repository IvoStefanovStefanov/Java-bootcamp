package api.user;

import io.restassured.http.ContentType;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.http.HttpStatus;

import static io.restassured.RestAssured.given;

public class UserApiUpdateTests {

    private final static String URI = "/users/{id}";

    private final static JwtTokenGenerator jwtTokenGenerator = new JwtTokenGenerator();

    private final static JsonResourcesReader jsonResourcesReader = new JsonResourcesReader();

    private static int savedId;

    @BeforeClass
    public static void createUser() {
        String user = jsonResourcesReader.readFile("classpath:user.json");

        savedId = given()
                .body(user)
                .contentType(ContentType.JSON)
                .when()
                .post("/users")
                .then()
                .assertThat()
                .statusCode(HttpStatus.CREATED.value())
                .extract()
                .path("id");
    }

    @Test
    public void updateUserByIdWithoutTokenShouldReturnUnauthorized() {

        given().pathParams("id", savedId)
                .when()
                .put(URI)
                .then()
                .assertThat()
                .statusCode(HttpStatus.UNAUTHORIZED.value());
    }

    @Test
    public void updateUsersShouldReturnOk() {
        String jwt = jwtTokenGenerator.generateAdminToken();
        String user = jsonResourcesReader.readFile("classpath:user.json");

        given()
                .header("Authorization", "Bearer " + jwt)
                .pathParams("id", savedId)
                .contentType(ContentType.JSON)
                .body(user)
                .when()
                .put(URI)
                .then()
                .assertThat()
                .statusCode(HttpStatus.OK.value());
    }

    @Test
    public void updateUserByIdWithUserTokenShouldReturnForbidden() {
        String jwt = jwtTokenGenerator.generateUserToken();
        String user = jsonResourcesReader.readFile("classpath:user.json");

        given()
                .header("Authorization", "Bearer " + jwt)
                .pathParams("id", savedId)
                .contentType(ContentType.JSON)
                .body(user)
                .when()
                .put(URI)
                .then()
                .assertThat()
                .statusCode(HttpStatus.FORBIDDEN.value());
    }

    @Test
    public void updateNonExistingUserByIdWithTokenShouldReturnNotFound() {
        String jwt = jwtTokenGenerator.generateAdminToken();
        String user = jsonResourcesReader.readFile("classpath:updateUser.json");

        given()
                .header("Authorization", "Bearer " + jwt)
                .pathParams("id", (savedId + 1))
                .contentType(ContentType.JSON)
                .body(user)
                .when()
                .put(URI)
                .then()
                .assertThat()
                .statusCode(HttpStatus.NOT_FOUND.value());
    }

    @Test
    public void updateUsersWithEmptyBodyShouldReturnBadRequest() {
        String jwt = jwtTokenGenerator.generateAdminToken();
        String user = jsonResourcesReader.readFile("classpath:emptyUser.json");

        given()
                .header("Authorization", "Bearer " + jwt)
                .pathParams("id", savedId)
                .contentType(ContentType.JSON)
                .body(user)
                .when()
                .put(URI)
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
                .delete(URI)
                .then()
                .assertThat()
                .statusCode(HttpStatus.NO_CONTENT.value());
    }
}
