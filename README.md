Run the Spring Boot project using JDK 8



<div align='center'>

<p>this project handles the backend for the agenda editor project</p>



</div>

# :notebook_with_decorative_cover: Table of Contents

- [About the Project](#star2-about-the-project)


## :star2: About the Project
- this project uses Java 8 to handle the backend of agenda-editor. 
-  it is build using MVC pattern as a base, since the operations are fairly simple
- the frontent supports several features, check them out<a href="https://github.com/JudasII/agenda-editor-front"> Here</a>
### :dart: Features
- CRUD agendas
- CRUD agenda Items


## :toolbox: Getting Started

### :bangbang: Prerequisites

- clone/download the frontend Repo<a href="https://github.com/JudasII/agenda-editor-front"> Here</a>
- once you have it, simply run npm start on the project root
- run npm install


### :gear: Installation

to start the project use maven
```bash
spring-boot:run -f pom.xml
```

or follow the original base: 
- run npm install for the first time
- enable Annotation processing
- the app can be loaded at localhost:8080 or configure a different port server.port= in application.properties
- or use the Maven package task to generate a jar artifact and execute the command via java -jar target/agendaeditor-0.0.1-SNAPSHOT.jar for ex.
- feel free to migrate the project to a different tech stack that you are more comfortable with


### :test_tube: Running Tests

To run the tests, you can use:
```bash
mvn test
```