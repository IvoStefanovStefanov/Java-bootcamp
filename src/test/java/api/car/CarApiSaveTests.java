package api.car;

import api.user.JsonResourcesReader;
import api.user.JwtTokenGenerator;
import io.restassured.http.ContentType;
import org.junit.AfterClass;
import org.junit.Test;
import org.springframework.http.HttpStatus;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

public class CarApiSaveTests {

    private final String URI = "/cars";

    private final static JwtTokenGenerator jwtTokenGenerator = new JwtTokenGenerator();

    private final JsonResourcesReader jsonResourcesReader = new JsonResourcesReader();

    private static int savedId;

    @Test
    public void saveCarShouldReturnCreated() {
        String jwt = jwtTokenGenerator.generateAdminToken();
        String car = jsonResourcesReader.readFile("classpath:car.json");

        savedId = given()
                .header("Authorization", "Bearer " + jwt)
                .contentType(ContentType.JSON)
                .body(car)
                .when()
                .post(URI)
                .then()
                .statusCode(HttpStatus.CREATED.value())
                .body("id", notNullValue(),
                        "category", is("Van"),
                        "brand", is("VW"),
                        "model", is("Transporter"),
                        "image", is("https://images.app.goo.gl/newImage.jpg"),
                        "images", notNullValue(),
                        "seats",is(8),
                        "price",is(600.0f))
                .extract()
                .path("id");
    }

    @Test
    public void saveCarWithoutTokenShouldReturnUnauthorized() {

        given()
                .when()
                .post(URI)
                .then()
                .assertThat()
                .statusCode(HttpStatus.UNAUTHORIZED.value())
                .body("message", notNullValue());
    }

    @Test
    public void saveCarWithUserTokenShouldReturnForbidden() {
        String jwt = jwtTokenGenerator.generateUserToken();
        String car = jsonResourcesReader.readFile("classpath:car.json");

        given()
                .header("Authorization", "Bearer " + jwt)
                .contentType(ContentType.JSON)
                .body(car)
                .when()
                .post(URI)
                .then()
                .assertThat()
                .statusCode(HttpStatus.FORBIDDEN.value())
                .body("message", notNullValue());
    }

    @Test
    public void saveCarWithEmptyBodyShouldReturnBadRequest() {
        String jwt = jwtTokenGenerator.generateAdminToken();
        String car = jsonResourcesReader.readFile("classpath:emptyCar.json");

        given()
                .header("Authorization", "Bearer " + jwt)
                .contentType(ContentType.JSON)
                .body(car)
                .when()
                .post(URI)
                .then()
                .assertThat()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @AfterClass
    public static void deleteCar() {
        String jwt = jwtTokenGenerator.generateAdminToken();

        given()
                .header("Authorization", "Bearer " + jwt)
                .pathParams("id", savedId)
                .when()
                .delete("/cars/{id}")
                .then()
                .assertThat()
                .statusCode(HttpStatus.NO_CONTENT.value());
    }
}
