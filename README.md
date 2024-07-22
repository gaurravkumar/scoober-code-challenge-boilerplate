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
