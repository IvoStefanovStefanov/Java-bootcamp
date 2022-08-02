package api.room;

import api.user.JwtTokenGenerator;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.springframework.http.HttpStatus;

import static io.restassured.RestAssured.given;

@RunWith(JUnit4.class)
public class RoomApiFindByIdTest {

    private static JwtTokenGenerator jwtTokenGenerator = new JwtTokenGenerator();

    @Test
    public void getRoomFindByIdWithTokenMustReturnOk() {
        String jwt = jwtTokenGenerator.generateUserToken();
        given()
                .header("Authorization", "Bearer " + jwt)
                .pathParams("id", 207)
                .when()
                .get("rooms/{id}")
                .then()
                .assertThat()
                .statusCode(HttpStatus.OK.value());
    }

    @Test
    public void getRoomFindByIdWithoutTokenMustReturnUnauthorized() {
        given()
                .pathParam("id", 159)
                .when()
                .get("/rooms/{id}")
                .then()
                .assertThat()
                .statusCode(HttpStatus.UNAUTHORIZED.value());
    }

    @Test
    public void getRoomFindByIdWithTokenMustReturnNotFound() {
        String jwt = jwtTokenGenerator.generateUserToken();
        given()
                .header("Authorization", "Bearer " + jwt)
                .pathParams("id", 194)
                .when()
                .get("rooms/{id}")
                .then()
                .assertThat()
                .statusCode(HttpStatus.NOT_FOUND.value());
    }

}
