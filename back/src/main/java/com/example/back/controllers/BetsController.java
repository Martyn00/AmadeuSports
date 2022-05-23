package com.example.back.controllers;

import com.example.back.controllers.dto.BetDto;
import com.example.back.service.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/AmadeusSports/bet")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class BetsController {

//    private final UserServiceImpl userService;
//
//    @GetMapping(path = "/{userId}")
//    public List<BetDto> getBetByUserId(@PathVariable long userId) {
//        return userService.getBetsByUserId(userId);
//    }
}
