package api.room;

import api.user.JsonResourcesReader;
import api.user.JwtTokenGenerator;
import io.restassured.http.ContentType;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.http.HttpStatus;

import static io.restassured.RestAssured.given;

public class RoomApiUpdateTests {
    private final static String URI = "/rooms/{id}";

    private final static JwtTokenGenerator jwtTokenGenerator = new JwtTokenGenerator();

    private final static JsonResourcesReader jsonResourcesReader = new JsonResourcesReader();

    private static int savedId;

    @BeforeClass
    public static void createRoom() {
        String jwt = jwtTokenGenerator.generateAdminToken();
        String room = jsonResourcesReader.readFile("classpath:room.json");

        savedId = given()
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
    public void updateRoomByIdWithoutTokenShouldReturnUnauthorized() {

        given().pathParams("id", savedId)
                .when()
                .put(URI)
                .then()
                .assertThat()
                .statusCode(HttpStatus.UNAUTHORIZED.value());
    }

    @Test
    public void updateRoomShouldReturnOk() {
        String jwt = jwtTokenGenerator.generateAdminToken();
        String room = jsonResourcesReader.readFile("classpath:updateRoom.json");

        given()
                .header("Authorization", "Bearer " + jwt)
                .pathParams("id", savedId)
                .contentType(ContentType.JSON)
                .body(room)
                .when()
                .put(URI)
                .then()
                .assertThat()
                .statusCode(HttpStatus.OK.value());
    }

    @Test
    public void updateRoomByIdWithUserTokenShouldReturnForbidden() {
        String jwt = jwtTokenGenerator.generateUserToken();
        String room = jsonResourcesReader.readFile("classpath:updateRoom.json");

        given()
                .header("Authorization", "Bearer " + jwt)
                .pathParams("id", savedId)
                .contentType(ContentType.JSON)
                .body(room)
                .when()
                .put(URI)
                .then()
                .assertThat()
                .statusCode(HttpStatus.FORBIDDEN.value());
    }

    @Test
    public void updateNonExistingRoomByIdWithTokenShouldReturnNotFound() {
        String jwt = jwtTokenGenerator.generateAdminToken();
        String room = jsonResourcesReader.readFile("classpath:updateRoom.json");

        given()
                .header("Authorization", "Bearer " + jwt)
                .pathParams("id", (savedId + 1))
                .contentType(ContentType.JSON)
                .body(room)
                .when()
                .put(URI)
                .then()
                .assertThat()
                .statusCode(HttpStatus.NOT_FOUND.value());
    }

    @Test
    public void updateRoomWithEmptyBodyShouldReturnBadRequest() {
        String jwt = jwtTokenGenerator.generateAdminToken();
        String room = jsonResourcesReader.readFile("classpath:emptyRoom.json");

        given()
                .header("Authorization", "Bearer " + jwt)
                .pathParams("id", savedId)
                .contentType(ContentType.JSON)
                .body(room)
                .when()
                .put(URI)
                .then()
                .assertThat()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @AfterClass
    public static void deleteRoom() {
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
