package com.example.back.service;

import com.example.back.controllers.dto.BetDto;

import java.util.ArrayList;

public interface BetService {
    ArrayList<BetDto> getBetHistory();
}
