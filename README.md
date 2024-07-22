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