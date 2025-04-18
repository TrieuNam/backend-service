# backend-service

1. Prerequisite

- Cài đặt JDK 17+
- Install Maven 3.5+
- Install IntelliJ
- Install Docker

2. Technical Stacks

- Java 17
  -Maven 3.5+
- Spring Boot 3.3.4
- Spring Data Validation
- Spring Data JPA
- Postgres/MySQL (optional)
- Lombok
- DevTools
- Docker
- Docker compose
- ...
3. Build & Run Application
   – Run application bởi mvnw tại folder backend-service
```shell
$ ./mvnw spring-boot:run
```
– Run application bởi docker
```shell
$ mvn clean install -P dev
$ docker build -t backend-service:latest .
$ docker run -it -p 8080:8080 --name backend-service backend-service:latest
```
4. Test
   – Check health với cURL
```shell
curl --location 'http://localhost:8080/actuator/health'

-- Response --
{
    "status": "UP"
}
```