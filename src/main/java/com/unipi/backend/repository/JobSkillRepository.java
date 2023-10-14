package com.unipi.backend.repository;

import com.unipi.backend.model.JobSkill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JobSkillRepository extends JpaRepository<JobSkill, Long> {
    List<JobSkill> findByJobPositionId(Long positionId);

    JobSkill findBySkillId(Long skillId);

    JobSkill findByJobPositionIdAndSkillId(Long positionId, Long skillId);
}