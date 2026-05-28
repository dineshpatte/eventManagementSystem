package com.dinesh.tickets.fliters;


import com.dinesh.tickets.Domain.entities.User;
import com.dinesh.tickets.repositories.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class UserProvisioningFilter extends OncePerRequestFilter {

    private final UserRepository userRepository;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication!=null&&authentication.isAuthenticated()&&authentication.getPrincipal() instanceof Jwt jwt) {
            UUID keyCloakId = UUID.fromString(jwt.getSubject());

            System.out.println("JWT CLAIMS: " + jwt.getClaims());
            System.out.println("preferred_username: " + jwt.getClaimAsString("preferred_username"));
            System.out.println("email: " + jwt.getClaimAsString("email"));

            if (!userRepository.existsById(keyCloakId)) {

                String username = jwt.getClaimAsString("preferred_username");
                String email = jwt.getClaimAsString("email");

                if (username == null) {
                    username = jwt.getSubject(); // fallback to UUID string
                }

                if (email == null) {
                    email = "unknown@email.com";
                }

                User user = new User();
                user.setId(keyCloakId);
                user.setName(username);
                user.setEmail(email);

                userRepository.save(user);
            }
        }

        filterChain.doFilter(request,response);
    }

}
