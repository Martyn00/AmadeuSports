package com.example.back.service;

import com.example.back.models.entities.MatchEntity;

import java.util.List;

public interface MatchService {
    List<MatchEntity> getMatchByDate(Integer data);
}
