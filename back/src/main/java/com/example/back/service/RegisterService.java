package com.example.back.service;

import com.example.back.models.entities.ConfirmationToken;
import com.example.back.models.entities.User;
import com.example.back.models.requestsAndResponses.RegisterRequest;
import com.example.back.repositories.UserRepo;
import com.example.back.security.EmailValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RegisterService implements UserDetailsService {

    private final UserRepo userRepo;
    private final EmailValidator emailValidator;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final ConfirmationTokenService confirmationTokenService;
    private final EmailService emailService;

    public String registerNewUser(RegisterRequest registerRequest) {
        //verificam daca email-ul are fromatul corespunzator
        if(!emailValidator.validateEmail(registerRequest.getEmail())) {
            return "The email does not have the right format!";
        }

        //verificam daca un user cu email-ul respectiv se gaseste deja
        Optional<User> appuser = userRepo.findByEmail(registerRequest.getEmail());
        if(appuser.isPresent()) {
            return "Email already taken!";
        }

        //verificam daca un user cu username-ul respectiv se gaseste deja
        appuser = userRepo.findByUsername(registerRequest.getUsername());
        if(appuser.isPresent()) {
            return "Username already taken!";
        }

        if(registerRequest.getPassword().length() < 6) {
            return "The password must be at least 6 characters!";
        }

        //incriptam parola si salvam userul in baza de date
        User user = new User(registerRequest.getUsername(),
                registerRequest.getEmail(),
                registerRequest.getPassword(),
                registerRequest.getRole());
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userRepo.save(user);

        //salvam tokenul in baza de date
        String token = UUID.randomUUID().toString();
        confirmationTokenService.saveCorfirmationToken(new ConfirmationToken(
                token,
                LocalDateTime.now(),
                LocalDateTime.now().plusMinutes(20),
                user
        ));

        //trimitem tokenul pe mail pentru verificare
        String link = "http://localhost:8080/api/confirmToken?token=" + token;
        emailService.send(user.getEmail(), buildEmail(link));

        return "You have been registered, now confirm your email!";
    }

    public String buildEmail(String link) {
        return "<h1 style=\"margin: auto; border: 0em; a\">Hello,</h1>\n" +
                "    </br>\n" +
                "    </br>\n" +
                "    <p>Thanks for your registration!</p>\n" +
                "    <p style=\"margin-top: -1pc\">\n" +
                "        Please confirm your registration by clicking on the link below.\n" +
                "    </p>\n" +
                "    <div style=\"margin-left: 30px;\">\n" +
                "        <a href=\"" + link + "\">Activate Now</a>"+
                "\n" +
                "    </div>\n" +
                "    <br>\n" +
                "    <p><i>pwp :* from AmadeusSports</i></p>";
    }

    @Transactional
    public void confirmToken(String token) {
        //verificam daca token-ul exista
        ConfirmationToken confirmationToken = confirmationTokenService
                .getToken(token)
                .orElseThrow(() -> new IllegalStateException("token not found"));

        //verificam daca tokenul a fost deja verificat
        if(confirmationToken.getConfirmedAt() != null) {
            throw new IllegalStateException("token already confirmed");
        }

        //verificam daca tokenul a expirat deja
        if(confirmationToken.getExpiresAt().isBefore(LocalDateTime.now())) {
            throw new IllegalStateException("token expired");
        }

        confirmationTokenService.updateConfirmedAt(token);
        userRepo.updateConfirmUser(confirmationToken.getUser().getUsername());
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepo.findByUsername(username).orElseThrow(() ->
                new UsernameNotFoundException(
                        String.format("username %s not found", username)
                ));
    }
}
