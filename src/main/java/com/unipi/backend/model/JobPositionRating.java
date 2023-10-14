package com.unipi.backend.model;

import javax.persistence.*;
import java.util.Objects;

public class JobPositionRating {

    private JobPosition jobPosition;

    private double rating;

    public JobPositionRating(){

    }
    public JobPositionRating(JobPosition jobPosition, double positionRating) {
        this.jobPosition = jobPosition;
        this.rating = positionRating;
    }

    public JobPosition getJobPosition() {
        return jobPosition;
    }

    public void setJobPosition(JobPosition jobPosition) {
        this.jobPosition = jobPosition;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        JobPositionRating that = (JobPositionRating) o;
        return Objects.equals(jobPosition, that.jobPosition) &&
                Objects.equals(rating, that.rating);
    }

    @Override
    public int hashCode() {
        return Objects.hash(jobPosition, rating);
    }

    @Override
    public String toString() {
        return "JobPositionRating{" +
                ", jobPosition=" + jobPosition +
                ", rating=" + rating +
                '}';
    }
}
