package org.sample.devops.service.authentification.domain;

import org.sample.devops.service.authentification.db.User;
import org.sample.devops.service.authentification.db.UsersDB;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthenticationService {

    private final UsersDB userDB;

    public AuthenticationService(UsersDB userDB) {
        this.userDB = userDB;
    }

    public Optional<User> authenticate(String mail, String password) {
        Optional<User> user = this.userDB.getUserByMail(mail);
        if (user.isPresent()) {
            if (user.get().getPassword().equals(password)) {
                return user;
            }
        }
        return Optional.empty();
    }
}