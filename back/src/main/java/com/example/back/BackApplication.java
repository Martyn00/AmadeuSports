package com.example.back;

import com.example.back.models.Role;
import com.example.back.models.entities.ConfirmationToken;
import com.example.back.models.entities.User;
import com.example.back.repositories.UserRepo;
import com.example.back.service.ConfirmationTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.UUID;

@SpringBootApplication
@RequiredArgsConstructor
public class BackApplication {

    private final UserRepo appUserRepo;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final ConfirmationTokenService confirmationTokenService;

    public static void main(String[] args) {
        SpringApplication.run(BackApplication.class, args);
    }

    @EventListener(ApplicationReadyEvent.class)
    @Transactional
    public void doSomethingAfterStartup() {
//        User appUser = new User("TibiAlex",
//                "tibi.alex@yahoo.com",
//                "password",
//                Role.ADMIN);
//        saveUser(appUser);
//
//        appUser = new User("Martin",
//                "martin@yahoo.com",
//                "password",
//                Role.ADMIN);
//        saveUser(appUser);
//
//        appUser = new User("Stefan",
//                "stefan@yahoo.com",
//                "password",
//                Role.ADMIN);
//        saveUser(appUser);
//
//        appUser = new User("Rares",
//                "rares@yahoo.com",
//                "password",
//                Role.ADMIN);
//        saveUser(appUser);
    }

    @Transactional
    public void saveUser(User appUser) {
        appUser.setPassword(bCryptPasswordEncoder.encode(appUser.getPassword()));
        appUserRepo.save(appUser);
        String token = UUID.randomUUID().toString();
        confirmationTokenService.saveCorfirmationToken(new ConfirmationToken(
                token,
                LocalDateTime.now(),
                LocalDateTime.now().plusMinutes(20),
                appUser
        ));
        ConfirmationToken confirmationToken = confirmationTokenService
                .getToken(token)
                .orElseThrow(() -> new IllegalStateException("token not found"));
        confirmationTokenService.updateConfirmedAt(token);
        appUserRepo.updateConfirmUser(confirmationToken.getUser().getUsername());
    }

}
