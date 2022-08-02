package api.user;

import com.academy.javabootcamp.Jwt.JwtRequest;
import com.academy.javabootcamp.Jwt.JwtResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class JwtTokenGenerator {

    private static final String TOKEN_URL = "http://localhost:8080/users/token";

    private static final JwtRequest ADMIN_USER = new JwtRequest("test@abv.bg","123456");
    private static final JwtRequest USER = new JwtRequest("emo@abv.bg","654321");

    private final RestTemplate restTemplate = new RestTemplate();


    public String generateUserToken(){
        ResponseEntity<JwtResponse> jwtResponseEntity =
                restTemplate.postForEntity(TOKEN_URL, USER, JwtResponse.class);

        return  jwtResponseEntity.getBody().getToken();
    }

    public String generateAdminToken(){
        ResponseEntity<JwtResponse> jwtResponseEntity =
                restTemplate.postForEntity(TOKEN_URL, ADMIN_USER, JwtResponse.class);

        return  jwtResponseEntity.getBody().getToken();
    }

}
