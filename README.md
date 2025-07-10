# recipe-test

## Instructions on How to Install, Test and Run

### Prerequisites:
Ensure that the server have the following tools installed:
* 1. Java Development Kit (JDK 21)
* 2. Apache Maven
* 3. Lombok

### To begin, you need to folow this steps:
* 1. Clone the repository from github

* 2. Configure the application:
You don't need to modify the application.yml. They use a memory h2 DB

* 3. Build the Project
You need to go to the root directory of the project and build using maven with the command:mvn clean install

* 4. Running Unit Tests
The project have many unit tests. To run, use the command: mvn test

* 5. Run the Application
After you build the project, you can run using the command: mvn spring-boot:run
* 6. Access from Browser
http://localhost:8080

* 7. Test the Application
You can use postman to test the REST endpoints
