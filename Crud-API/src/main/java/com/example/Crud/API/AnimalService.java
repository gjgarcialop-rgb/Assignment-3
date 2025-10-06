package com.example.Crud.API;

import java.io.IOException;
import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class AnimalService {

  @Autowired
  private AnimalRepository animalRepository;

  /**
   * Method to get all animals
   *
   * @return List of all animals
   */
  public Object getAllAnimals() {
    return animalRepository.findAll();
  }

  /**
   * Method to get an animal by ID
   *
   * @param animalId The ID of the animal to retrieve
   * @return The animal with the specified ID
   */
  public Animal getAnimalById(@PathVariable long animalId) {
    return animalRepository.findById(animalId).orElse(null);
  }

  /**
   * Method to get animals by name
   *
   * @param name The name of the animal to search for
   * @return List of animals with the specified name
   */
  public Object getAnimalsByName(String name) {
    return animalRepository.getAnimalsByName(name);
  }

  /**
   * Method to get animals by breed
   *
   * @param breed The breed to search for
   * @return List of animals with the specified breed
   */
  public Object getAnimalsByBreed(String breed) {
    return animalRepository.getAnimalsByBreed(breed);
  }

  /**
   * Fetch all animals with a weight above a threshold.
   *
   * @param weight the weight threshold
   * @return the list of matching Animals
   */
  public Object getHeavyAnimals(double weight) {
    return animalRepository.getHeavyAnimals(weight);
  }

  /**
   * Method to add a new animal
   *
   * @param animal The animal to add
   */
  public Animal addAnimal(Animal animal) {
    return animalRepository.save(animal);
  }

  /**
   * Method to update an animal
   *
   * @param animalId The ID of the animal to update
   * @param animal   The updated animal information
   */
  public Animal updateAnimal(Long animalId, Animal animal) {
    return animalRepository.save(animal);
  }

  /**
   * Method to delete an animal
   *
   * @param animalId The ID of the animal to delete
   */
  public void deleteAnimal(Long animalId) {
    animalRepository.deleteById(animalId);
  }

  /**
   * Method to write an animal object to a JSON file
   *
   * @param animal The animal object to write
   */
  public String writeJson(Animal animal) {
    ObjectMapper objectMapper = new ObjectMapper();
    try {
      objectMapper.writeValue(new File("animals.json"), animal);
      return "Animal written to JSON file successfully";
    } catch (IOException e) {
      e.printStackTrace();
      return "Error writing animal to JSON file";
    }
  }

  /**
   * Method to read an animal object from a JSON file
   *
   * @return The animal object read from the JSON file
   */
  public Object readJson() {
    ObjectMapper objectMapper = new ObjectMapper();
    try {
      return objectMapper.readValue(new File("animals.json"), Animal.class);
    } catch (IOException e) {
      e.printStackTrace();
      return null;
    }
  }
}