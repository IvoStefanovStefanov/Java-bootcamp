package api.room;

import api.user.JwtTokenGenerator;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.springframework.http.HttpStatus;

import static io.restassured.RestAssured.given;


@RunWith(JUnit4.class)
public class RoomApiDeleteByIdTest {

    private final JwtTokenGenerator jwtTokenGenerator = new JwtTokenGenerator();

    @Test
    public void deleteRoomByIdWithTokenMustReturnNoContent() {
        String jwt = jwtTokenGenerator.generateAdminToken();
        given()
                .header("Authorization", "Bearer " + jwt)
                .pathParams("id", 222)
                .when()
                .delete("/rooms/{id}")
                .then()
                .assertThat()
                .statusCode(HttpStatus.NO_CONTENT.value());

    }

    @Test
    public void deleteRoomByIdWithoutTokenMustReturnUnauthorized() {
        given()
                .pathParams("id", 400)
                .when()
                .delete("/rooms/{id}")
                .then()
                .assertThat()
                .statusCode(HttpStatus.UNAUTHORIZED.value());


    }

    @Test
    public void deleteRoomByIdWithTokenMustReturnForbidden() {
        String jwt = jwtTokenGenerator.generateUserToken();
        given()
                .header("Authorization", "Bearer " + jwt)
                .pathParams("id", 220)
                .when()
                .delete("/rooms/{id}")
                .then()
                .assertThat()
                .statusCode(HttpStatus.FORBIDDEN.value());

    }

    @Test
    public void deleteRoomByIdWithTokenMustReturnNotFound() {
        String jwt = jwtTokenGenerator.generateAdminToken();
        given()
                .header("Authorization", "Bearer " + jwt)
                .pathParams("id", 40000)
                .when()
                .delete("/rooms/{id}")
                .then()
                .assertThat()
                .statusCode(HttpStatus.NOT_FOUND.value());
    }
}
