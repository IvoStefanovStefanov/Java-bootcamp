package api.car;

import api.user.JsonResourcesReader;
import api.user.JwtTokenGenerator;
import io.restassured.http.ContentType;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.http.HttpStatus;

import static io.restassured.RestAssured.given;

public class CarTransferSummarizeApiTests {
    private final String URI = "/cars/{id}/summarize";

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
    public void getAllCarTransfersWithoutTokenShouldReturnUnauthorized() {

        given()
                .pathParams("id", savedId)
                .when()
                .post(URI)
                .then()
                .assertThat()
                .statusCode(HttpStatus.UNAUTHORIZED.value());
    }

    @Test
    public void getAllCarTransfersWithAdminTokenShouldReturnOK() {
        String jwt = jwtTokenGenerator.generateAdminToken();
        String carTransfer = jsonResourcesReader.readFile("classpath:carTransfer.json");

        given()
                .pathParams("id", savedId)
                .header("Authorization", "Bearer " + jwt)
                .contentType(ContentType.JSON)
                .body(carTransfer)
                .when()
                .post(URI)
                .then()
                .assertThat()
                .statusCode(HttpStatus.OK.value());
    }

    @Test
    public void getAllCarTransfersWithWrongDataShouldReturnBadRequest() {
        String jwt = jwtTokenGenerator.generateUserToken();
        String carTransfer = jsonResourcesReader.readFile("classpath:emptyCarTransfer.json");

        given()
                .pathParams("id", savedId)
                .header("Authorization", "Bearer " + jwt)
                .contentType(ContentType.JSON)
                .body(carTransfer)
                .when()
                .post(URI)
                .then()
                .assertThat()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    public void getAllTransfersOfNonExistingCarShouldNotFound() {
        String jwt = jwtTokenGenerator.generateAdminToken();
        String carTransfer = jsonResourcesReader.readFile("classpath:carTransfer.json");

        given()
                .pathParams("id", (savedId+1))
                .header("Authorization", "Bearer " + jwt)
                .contentType(ContentType.JSON)
                .body(carTransfer)
                .when()
                .post(URI)
                .then()
                .assertThat()
                .statusCode(HttpStatus.NOT_FOUND.value());
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

