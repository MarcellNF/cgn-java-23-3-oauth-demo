package de.neuefische.backend.security;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @GetMapping("/me")
    public AppUser getMe() {
        var principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof DefaultOAuth2User defaultOAuth2User) {
            return AppUser.builder()
                    .id(Integer.parseInt(defaultOAuth2User.getAttributes().get("id").toString()))
                    .login(defaultOAuth2User.getAttributes().get("login").toString())
                    .avatarUrl(defaultOAuth2User.getAttributes().get("avatar_url").toString())
                    .build();
        }
        return null;
    }

}
