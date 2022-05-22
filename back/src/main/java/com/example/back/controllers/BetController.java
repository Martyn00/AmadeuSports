package com.example.back.controllers;

import com.example.back.controllers.dto.BetDto;
import com.example.back.service.BetService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
@RequestMapping(path = "/AmadeusSports/bet")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class BetController {

    private final BetService betService;

    @GetMapping(path = "/history")
    public ArrayList<BetDto> getBetHistory() {
        return betService.getBetHistory();
    }
}
