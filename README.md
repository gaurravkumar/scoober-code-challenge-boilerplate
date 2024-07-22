# scoober-code-challenge-boilerplate

See [here](./example.md)

# Resources
- [Example](./example.md) contains instructions about the repository content and how to use it
- [LICENSE](./LICENSE) contains a reference copy of the Apache 2.0 license that applies all Just Eat Takeaway.com projects.
- [CODE_OF_CONDUCT.md](./CODE_OF_CONDUCT.md) describes the Code of Conduct that applies to all contributors to our projects.
- [README.md](./README.md) How to use the code and what still needs to be done.
- [FLOW](./Getting%20the%20Three%20Flow.png) contains the flow of the application.
- [EXPLANATION](./GettingTheThreeDemo.mov) contains the explanation of the flow of the application.

# How to use the code

## Prerequisites
- Java 17
- Maven 3.8.3
- Docker
- Docker Compose
- Postman
- Git
- IDE (IntelliJ IDEA, Eclipse, etc.)

## How to run the Infrastructure
- If you are running the application on Mac, you need to first run 
  - `echo "127.0.0.1 host.docker.internal" | sudo tee -a /etc/hosts`
  - `docker pull rabbitmq`
  - `docker run -p 5672:5672 -d rabbitmq`
  - `docker pull mongo`
  - `docker run -p 27017:27017 -d mongo`

## How to run the application
- Clone the repository
- Run 'mvn clean install' to build the project
- Run 'mvn spring-boot:run' to start the application or use the green arrow in your IDE

### Using Docker
- Go to root of the project.
- Run `docker-compose up` (Currently getting Unsupported JDK Issue. Need to fix it)


## How to test the application
As per problem statement, if we want to switch between MANUAL and AUTOMATIC mode, we can do it by adding "number" as 
query parameter in the start game API. 
Also, we can add "addNumber" as query parameter in the make move API to make a MANUAL move in the game.
- Open Postman
- Use the following sequence
  - Add a Player .. say Player 1
  - Add a Player .. say Player 2
  - Player 1 creates a game
  - Player 2 joins the game
  - Player 1 starts the game
  - Player 2 makes a move in the game and then Player 1 and so on.
  - One of the Player wins the game

## curl requests

### Add Player
```curl --location 'localhost:8080/player/create' \
--header 'Content-Type: application/json' \
--data '{
 "name": "Batman",
 "uniqueLoginName": "sp2"
    
}' 
```

### Create Game
```curl --location 'localhost:8080/game/create' \
--header 'Content-Type: application/json' \
--data '{
    "loginName": "sp1",
    "inputType": "MANUAL"
}'
```
### Join Game
```curl --location 'localhost:8080/joinGame/game/669e6a2edd602c78447542a1' \
--header 'Content-Type: application/json' \
--data '{
    "player": "sp2"
}'
```

### Start Game
```curl --location 'localhost:8080/start/game/669e6a2edd602c78447542a1?number=17' \
--header 'Content-Type: application/json' \
--data '{
    
    "playerUniqueName": "sp1"
}'
```

### Make Move
```curl --location 'localhost:8080/makeMove/game/669e6a2edd602c78447542a1/player/sp1?addNumber=1' \
--data ''
``` 

### Get All Games
```curl --location 'localhost:8080/getAllActiveGames' \
--header 'Content-Type: application/json' \
--data ''
```


## Class Explanation

- `GameServiceImpl: This is a service class that implements the GameService interface. It contains business logic related to game operations, such as starting a game, creating a game, getting all active games, joining a game, and making a move in a game.`  
- `PlayerServiceImpl: This is a service class that implements the PlayerService interface. It contains business logic related to player operations, such as creating a player and getting a player by their unique login name.`  
- `ProcessorImpl: This is a class that implements the Processor interface. It contains logic for processing a message in the game.`  
- `ScrooberPlayerController: This is a controller class that handles HTTP requests related to player operations. It uses the PlayerServiceImpl to perform these operations.`  
- `ScrooberGameController: This is a controller class that handles HTTP requests related to game operations. It uses the GameServiceImpl to perform these operations.`  
- `MessageSenderService: This is a service class that sends messages to a RabbitMQ queue.`  
- `ListenerService: This is a service class that listens to a RabbitMQ queue for messages.`  
- `PlayerPersistence: This is a class that interacts with the PlayerRepository to perform database operations related to players.`  
- `GamePersistence: This is a class that interacts with the GameRepository to perform database operations related to games.`  
- `GameProgressPersistence: This is a class that interacts with the GameProgressRepository to perform database operations related to game progress.`  
- `PlayerRepository: This is an interface that extends MongoRepository. It is used to perform CRUD operations on the Player model.`  
- `GameRepository: This is an interface that extends MongoRepository. It is used to perform CRUD operations on the Game model.`  
- `GameProgressRepository: This is an interface that extends MongoRepository. It is used to perform CRUD operations on the GameProgress model.`  
- `RabbitMQConfig: This is a configuration class that sets up RabbitMQ, including creating queues and setting up the RabbitTemplate.`  
- `Each of these classes is annotated with a Spring stereotype annotation (@Service, @Controller, @Repository, @Configuration), which indicates its role in the application and enables Spring to detect and manage it as a bean in the Spring application context.`