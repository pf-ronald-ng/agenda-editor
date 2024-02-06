Run the Spring Boot project using JDK 8
- run npm install for the first time
- enable Annotation processing
- the app can be loaded at localhost:8080 or configure a different port server.port= in application.properties
- or use the Maven package task to generate a jar artifact and execute the command via java -jar target/agendaeditor-0.0.1-SNAPSHOT.jar for ex.
- feel free to migrate the project to a different tech stack that you are more comfortable with

# Brief description

I created a new Front project with `Angular framework`, and added new features to backend project with spring boot, I also added the `unit tests` and `swagger`, 
I didn't have time to add secutiry with spring secutiry.

With the button `Find` you can list the agendas, you can create a new agenda with the button `Add Agenda` and you must add one item per agenda.

Each agenda has the option to `view` and `delete`. If you click on `view`, you can find all items per agenda, and you can add more items or edit or delete items.
If you want go back, jut click on `Find` button.

# Backend Project

This project was generated with `Spring Boot` version 2.3.0.

## Development server

- Run the application normally.
- The server run on `http://localhost:8080/`.

## Swagger

- To see the API documentation, run the backend application.
- Navigate to `http://localhost:8080/swagger-ui/`.

# Frontend AgendasProject

This project was generated with [Angular CLI](https://github.com/angular/angular-cli) version 17.1.1.

## Development server

- Go to path `agenda-editor\src\main\angular\agendasProject`
- Run `npm install` for the first time
- Run `ng serve` for a dev server. Navigate to `http://localhost:4200/`. The application will automatically reload if you change any of the source files.

