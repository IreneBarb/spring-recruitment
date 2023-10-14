package com.unipi.backend.model;

import java.util.Objects;

public class CandidateRating {

    private Candidate candidate;

    private double rating;

    public CandidateRating(){

    }
    public CandidateRating(Candidate candidate, double positionRating) {
        this.candidate = candidate;
        this.rating = positionRating;
    }

    public Candidate getCandidate() {
        return candidate;
    }

    public void setCandidate(Candidate candidate) {
        this.candidate = candidate;
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
        CandidateRating that = (CandidateRating) o;
        return Objects.equals(candidate, that.candidate) &&
                Objects.equals(rating, that.rating);
    }

    @Override
    public int hashCode() {
        return Objects.hash(candidate, rating);
    }

    @Override
    public String toString() {
        return "JobPositionRating{" +
                ", candidate=" + candidate +
                ", rating=" + rating +
                '}';
    }
}
