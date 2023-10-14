package com.unipi.backend.model;

import javax.persistence.*;

@Entity
@Table(name = "job_skills")
public class JobSkill {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "job_position_id")
    private Positions jobPosition;

    @ManyToOne
    @JoinColumn(name = "skill_id")
    private Skill skill;

    private double weight;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Positions getJobPosition() {
        return jobPosition;
    }

    public void setJobPosition(Positions jobPosition) {
        this.jobPosition = jobPosition;
    }

    public Skill getSkill() {
        return skill;
    }

    public void setSkill(Skill skill) {
        this.skill = skill;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }
}