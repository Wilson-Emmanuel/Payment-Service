# Payment service
This is a GraphQL web API that models a simple payment service with lots of features.

## Tools used
- Java 17
- Spring Boot 3.0.1 (Springframework 6)
- GraphQL (Spring-boot-starter-graphql) integrated with GraphiQL, which is An in-browser IDE for exploring GraphQL.
- Postgres: DBMS
- Mockito and JUnit: For `unit` and `integration` testing
- Docker-compose : A simple container orchestration tool for defining and running multi-container docker applications.
- pgAdmin : A web-based management tool for Postgres DB.

## Features
- INPUT VALIDATION: GraphQL uses the defined schema to validate supplied input. Also, additional layer of validation was added using `Hibernate Validator` library. This is to ensure that all supplied input are validated before any further processing or storage.
- DATA STORAGE AND RETRIEVAL: using postgres db
- GRAPHQL PLAY GROUND: `spring-boot-starter-graphql` integrates with the `GraphiQL`, which is accessible at `http://localhost:9091/graphiql` once the server starts running.
- DB MANAGEMENT: Using docker compose, `pgAdmin` has been FULLY orchestrated and configured for managing postgres data. The postgres server has been configured on pgAdmin also. 
- MULTI-CONTAINER DEPLOYMENT: `docker compose` is used to orchestrate 4 application containers for this payment service (the payment service container, pgAdmin container, two postgres database containers including test database)
- TESTING: Few integration tests has been written for this service and all tests passed.


## Run Instructions
Ensure you have latest `Docker` and `Git` installed and running on your system, after which, follow the instructions below to get the system up and running.
1. Clone the application to your local system:

```$ git clone https://github.com/Wilson-Emmanuel/Payment-Service.git```

2. `CD` into the docker directory which is inside project directory

```$ cd Payment-Service/docker```

3. Run the docker compose file.

```$ docker-compose up```


Note: The above instruction is using an already built `jar` file to run the application. This should be enough since the file contains the latest changes made to the application. However, if you would want to re-build the application, skip from `step 2` and continue the steps below after cloning the project.

2. `CD` into the project directory after cloning the project.

```$ cd Payment-Service```

3. Build a new `jar` file of the application.

```$ ./mvnw clean package -DskipTests```

Note: It is very necessary to include the `-DskipTests` in the command above since the test database is not yet running, else the build process would fail.

4. Once the jar file is successfully built, copy it from the target folder where it's built into the docker folder. Be sure to use the `-f` option to force an overwrite over the file already existing in the destination folder.

```$ cp -f target/payment-service-0.0.1-SNAPSHOT.jar docker/payment-service.jar```

5. Lastly, go into the docker directory and run the applications

```$ cd docker```

```$ docker-compose up```

Once the containers start running, you can access the services on the following locations
- ```http://localhost:9091/graphql``` : this is the single endpoint that GraphQL uses for all its requests. Use it incase you want to use external client services/tools like `Postman` to connect to the running payment service.
- ```"http://localhost:5050/browser/``` : this is the interface of `pgAdmin` with postgres already configured
- ```http://localhost:9091/graphiql```: this is the GraphQL playground. I have included [some sample snippets](https://github.com/Wilson-Emmanuel/Payment-Service/blob/master/query_snippets) so that you could easily start issuing request once the services are up.

At this point when the test database is already running, you can run the prepared tests. Just go the main project direct as in step 2 and execute the following command

```$ ./mvnw test```

PS: Use the following command to remove all docker resources if you want to start all over.

```$ docker-compose down --rmi all -v```


## GraphQL Query Snippets
I have prepared query snippets samples for all the available endpoints to ease the testing process. You can access the snippets on the link below;

[Sample Snippets Here](https://github.com/Wilson-Emmanuel/Payment-Service/blob/master/query_snippets)