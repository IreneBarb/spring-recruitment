package com.unipi.backend.controller;

import com.unipi.backend.model.JobPosition;
import com.unipi.backend.service.PositionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/position")
public class PositionController {
    @Autowired
    private PositionService positionService;

    public PositionController() {
    }

    @CrossOrigin(
            origins = {"http://localhost:4200"}
    )
    @GetMapping
    public List<JobPosition> getAllPositions() {
        return positionService.getAllPositions();
    }

}