package api.room;

import api.user.JsonResourcesReader;
import api.user.JwtTokenGenerator;
import io.restassured.http.ContentType;
import org.junit.AfterClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.springframework.http.HttpStatus;

import static io.restassured.RestAssured.given;

@RunWith(JUnit4.class)
public class RoomApiSaveTest {

    private final static JwtTokenGenerator jwtTokenGenerator = new JwtTokenGenerator();

    private static final JsonResourcesReader jsonResourcesReader = new JsonResourcesReader();

    private final static String emptyRoom = jsonResourcesReader.readFile("classpath:emptyRoom.json");

    private final static String room = jsonResourcesReader.readFile("classpath:room.json");

    private static int saveRoomId;

    @Test
    public void saveRoomHaveToReturnBadRequest() {
        String jwt = jwtTokenGenerator.generateAdminToken();


        given()
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + jwt)
                .body(emptyRoom)
                .when()
                .post("/rooms")
                .then()
                .assertThat()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    public void saveRoomWithoutTokenHaveToReturnUnauthorized() {

        given()
                .contentType(ContentType.JSON)
                .body(emptyRoom)
                .when()
                .post("/rooms")
                .then()
                .assertThat()
                .statusCode(HttpStatus.UNAUTHORIZED.value());

    }

    @Test
    public void saveRoomWithClientTokenHaveToReturnForbidden() {
        String jwt = jwtTokenGenerator.generateUserToken();

        given()
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + jwt)
                .body(room)
                .when()
                .post("/rooms")
                .then()
                .assertThat()
                .statusCode(HttpStatus.FORBIDDEN.value());

    }

    @Test
    public void saveRoomHaveToReturnCreated() {
        String jwt = jwtTokenGenerator.generateAdminToken();

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




