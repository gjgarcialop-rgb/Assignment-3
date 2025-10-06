package com.example.Crud.API;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AnimalRepository extends JpaRepository<Animal, Long> {

    List<Animal> getAnimalsByBreed(String breed);

    @Query(value = "select * from animals a where a.weight >= ?1", nativeQuery = true)
    List<Animal> getHeavyAnimals(double weight);

    @Query(value = "select * from animals a where a.name like %?1% ", nativeQuery = true)
    List<Animal> getAnimalsByName(String name);
}