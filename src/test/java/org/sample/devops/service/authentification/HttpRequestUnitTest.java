package org.sample.devops.service.authentification;

import org.junit.jupiter.api.Test;
import org.sample.devops.service.authentification.dto.AuthentificationRequest;
import org.sample.devops.service.authentification.dto.AuthentificationResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.resttestclient.TestRestTemplate;
import org.springframework.boot.resttestclient.autoconfigure.AutoConfigureTestRestTemplate;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.net.URI;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestRestTemplate
public class HttpRequestUnitTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void authentication_should_return_not_found_error_when_incorrect_password() throws Exception {
        final String baseUrl = "http://localhost:" + port + "/api/authenticate";
        URI uri = new URI(baseUrl);
        var request = new AuthentificationRequest("user", "password");
        ResponseEntity<AuthentificationResponse> response = this.restTemplate.postForEntity(uri, request, AuthentificationResponse.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    void authentication_should_return_user_when_correct_mail_and_password() throws Exception {
        final String baseUrl = "http://localhost:" + port + "/api/authenticate";
        URI uri = new URI(baseUrl);
        AuthentificationRequest request = new AuthentificationRequest("user1@mail.com", "password1");
        ResponseEntity<AuthentificationResponse> response = this.restTemplate.postForEntity(uri, request, AuthentificationResponse.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(new AuthentificationResponse("user1@mail.com", "User1FirstName", "User1LastName"));
    }
}