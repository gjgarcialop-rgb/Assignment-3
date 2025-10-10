# Animal CRUD API
Simple CRUD API for Animal Objects with JPA (Hibernate)

### Version
1.0.0

### Video
<video controls src="file:///C:/Users/darkn/OneDrive%20-%20UNCG/340%20work/Assignment-3-vid.mp4" title="Title"></video>
     
file:///C:/Users/darkn/OneDrive%20-%20UNCG/340%20work/Assignment-3-vid.mp4

## Installation
- Get the project
    - clone
        ```
      git clone https://github.com/gjgarcialop-rgb/Assignment-3.git
        ```
    - OR download zip.
- Open the project in VS Code.
- This project is built to run with jdk 21.
- Dependencies to JPA and Postgres in addition to the usual Spring Web. JPA handles the persistence, Postgresql is the database to be used.
- [`/src/main/resources/application.properties`] This file has the configuration for the PostgreSQL database to use for the API.
  - You MUST have the database up and running before running the project!
    - Login to your neon.tech account.
    - Locate your database project.
    - On the project dashboard, click on "Connect" and select Java.
    - Copy the connection string provided.
    - Paste it as a value for the property `spring.datasource.url`. No quotation marks.
- Build and run the main class. You should see a new table created in the Neon database.
## Notes
### Java - [Spring ORM with JPA and Hibernate](https://medium.com/@burakkocakeu/jpa-hibernate-and-spring-data-jpa-efa71feb82ac)
- We are using ORM (Object-Relational Mapping) to deal with databases. This is a technique that allows us to interact with a relational database using object-oriented programming principles.
- JPA (Jakarta Persistence, formerly Java Persistence API) is a specification that defines ORM standards in Java. It provides an abstraction layer for ORM frameworks to make concrete implementations.
- Hibernate: Hibernate is a popular ORM framework that implements JPA. It simplifies database operations by mapping Java objects to database tables and handling queries efficiently.
Spring ORM allows seamless integration of Hibernate and JPA, making database interactions more manageable and reducing boilerplate code.
### Animal Java classes have different purposes: Separation of concerns!
- **Entity**
  - The Animal class is annotated as an `@Entity`. This is used to map class attributes to database tables and SQL types.
  - We also annotated with `@Table` to give Hibernate directions to use this specific table name "animals". This is optional but it helps with naming conventions.
  - Any Entity must have at least one attribute that is annotated as an `@Id`. In our case it's conveniently the `animalId` attribute.
    - We are also using an autogeneration strategy for the ID. This way we are not manually assigning IDs to our animals. This is optional.
       - For this reason, we also added a constructor to make an Animal without an ID.
  - An Entity must have a no-argument constructor.
- **Repository**
  - We are using an extension of the JPA Repository that comes with prebuilt database operations such as select all, select by id, select by any other reference, insert, delete, etc.
  - Annotate it as a `@Repository`.
  - We parametrize this using our object and its ID type.
    - `public interface AnimalRepository extends JpaRepository<Animal, Long>` => We want to apply the JPA repository operations on the `Animal` type. The `Animal` has an ID of type `long`.
  - If we need special database queries that are not the standard ones mentioned above, we can create a method with a special purpose query as shown. This is an interface so no implementation body.
- **Service**
  - Annotated as a `@Service`.
  - It is the go-between from controller to database. In here we define what functions we need from the repository. A lot of the functions are default functions that our repository inherits from JPA (save, delete, findAll, findByX), some of them are custom made (getHeavyAnimals, getAnimalsByName, getAnimalsByBreed).
  - It asks the repository to perform SQL queries.
  - The Repository class is `@Autowired`. This is for managing the dependency to the repository. Do not use a constructor to make a Repository object, you will get errors.
- **Rest Controller**
  - Annotated as a `@RestController`.
  - It asks the Service class to perform data access functions.
  - The Service class is `@Autowired` here as well :)

