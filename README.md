# Credit_Suisse_Test

Credit_Suisse_Test is a back-end java application assignment.

Backend application which receives input as json file and process it further to identify which process has taken longer time, below are the core tech-stack used to implement

Java programming language Java/JDK 1.8, Spring-boot 2.5.5, Spring framework / JPA, h2 in memory database, Gradle, IDE STS & Swagger.

The project is implemented using IDE STS & used basic auth in memory.

I have used the h2 in memory database(http://localhost:8001/h2-console) that runs in memory to store the data. 
Post importing application into workspace you have to use below gradle command to start the application "clean build test assemble bootRun"

Above command will also create the application.jar executable jar file, you can execute only that also using command "java -jar <path_to_jar>/application.jar"

It will take some time to start the application post that you can access the services as below.

Use below credentials while accessing services, using postman/swagger. 
User:- credit_suisse   &   Password:- password

Swagger URL http://<your_machine_ip>:8001/swagger-ui.html or http://localhost:8001/swagger-ui.html to test the services.

API #1 - POST /event/file/upload  - to upload valid json file.
API #2 - GET /event/list   -  to list alerts who taken 4 and more than 4 miliseconds to process.
