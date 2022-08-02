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
public class RoomReservationFindAllApiTest {

    private final String URI = "/rooms/{id}/reservations";

    private final static JwtTokenGenerator jwtTokenGenerator = new JwtTokenGenerator();


    private static final JsonResourcesReader jsonResourcesReader = new JsonResourcesReader();

    private static int saveRoomId;

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

            given()
                .header("Authorization", "Bearer " + jwt)
                .pathParams("id", saveRoomId)
                .contentType(ContentType.JSON)
                .body(reservation)
                .when()
                .post("/rooms/{id}/reservations")
                .then()
                .statusCode(HttpStatus.CREATED.value());
    }

    @Test
    public void getAllReservationsWithoutTokenShouldReturnUnauthorized() {

        given()
                .pathParams("id", saveRoomId)
                .when()
                .get(URI)
                .then()
                .assertThat()
                .statusCode(HttpStatus.UNAUTHORIZED.value());
    }

    @Test
    public void getAllReservationsWithAdminTokenShouldReturnReservations() {
        String jwt = jwtTokenGenerator.generateAdminToken();
        given()
                .header("Authorization", "Bearer " + jwt)
                .pathParams("id", saveRoomId)
                .when()
                .get(URI)
                .then()
                .assertThat()
                .statusCode(HttpStatus.OK.value());
    }

    @Test
    public void getAllReservationsWithUserTokenShouldReturnForbidden() {
        String jwt = jwtTokenGenerator.generateUserToken();
        given()
                .header("Authorization", "Bearer " + jwt)
                .pathParams("id", saveRoomId)
                .when()
                .get(URI)
                .then()
                .assertThat()
                .statusCode(HttpStatus.FORBIDDEN.value());
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
