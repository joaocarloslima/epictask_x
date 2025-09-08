package br.com.fiap.epictaskx.user;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
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

    public void addScore(User user, int score) {
        user.setScore(user.getScore() + score);
        userRepository.save(user);
    }
}
