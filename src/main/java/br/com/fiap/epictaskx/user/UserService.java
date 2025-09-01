package br.com.fiap.epictaskx.user;

import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User register(OAuth2User principal) {
        String email = principal.getAttributes().get("email").toString();
        return userRepository
                .findByEmail(email)
                .orElseGet(
                        () -> userRepository.save(new User(principal))
                );
    }
}
