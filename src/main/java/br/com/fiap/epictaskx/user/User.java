package br.com.fiap.epictaskx.user;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Generated;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;

@Entity
@Data
@Table(name = "epicuser")
public class User {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String email;

    private String name;

    private String avatarUrl;

    public User(OAuth2User principal) {
        this.email = principal.getAttributes().get("email").toString();
        this.name = principal.getAttributes().get("name").toString();
        var avatarUrl = principal.getAttributes().get("picture") != null ?
                principal.getAttributes().get("picture") :
                principal.getAttributes().get("avatar_url");

        this.avatarUrl = avatarUrl.toString();
    }

    public User() {

    }
}
