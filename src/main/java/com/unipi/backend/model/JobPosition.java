package com.unipi.backend.model;


import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "positions")
public class JobPosition {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "position_name")
    private String positionName;

    @Column(name = "description")
    private String description;

    @Column(name = "department")
    private String department;

    @Column(name = "company")
    private String company;

    public JobPosition() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPositionName() {
        return positionName;
    }

    public void setPositionName(String positionName) {
        this.positionName = positionName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        JobPosition that = (JobPosition) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(positionName, that.positionName) &&
                Objects.equals(description, that.description) &&
                Objects.equals(department, that.department) &&
                Objects.equals(company, that.company);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, positionName, description, department, company);
    }

    @Override
    public String toString() {
        return "JobPosition{" +
                "id=" + id +
                ", positionName='" + positionName + '\'' +
                ", description='" + description + '\'' +
                ", department='" + department + '\'' +
                ", company='" + company + '\'' +
                '}';
    }
}