## API Endpoints
Base URL: [`http://localhost:8080/animals`](http://localhost:8080/animals)


1. ### [`/`](http://localhost:8080/animals) (GET)
Gets a list of all Animals in the database.

#### Response - A JSON array of Animal objects.

 ```
[
{
    "animalId": 2,
    "name": "Chimey",
    "description": "This penguin loves to dance",
    "breed": "Penguin",
    "weight": 12.0
  },
  {
    "animalId": 3,
    "name": "Larry",
    "description": "This giraffe loves strawberries",
    "breed": "Giraffe",
    "weight": 27.0
  },
  {
    "animalId": 1,
    "name": "Ceaser",
    "description": "This animal loves to climb trees",
    "breed": "Chimpanzee",
    "weight": 15.0
  }
]
```

2. ### [`/{animalId}`](http://localhost:8080/animals/2) (GET)
Gets an individual Animal in the system. Each Animal is identified by a numeric `animalId`

#### Parameters
- Path Variable: `animalId` &lt;Long &gt; - REQUIRED

#### Response - A single Animal

```
  {
  "animalId": 2,
  "name": "Chimey",
  "description": "This penguin loves to dance",
  "breed": "Penguin",
  "weight": 12.0
}
```

3. ### [`/name`](http://localhost:8080/animals/name?key=s) (GET)
Gets a list of animals with a name that contains the given string.

#### Parameters
- query parameter: `key` &lt; String &gt; - REQUIRED

#### Response - A JSON array of Animal objects.

```
[
  {
    "animalId": 1,
    "name": "Ceaser",
    "description": "This animal loves to climb trees",
    "breed": "Chimpanzee",
    "weight": 15.0
  }
]
```

4. ### [`/breed/{breed}`](http://localhost:8080/animals/breed/Penguin) (GET)
Gets a list of animals for a named breed.

#### Parameters
- path variable: `breed` &lt; String &gt; - REQUIRED

#### Response - A JSON array of Animal objects.

```
[
   {
    "animalId": 2,
    "name": "Chimey",
    "description": "This penguin loves to dance",
    "breed": "Penguin",
    "weight": 12.0
  }
]
```
5. ### [`/heavy`](http://localhost:8080/animals/heavy?weight=20.0) (GET)
Gets a list of animals with a weight meeting the threshold.

#### Parameters
- query parameter: `weight` &lt;Double&gt; - REQUIRED

#### Response - A JSON array of Animal objects.

```
[
  {
    "animalId": 1,
    "name": "Ceaser",
    "description": "This animal loves to climb trees",
    "breed": "Chimpanzee",
    "weight": 15.0
  }
]
```
6. ### [`/`](http://localhost:8080/animals) (POST)
Create a new Animal entry

#### Request Body
An animal object. Note the object does not include an ID as this is autogenerated.
```
{
  {
  "name": "Larry",
  "description": "This giraffe loves strawberries",
  "breed": "Giraffe",
  "weight": 27.0
}
}
```
#### Response - The newly created Animal.

```
  {
    "animalId": 3,
   "name": "Larry",
  "description": "This giraffe loves strawberries",
  "breed": "Giraffe",
  "weight": 27.0
  }
```

7. ### [`/{animalId}`](http://localhost:8080/animals/3) (PUT)
Update an existing Animal.

#### Parameters
- Path Variable: `animalId` &lt;Long&gt; - REQUIRED

#### Request Body
An animal object with the updates.
```
{
  "animalId": 1,
  "name": "Ceaser",
  "description": "This animal loves to climb trees",
  "breed": "Chimpanzee",
  "weight": 18.0
}
```
#### Response - the updated Animal object.
```
{
  "animalId": 1,
  "name": "Ceaser",
  "description": "This animal loves to climb trees",
  "breed": "Chimpanzee",
  "weight": 18.0
}
```

8. ### [`/{animalId}`](http://localhost:8080/animals/3) (DELETE)
Delete an existing Animal.

#### Parameters
- Path Variable: `animalId` &lt;Long&gt; - REQUIRED

#### Response - the updated list of Animals.
```
[
 {
    "animalId": 2,
    "name": "Chimey",
    "description": "This penguin loves to dance",
    "breed": "Penguin",
    "weight": 12.0
  },
  {
    "animalId": 1,
    "name": "Ceaser",
    "description": "This animal loves to climb trees",
    "breed": "Chimpanzee",
    "weight": 18.0
  }
]
```