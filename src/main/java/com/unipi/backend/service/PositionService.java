package com.unipi.backend.service;

import com.unipi.backend.model.JobPosition;
import com.unipi.backend.repository.PositionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PositionService {

    @Autowired
    private PositionRepository positionRepository;

    public List<JobPosition> getAllPositions() {
        return positionRepository.findAll();
    }

}