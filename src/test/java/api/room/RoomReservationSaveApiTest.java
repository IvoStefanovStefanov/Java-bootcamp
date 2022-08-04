package api.room;

import api.user.JsonResourcesReader;
import api.user.JwtTokenGenerator;
import io.restassured.http.ContentType;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.springframework.http.HttpStatus;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

@RunWith(JUnit4.class)
public class RoomReservationSaveApiTest {

    private final String URI = "/rooms/{id}/reservations";

    private final static JwtTokenGenerator jwtTokenGenerator = new JwtTokenGenerator();


    private static final JsonResourcesReader jsonResourcesReader = new JsonResourcesReader();

    private static int saveRoomId;

    @BeforeClass
    public static void createRoom() {
        String jwt = jwtTokenGenerator.generateAdminToken();
        String room = jsonResourcesReader.readFile("classpath:room.json");

        saveRoomId = given()
                .header("Authorization", "Bearer " + jwt)
                .body(room)
                .contentType(ContentType.JSON)
                .when()
                .post("/rooms")
                .then()
                .assertThat()
                .statusCode(HttpStatus.CREATED.value())
                .extract()
                .path("id");
    }

    @Test
    public void saveRoomReservationShouldReturnCreated() {

        String jwt = jwtTokenGenerator.generateAdminToken();
        String reservation = jsonResourcesReader.readFile("classpath:roomReservation.json");



        given()
                .header("Authorization", "Bearer " + jwt)
                .pathParams("id", saveRoomId)
                .contentType(ContentType.JSON)
                .body(reservation)
                .when()
                .post(URI)
                .then()
                .statusCode(HttpStatus.CREATED.value())
                .body("id", notNullValue(),
                        "room", is("probna staq2"),
                        "start_date", is("2022-06-02"),
                        "end_date", is("2022-06-03"),
                        "days", is(1),
                        "adults", is(2),
                        "kids", is(0),
                        "price", is(51f)
                );
    }

    @Test
    public void saveRoomReservationWithoutTokenShouldReturnUnauthorized() {

        given().pathParams("id", saveRoomId)
                .when()
                .post(URI)
                .then()
                .assertThat()
                .statusCode(HttpStatus.UNAUTHORIZED.value());
    }

    @Test
    public void saveRoomReservationByIdWithUserTokenShouldReturnForbidden() {
        String jwt = jwtTokenGenerator.generateUserToken();
        String reservation = jsonResourcesReader.readFile("classpath:roomReservation.json");

        given()
                .header("Authorization", "Bearer " + jwt)
                .pathParams("id", saveRoomId)
                .contentType(ContentType.JSON)
                .body(reservation)
                .when()
                .post(URI)
                .then()
                .assertThat()
                .statusCode(HttpStatus.FORBIDDEN.value());
    }

    @Test
    public void saveRoomReservationWithEmptyBodyMustReturnBadRequest() {
        String jwt = jwtTokenGenerator.generateAdminToken();
        String reservation = jsonResourcesReader.readFile("classpath:emptyRoomReservation.json");

        given()
                .header("Authorization", "Bearer " + jwt)
                .pathParams("id", saveRoomId)
                .contentType(ContentType.JSON)
                .body(reservation)
                .when()
                .post(URI)
                .then()
                .assertThat()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    public void saveReservationOnNonExistingRoomWithTokenShouldReturnNotFound() {
        String jwt = jwtTokenGenerator.generateAdminToken();
        String reservation = jsonResourcesReader.readFile("classpath:roomReservation.json");

        given()
                .header("Authorization", "Bearer " + jwt)
                .pathParams("id", (saveRoomId + 1))
                .contentType(ContentType.JSON)
                .body(reservation)
                .when()
                .post(URI)
                .then()
                .assertThat()
                .statusCode(HttpStatus.NOT_FOUND.value());
    }

    @AfterClass
    public static void deleteRoomAndReservation() {
        String jwt = jwtTokenGenerator.generateAdminToken();

        given()
                .header("Authorization", "Bearer " + jwt)
                .pathParams("id", saveRoomId)
                .when()
                .delete("/rooms/{id}")
                .then()
                .assertThat()
                .statusCode(HttpStatus.NO_CONTENT.value());
    }
}
