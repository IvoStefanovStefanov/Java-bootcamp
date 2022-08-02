package api.room;

import api.user.JwtTokenGenerator;
import com.academy.javabootcamp.dto.RoomSaveRequest;
import io.restassured.http.ContentType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;

@RunWith(JUnit4.class)
public class RoomApiSaveTest {

    private final static JwtTokenGenerator jwtTokenGenerator = new JwtTokenGenerator();

    private final static RoomSaveRequest roomSaveRequest = new RoomSaveRequest();

    @Test
    public void saveRoomHaveToReturnBadRequest() {
        String jwt = jwtTokenGenerator.generateAdminToken();

        given()
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + jwt)
                .body(roomSaveRequest)
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
                .body(roomSaveRequest)
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
                .body(roomSaveRequest)
                .when()
                .post("/rooms")
                .then()
                .assertThat()
                .statusCode(HttpStatus.FORBIDDEN.value());

    }

    @Test
    public void saveRoomHaveToReturnCreated() {
        String jwt = jwtTokenGenerator.generateAdminToken();

        List<String> images = new ArrayList<>();
        roomSaveRequest.setTitle("staq s kanape");
        roomSaveRequest.setImage("http://localhost:8080/rooms36.bmp");
        roomSaveRequest.setImages(images);
        roomSaveRequest.setDescription("изглед към море");
        roomSaveRequest.setFacilities("s 2 shezlonga");
        roomSaveRequest.setArea(29.99);
        roomSaveRequest.setPeople(4);
        roomSaveRequest.setPrice(222.99);
        roomSaveRequest.setCount(4);

        given()
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + jwt)
                .body(roomSaveRequest)
                .when()
                .post("rooms")
                .then()
                .assertThat()
                .statusCode(HttpStatus.CREATED.value());
    }
}




