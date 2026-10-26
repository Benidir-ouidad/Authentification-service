package org.sample.devops.service.authentification.controller;

import org.sample.devops.service.authentification.db.User;
import org.sample.devops.service.authentification.domain.AuthenticationService;
import org.sample.devops.service.authentification.dto.AuthentificationRequest;
import org.sample.devops.service.authentification.dto.AuthentificationResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/api")
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthentificationResponse> authenticate(@RequestBody AuthentificationRequest authenticationRequest) {
        Optional<User> userOptional = authenticationService.authenticate(authenticationRequest.mail(), authenticationRequest.password());
        if (userOptional.isPresent()) {
            var user = userOptional.get();
            var authenticationResponse = new AuthentificationResponse(user.getMail(), user.getFirstname(), user.getLastname());
            return ResponseEntity.ok(authenticationResponse);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}