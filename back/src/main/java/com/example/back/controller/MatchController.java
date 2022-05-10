package com.example.back.controller;


import com.example.back.controller.dto.MatchDto;
import com.example.back.models.entities.MatchEntity;
import com.example.back.service.MatchService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/AmadeusSports/match")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class MatchController {

    private final MatchService matchService;

    @GetMapping(path = "/{data}")
    public List<MatchDto> getMatchByDate(@PathVariable Integer data) {

        return matchService.getMatchByDate(data);
    }
}
