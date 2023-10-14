package com.unipi.backend.service;

import com.unipi.backend.model.JobSkill;
import com.unipi.backend.repository.JobSkillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JobSkillsService {

    private final JobSkillRepository jobSkillRepository;

    @Autowired
    public JobSkillsService(JobSkillRepository jobSkillRepository) {
        this.jobSkillRepository = jobSkillRepository;
    }

    public List<JobSkill> findSkillsForPosition(Long positionId) {
        return jobSkillRepository.findByJobPositionId(positionId);
    }

    // Get all job skills
    public List<JobSkill> getAllJobSkills() {
        return jobSkillRepository.findAll();
    }

    // Create a new job skill
    public JobSkill createJobSkill(JobSkill jobSkill) {
        return jobSkillRepository.save(jobSkill);
    }

    // Update an existing job skill
    public JobSkill updateJobSkill(Long id, JobSkill jobSkill) {
//        implement this
        return null;
    }

    // Delete a job skill by ID
    public void deleteJobSkill(Long id) {
        jobSkillRepository.deleteById(id);
    }

    public JobSkill getJobSkillBySkillId(Long skillId) {
        return jobSkillRepository.findBySkillId(skillId);
    }
}