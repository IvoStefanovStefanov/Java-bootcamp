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

@RunWith(JUnit4.class)
public class RoomReservationDeleteApiTest {
    private final String URI = "/rooms/{id}/reservations/{rid}";

    private final static JwtTokenGenerator jwtTokenGenerator = new JwtTokenGenerator();


    private static final JsonResourcesReader jsonResourcesReader = new JsonResourcesReader();

    private static int saveRoomId;
    private static int saveReservationId;

    @BeforeClass
    public static void createRoomAndReservation() {
        String jwt = jwtTokenGenerator.generateAdminToken();
        String room = jsonResourcesReader.readFile("classpath:room.json");
        String reservation = jsonResourcesReader.readFile("classpath:roomReservation.json");

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

        saveReservationId = given()
                .header("Authorization", "Bearer " + jwt)
                .pathParams("id", saveRoomId)
                .contentType(ContentType.JSON)
                .body(reservation)
                .when()
                .post("/rooms/{id}/reservations")
                .then()
                .statusCode(HttpStatus.CREATED.value())
                .extract()
                .path("id");
    }

    @Test
    public void deleteReservationWithoutTokenShouldReturnUnauthorized() {

        given()
                .pathParams("id", saveRoomId)
                .pathParams("rid", saveReservationId)
                .when()
                .delete(URI)
                .then()
                .assertThat()
                .statusCode(HttpStatus.UNAUTHORIZED.value());
    }

    @Test
    public void deleteRoomReservationByIdWithUserTokenShouldReturnForbidden() {
        String jwt = jwtTokenGenerator.generateUserToken();
        String reservation = jsonResourcesReader.readFile("classpath:roomReservation.json");

        given()
                .header("Authorization", "Bearer " + jwt)
                .pathParams("id", saveRoomId)
                .pathParams("rid", saveReservationId)
                .contentType(ContentType.JSON)
                .body(reservation)
                .when()
                .delete(URI)
                .then()
                .assertThat()
                .statusCode(HttpStatus.FORBIDDEN.value());
    }

    @Test
    public void deleteNonExistingReservationByIdWithTokenShouldReturnNotFound() {
        String jwt = jwtTokenGenerator.generateAdminToken();
        given()
                .header("Authorization", "Bearer " + jwt)
                .pathParams("id", saveRoomId)
                .pathParams("rid", (saveReservationId + 1))
                .when()
                .delete(URI)
                .then()
                .assertThat()
                .statusCode(HttpStatus.NOT_FOUND.value());
    }

    @Test
    public void deleteReservationByIdShouldReturnNoContent() {
        String jwt = jwtTokenGenerator.generateAdminToken();

        given()
                .header("Authorization", "Bearer " + jwt)
                .pathParams("id", saveRoomId)
                .pathParams("rid", saveReservationId)
                .when()
                .delete(URI)
                .then()
                .assertThat()
                .statusCode(HttpStatus.NO_CONTENT.value());
    }

    @AfterClass
    public static void deleteRoom() {
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
