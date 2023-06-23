package pt.ua.deti.store.security;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;
import pt.ua.deti.store.database.UserRepository;


@Component
public class JwtFilter {
    private final UserRepository repository;
    private final HttpServletRequest request;

    public JwtFilter(UserRepository repository, HttpServletRequest request) {
        this.repository = repository;
        this.request = request;
    }

    public boolean filter() {
        if (request.getHeader("Authorization") == null || !request.getHeader("Authorization").startsWith("Bearer ")) {
            return false;
        }

        String token = request.getHeader("Authorization").replace("Bearer ", "");

        if (!Jwt.validate(token)) {
            return false;
        }

        String user = Jwt.getSubject(token);
        if (user == null) {
            return false;
        }

        return repository.existsByEmail(user);
    }
}
