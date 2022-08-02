package api.user;

import org.junit.Test;
import org.springframework.http.HttpStatus;

import static io.restassured.RestAssured.given;

public class UserApiFindAllTests {

    private final String URI = "/users";

    private final JwtTokenGenerator jwtTokenGenerator = new JwtTokenGenerator();

    @Test
    public void getAllUsersWithoutTokenShouldReturnUnauthorized() {

        given()
                .when()
                .get(URI)
                .then()
                .assertThat()
                .statusCode(HttpStatus.UNAUTHORIZED.value());
    }

    @Test
    public void getAllUsersWithAdminTokenShouldReturnUsers() {
        String jwt = jwtTokenGenerator.generateAdminToken();
        given()
                .header("Authorization", "Bearer " + jwt)
                .when()
                .get(URI)
                .then()
                .assertThat()
                .statusCode(HttpStatus.OK.value());
    }

    @Test
    public void getAllUsersWithUserTokenShouldReturnForbidden() {
        String jwt = jwtTokenGenerator.generateUserToken();
        given()
                .header("Authorization", "Bearer " + jwt)
                .when()
                .get(URI)
                .then()
                .assertThat()
                .statusCode(HttpStatus.FORBIDDEN.value());
    }
}
