package com.unipi.backend.controller;

import com.unipi.backend.model.JobSkill;
import com.unipi.backend.service.JobSkillsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/jobskills")
public class JobSkillController {
    @Autowired
    private JobSkillsService jobSkillService;

    public JobSkillController() {
    }

    // Endpoint to get all job skills
    @CrossOrigin(
            origins = {"http://localhost:4200"}
    )
    @GetMapping
    public List<JobSkill> getAllJobSkills() {
        return jobSkillService.getAllJobSkills();
    }

    @CrossOrigin(
            origins = {"http://localhost:4200"}
    )
    @GetMapping("/{skillId}")
    public JobSkill getJobSkillBySkillId(@PathVariable Long skillId) {
        return jobSkillService.getJobSkillBySkillId(skillId);
    }

    // Endpoint to create a new job skill
    @CrossOrigin(
            origins = {"http://localhost:4200"}
    )
    @PostMapping
    public JobSkill createJobSkill(@RequestBody JobSkill jobSkill) {
        return jobSkillService.createJobSkill(jobSkill);
    }

    // Endpoint to update an existing job skill
    @CrossOrigin(
            origins = {"http://localhost:4200"}
    )
    @PutMapping("/{id}")
    public JobSkill updateJobSkill(@PathVariable Long id, @RequestBody JobSkill jobSkill) {
        return jobSkillService.updateJobSkill(id, jobSkill);
    }

    // Endpoint to delete a job skill by ID
    @CrossOrigin(
            origins = {"http://localhost:4200"}
    )
    @DeleteMapping("/{id}")
    public void deleteJobSkill(@PathVariable Long id) {
        jobSkillService.deleteJobSkill(id);
    }
}