package com.unipi.backend.model;


import javax.persistence.*;

@Entity
@Table(
        name = "quiz"
)
public class Quiz {
    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    private Long id;
    private String name;

    public Quiz() {
    }

    public Quiz(String name) {
        this.name = name;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
