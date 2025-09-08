package br.com.fiap.epictaskx.user;

import org.springframework.boot.Banner;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserController {

    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("ranking")
    public String ranking(Model model, @AuthenticationPrincipal OAuth2User user){
        model.addAttribute("users", userRepository.findByOrderByScoreDesc());
        model.addAttribute("user", user);
        return "ranking";
    }

}
