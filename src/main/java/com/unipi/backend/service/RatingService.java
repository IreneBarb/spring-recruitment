package com.unipi.backend.service;

import com.unipi.backend.model.*;
import com.unipi.backend.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class RatingService {
    @Autowired
    JobSkillRepository jobSkillRepository;
    @Autowired
    private UserQuizAnswerRepository userQuizAnswerRepository;
    @Autowired
    private JobPositionRepository jobPositionRepository;
    @Autowired
    private QuestionsRepository questionsRepository;
    @Autowired
    private CandidateRepository candidateRepository;

    public RatingService() {
    }

    public List<JobPositionRating> rateCandidateForPositions(Long candidateId) {
        List<UserQuizAnswer> userQuizAnswers = userQuizAnswerRepository.findByUserId(candidateId);

        // Create a map to store user's scores for each skill
        Map<Integer, Double> skillScores = new HashMap<>();

        // Calculate skill scores based on user's answers
        for (UserQuizAnswer userQuizAnswer : userQuizAnswers) {
            Question question = getQuestion(userQuizAnswer.getQuestionId().intValue());
            int skillId = question.getSkillId();
            int correctOption = question.getCorrectOption();
            int selectedOption = userQuizAnswer.getSelectedOption().intValue();

            // Calculate the score for this question
            double questionScore = (selectedOption == correctOption) ? 1.0 : 0.0;


            // Add the question's score to the skill's total score
            skillScores.put(skillId, skillScores.getOrDefault(skillId, 0.0) + questionScore);
        }

        // Normalize skill scores to the range [0, 1]
        normalizeSkillScores(skillScores);

        // Retrieve all job positions
        List<JobPosition> jobPositions = jobPositionRepository.findAll();

        // Calculate the overall rating for each job position based on required skills
        List<JobPositionRating> positionRatings = new ArrayList<>();
        for (JobPosition jobPosition : jobPositions) {
            List<JobSkill> requiredSkills = getRequiredSkills(jobPosition.getId());
            double positionRating = calculatePositionRating(requiredSkills, skillScores);
            positionRatings.add(new JobPositionRating(jobPosition, positionRating));
        }

        return positionRatings;
    }

    private void normalizeSkillScores(Map<Integer, Double> skillScores) {
        // Find the maximum skill score
        double maxScore = skillScores.values().stream()
                .mapToDouble(Double::doubleValue)
                .max()
                .orElse(1.0); // Default to 1.0 if there are no scores

        // Normalize scores to the range [0, 1]
        skillScores.replaceAll((skillId, score) -> score / maxScore);
    }

    private double calculatePositionRating(List<JobSkill> requiredSkills, Map<Integer, Double> skillScores) {
        double positionRating = 0.0;
        for (JobSkill requiredSkill : requiredSkills) {
            Long skillId = requiredSkill.getSkill().getId();
            if (skillScores.containsKey(skillId.intValue())) {
                positionRating += skillScores.get(skillId.intValue());
            } else {
                positionRating += 0;
            }
        }
        // Normalize the position rating to the range [0, 1]
        return Math.min(1.0, positionRating / requiredSkills.size());
    }

    public Question getQuestion(int questionId) {
        return questionsRepository.getReferenceById(questionId);
    }

    public List<JobSkill> getRequiredSkills(Long positionId) {
        return jobSkillRepository.findByJobPositionId(positionId);
    }

    public List<CandidateRating> getTopThreeCandidatesForPositions(Long positionId) {
        List<Candidate> candidates = candidateRepository.findAll();
        Map<Candidate, Double> candidateRatings = new HashMap<>();
        for(Candidate candidate: candidates){
            List<JobPositionRating> jobRatings = rateCandidateForPositions(candidate.getId());
            for(JobPositionRating jobPositionRating: jobRatings){
                if(jobPositionRating.getJobPosition().getId() == positionId) {
                    candidateRatings.put(candidate, jobPositionRating.getRating());
                }
            }
        }

        List<CandidateRating> sortedCandidates = new ArrayList<>();
        for (Map.Entry<Candidate, Double> entry : candidateRatings.entrySet()) {
            Candidate candidate = entry.getKey();
            Double rating = entry.getValue();
            sortedCandidates.add(new CandidateRating(candidate, rating));
        }

        Collections.sort(sortedCandidates, Comparator.comparing(CandidateRating::getRating).reversed());

        int topCount = Math.min(3, sortedCandidates.size());
        return sortedCandidates.subList(0, topCount);
    }
}
