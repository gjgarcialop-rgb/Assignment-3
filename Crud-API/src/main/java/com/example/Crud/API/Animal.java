package com.example.Crud.API;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "animals")
public class Animal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long animalId;

    @Column(nullable = false)
    private String name;
    private String description; // Fun facts about the animal
    private String breed; // The breed/type of the animal

    @Column(nullable = false)
    private Double weight;

    public Animal() {
    }

    public Animal(Long animalId, String name, String breed, String description, Double weight) {
        this.animalId = animalId;
        this.name = name;
        this.breed = breed;
        this.description = description;
        this.weight = weight;
    }

    public Animal(String name, String breed, String description, Double weight) {
        this.name = name;
        this.breed = breed;
        this.description = description;
        this.weight = weight;
    }

    public Long getAnimalId() {
        return animalId;
    }

    public void setAnimalId(Long animalId) {
        this.animalId = animalId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBreed() {
        return breed;
    }

    public void setBreed(String breed) {
        this.breed = breed;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }
}

