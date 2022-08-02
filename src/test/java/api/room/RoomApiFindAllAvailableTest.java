package api.room;

import api.user.JwtTokenGenerator;
import io.restassured.http.ContentType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.springframework.http.HttpStatus;

import static io.restassured.RestAssured.given;


@RunWith(JUnit4.class)
public class RoomApiFindAllAvailableTest {

    private final static JwtTokenGenerator jwtTokenGenerator = new JwtTokenGenerator();

    @Test
    public void getAllAvailableRoomWithTokenMustReturnAllRooms() {
        String jwt = jwtTokenGenerator.generateAdminToken();
        given()
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + jwt)
                .when()
                .get("/rooms")
                .then()
                .assertThat()
                .statusCode(HttpStatus.OK.value());

    }

    @Test
    public void getAllAvailableRoomMustReturnNotFound() {
        String jwt = jwtTokenGenerator.generateAdminToken();
        given()
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + jwt)
                .when()
                .get("/room")
                .then()
                .assertThat()
                .statusCode(HttpStatus.NOT_FOUND.value());

    }
}
