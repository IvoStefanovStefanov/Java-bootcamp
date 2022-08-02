package api.car;

import api.user.JsonResourcesReader;
import api.user.JwtTokenGenerator;
import io.restassured.http.ContentType;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.http.HttpStatus;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.notNullValue;

public class CarApiDeleteTests {

    private final String URI = "/cars/{id}";

    private final static JwtTokenGenerator jwtTokenGenerator = new JwtTokenGenerator();

    private final static JsonResourcesReader jsonResourcesReader = new JsonResourcesReader();

    private static int savedId;

    @BeforeClass
    public static void createCar(){
        String jwt = jwtTokenGenerator.generateAdminToken();
        String car = jsonResourcesReader.readFile("classpath:car.json");

        savedId = given()
                .header("Authorization", "Bearer " + jwt)
                .body(car)
                .contentType(ContentType.JSON)
                .when()
                .post("/cars")
                .then()
                .assertThat()
                .statusCode(HttpStatus.CREATED.value())
                .extract()
                .path("id");
    }

    @Test
    public void deleteCarByIdWithoutTokenShouldReturnUnauthorized() {

        given().pathParams("id", savedId)
                .when()
                .delete(URI)
                .then()
                .assertThat()
                .statusCode(HttpStatus.UNAUTHORIZED.value())
                .body("message", notNullValue());
    }

    @Test
    public void deleteCarByIdWithUserTokenShouldReturnForbidden() {
        String jwt = jwtTokenGenerator.generateUserToken();

        given()
                .header("Authorization", "Bearer " + jwt)
                .pathParams("id", savedId)
                .when()
                .delete(URI)
                .then()
                .assertThat()
                .statusCode(HttpStatus.FORBIDDEN.value())
                .body("message", notNullValue());
    }

    @Test
    public void deleteCarWithNonExistingIdShouldReturnNotFound() {
        String jwt = jwtTokenGenerator.generateAdminToken();

        given()
                .header("Authorization", "Bearer " + jwt)
                .pathParams("id", (savedId + 1))
                .when()
                .delete(URI)
                .then()
                .assertThat()
                .statusCode(HttpStatus.NOT_FOUND.value())
                .body("message", notNullValue());
    }

    @Test
    public void deleteUsersShouldReturnNonContent() {
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
