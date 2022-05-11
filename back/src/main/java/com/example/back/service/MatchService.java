package com.example.back.service;

import com.example.back.controller.dto.MatchDto;
import com.example.back.models.entities.MatchEntity;

import java.util.List;

public interface MatchService {
    List<MatchDto> getMatchByDate(Integer data);
}
