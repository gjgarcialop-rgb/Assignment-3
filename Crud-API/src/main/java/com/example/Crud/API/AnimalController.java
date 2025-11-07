package com.example.Crud.API;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AnimalController {

  @Autowired
  private AnimalService animalService;

  /**
   * Endpoint to get all animals
   *
   * @return List of all animals
   */
  @GetMapping("/animals")
  public Object getAllAnimals() {
    return animalService.getAllAnimals();
  }

  /**
   * Endpoint to get an animal by ID
   *
   * @param id The ID of the animal to retrieve
   * @return The animal with the specified ID
   */
  @GetMapping("/animals/{id}")
  public Animal getAnimalById(@PathVariable long id) {
    return animalService.getAnimalById(id);
  }

  /**
   * Endpoint to get animals by name
   *
   * @param name The name of the animal to search for
   * @return List of animals with the specified name
   */
  @GetMapping("/animals/name")
  public Object getAnimalsByName(@RequestParam String key) {
    if (key != null) {
      return animalService.getAnimalsByName(key);
    } else {
      return animalService.getAllAnimals();
    }
  }

  /**
   * Endpoint to get animals by breed
   *
   * @param breed The breed to search for
   * @return List of animals with the specified breed
   */
  @GetMapping("/animals/breed/{breed}")
  public Object getAnimalsByBreed(@PathVariable String breed) {
    return animalService.getAnimalsByBreed(breed);
  }

  /**
   * Endpoint to get heavy animals with weight above a specified threshold
   *
   * @param weight The weight threshold for heavy animals
   * @return List of heavy animals with weight above the specified threshold
   */
  @GetMapping("/animals/heavy")
  public Object getHeavyAnimals(@RequestParam(name = "weight", defaultValue = "50.0") double weight) {
    return new ResponseEntity<>(animalService.getHeavyAnimals(weight), HttpStatus.OK);
  }

  /**
   * Endpoint to add a new animal
   *
   * @param animal The animal to add
   * @return List of all animals
   */
  @PostMapping("/animals")
  public Object addAnimal(@RequestBody Animal animal) {
    return animalService.addAnimal(animal);
  }

  /**
   * Endpoint to update an animal
   *
   * @param id     The ID of the animal to update
   * @param animal The updated animal information
   * @return The updated animal
   */
  @PutMapping("/animals/{id}")
  public Animal updateAnimal(@PathVariable Long id, @RequestBody Animal animal) {
    System.out.println("=== UPDATE ANIMAL DEBUG ===");
    System.out.println("Received animal update for ID: " + id);
    System.out.println("Animal object: " + animal);
    System.out.println("Animal name: " + animal.getName());
    System.out.println("Animal breed: " + animal.getBreed());
    System.out.println("Animal imageUrl: " + animal.getImageUrl());
    System.out.println("=== END DEBUG ===");
    
    animalService.updateAnimal(id, animal);
    Animal updatedAnimal = animalService.getAnimalById(id);
    
    System.out.println("=== AFTER UPDATE DEBUG ===");
    System.out.println("Returning updated animal: " + updatedAnimal);
    System.out.println("Updated animal imageUrl: " + updatedAnimal.getImageUrl());
    System.out.println("=== END AFTER UPDATE DEBUG ===");
    
    return updatedAnimal;
  }

  /**
   * Endpoint to delete an animal
   *
   * @param id The ID of the animal to delete
   * @return List of all animals
   */
  @DeleteMapping("/animals/{id}")
  public Object deleteAnimal(@PathVariable Long id) {
    animalService.deleteAnimal(id);
    return animalService.getAllAnimals();
  }

  /**
   * Endpoint to write an animal to a JSON file
   *
   * @param animal The animal to write
   * @return An empty string indicating success
   */
  @PostMapping("/animals/writeFile")
  public Object writeJson(@RequestBody Animal animal) {
    return animalService.writeJson(animal);
  }

  /**
   * Endpoint to read a JSON file and return its contents
   *
   * @return The contents of the JSON file
   */
  @GetMapping("/animals/readFile")
  public Object readJson() {
    return animalService.readJson();
  }

  /**
   * Test endpoint to create an animal with imageUrl for debugging
   */
  @PostMapping("/animals/test-image")
  public Animal testCreateAnimalWithImage() {
    Animal testAnimal = new Animal();
    testAnimal.setName("Test Giraffe");
    testAnimal.setBreed("Giraffe");
    testAnimal.setWeight(800.0);
    testAnimal.setDescription("Test animal with custom image URL");
    testAnimal.setImageUrl("https://cdn.pixabay.com/photo/2024/10/24/15/55/giraffe-9146077_640.jpg");
    
    System.out.println("Creating test animal with imageUrl: " + testAnimal.getImageUrl());
    Animal savedAnimal = animalService.addAnimal(testAnimal);
    System.out.println("Saved test animal: " + savedAnimal);
    
    return savedAnimal;
  }
}