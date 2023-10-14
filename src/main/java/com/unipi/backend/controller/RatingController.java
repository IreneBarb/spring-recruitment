package com.unipi.backend.controller;

import com.unipi.backend.model.CandidateRating;
import com.unipi.backend.model.JobPositionRating;
import com.unipi.backend.model.UserQuizAnswer;
import com.unipi.backend.service.RatingService;
import com.unipi.backend.service.UserQuizAnswerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping({"/api/rating"})
public class RatingController {
    @Autowired
    private RatingService ratingService;

    public RatingController() {
    }

    @CrossOrigin(
            origins = {"http://localhost:4200"}
    )
    @GetMapping({"/candidate/{candidateId}"})
    public ResponseEntity<List<JobPositionRating>> rateCandidateForPositions(@PathVariable Long candidateId) {
        List<JobPositionRating> ratings = this.ratingService.rateCandidateForPositions(candidateId);
        return new ResponseEntity(ratings, HttpStatus.OK);
    }

    @CrossOrigin(
            origins = {"http://localhost:4200"}
    )
    @GetMapping({"/position/{positionId}"})
    public ResponseEntity<List<CandidateRating>> getTopThreeCandidatesForPosition(@PathVariable Long positionId) {
        List<CandidateRating> ratings = this.ratingService.getTopThreeCandidatesForPositions(positionId);
        return new ResponseEntity(ratings, HttpStatus.OK);
    }
}
