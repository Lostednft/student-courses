package com.project.test_student.service;

import com.project.test_student.domain.User;
import com.project.test_student.dto.UserLogin;
import com.project.test_student.repository.UserRepository;
import com.project.test_student.security.TokenService;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final TokenService tokenService;
    private final AuthenticationManager authenticationManager;

    public UserService(UserRepository userRepository, TokenService tokenService,@Lazy AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.tokenService = tokenService;
        this.authenticationManager = authenticationManager;
    }

    public User saveUser(User user){

        User userNew = user;

        String passwordEncoder = new BCryptPasswordEncoder().encode(user.getPassword());
        userNew.setPassword(passwordEncoder);

        return userRepository.save(userNew);
    }

    public User findByUsername(String username){

        return userRepository.findByUsername(username);
    }


    public String loginUserValidation(UserLogin login){

        var tokenUserPassword = new UsernamePasswordAuthenticationToken(login.username(), login.password());
        Authentication auth = authenticationManager.authenticate(tokenUserPassword);

        return tokenService.generate((User) auth.getPrincipal());
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username);
    }
}
