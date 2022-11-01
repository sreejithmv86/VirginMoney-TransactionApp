About The Project :

This project deals with the money transactions. 
User is the main actor as he/she pulls several levels/aggregation of transactions data using the end-points exposed.
  
Built With :

Java 11,
Spring Boot 2.7.5,
In memory H2 database

Plugins : 

Lombok,
Springfox-swagger

Getting Started :

Download the code from this repo using git clone command and set up workspace using any IDE like Intellij,Eclipse etc.
Run a "mvn clean install" command to download all the required depedencies of the project.
Check inside Project Settings -> Modules for the proper Java version and ensure the Project Settings ->Libraries path has all the maven dependencies downloaded.
Ensure the "data.sql" file is present in the classpath and has valid INSERT commands to initialise the in-memory DB.

Usage :

Run the main class "CodingApplication" and check the console for "Started CodingApplication" text.
The application must have started in port 8080 and "testdb" should have been created with a table "TRANSACTION" which holds the values mentioned in data.sql script.
Navigate to http://localhost:8080/h2-console and provide the below inputs in the UI shown.
          Driver Class : org.h2.Driver,
          JDBC URL : jdbc:h2:mem:testdb,
          User Name : sa,
          Password : password,
  Test the connection and if it responds as sucess, click on Connect to see the database and table with data populated.
  
  Navigate to http://localhost:8080/swagger-ui.html and select transaction-controller. Check the GET and POST end-points exposed and use them to understand how each of them works.
  
