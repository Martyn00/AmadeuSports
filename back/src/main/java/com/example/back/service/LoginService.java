package com.example.back.service;

import com.example.back.models.entities.User;
import com.example.back.models.requestsAndResponses.AuthRequest;
import com.example.back.models.requestsAndResponses.AuthResponse;
import com.example.back.models.requestsAndResponses.ForgotPassRequest;
import com.example.back.repositories.UserRepo;
import com.example.back.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LoginService {

    private final RegisterService registerService;
    private final UserRepo userRepo;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtTokenUtil;

    public ResponseEntity<?> tryLogin(AuthRequest authenticationRequest) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(),
                            authenticationRequest.getPassword())
            );
        }
        catch (BadCredentialsException e) {
            return new ResponseEntity<>("Incorrect username or password", HttpStatus.OK);
        }

        final UserDetails userDetails = registerService.
                loadUserByUsername(authenticationRequest.getUsername());

        final User appUser = userRepo.findByUsername(authenticationRequest.getUsername()).get();

        final String jwt = jwtTokenUtil.generateToken(userDetails);

        return ResponseEntity.ok(new AuthResponse(jwt, authenticationRequest.getUsername(), appUser.getRole()));
    }

    @Transactional
    public String forgotPassword(ForgotPassRequest forgotPassRequest) {
        Optional<User> user = userRepo.findByUsername(forgotPassRequest.getUsername());
        if(user.isEmpty()) {
            return "The username does not exist.";
        }

        if(!Objects.equals(user.get().getEmail(), forgotPassRequest.getEmail())) {
            return "The username and the email do not match.";
        }

        if(!bCryptPasswordEncoder.matches(forgotPassRequest.getOld_password(), user.get().getPassword())) {
            return "The old password does not match.";
        }

        String new_password_encoded = bCryptPasswordEncoder.encode(forgotPassRequest.getNew_password());
        userRepo.updatePassword(user.get().getUsername(), new_password_encoded);

        return "The password has been changed!";
    }
}
