package com.example.back.service;

import com.example.back.controllers.dto.MatchDto;

import java.util.List;

public interface MatchService {
    List<MatchDto> getMatchByDate(Integer data);
}
