package com.academy.javabootcamp.Jwt;

import com.academy.javabootcamp.model.Role;
import com.academy.javabootcamp.service.UserService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class JWTUserDetailsService implements UserDetailsService {
    private final UserService userService;

    public JWTUserDetailsService(UserService userService) {
        this.userService = userService;
    }


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        com.academy.javabootcamp.model.User foundUser = userService.findByEmail(email);
        String foundPassword = foundUser.getPassword();
        String foundEmail = foundUser.getEmail();

        if (foundEmail != null) {
            Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
            for (Role role : foundUser.getRoles()) {
                grantedAuthorities.add(new SimpleGrantedAuthority(role.getName()));
            }
            return new User(foundEmail, foundPassword, grantedAuthorities);

        } else {
            throw new UsernameNotFoundException("User not found with email: " + email);
        }
    }
}
