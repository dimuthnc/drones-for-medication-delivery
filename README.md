# Drone Management Service

## Introduction

Even though drones have been introduced as a mechanism to surveil things from higher elevations, they are now used for different purposes, such as photography and transportation.

The aim of this project is to develop a REST API using Spring Boot, allowing clients to communicate with medication delivery drones.

This application uses the H2 in-memory database as the default database. 

## Building the project

To build this project locally on your machine, please follow the below steps.

1. Install git, maven and JDK ( 11 or above) on your machine
Execute the below command from your terminal application in a desired location to clone this project.

```git clone https://github.com/dimuthnc/drones-for-medication-delivery ```

2. Locate the project location from the terminal application ( using cd commands) and execute the below command to build the application.

```mvn clean install```
	
3. Upon successfully completing this command, you will notice the **target** folder created in your root project location. Locate the __drone.management.service-<version>.jar__ file in that location and copy it to a desired location.

## Running the application

To run this program, your machine needs to have a JRE installed in the machine. Locate the above jar file using the terminal and execute the below command to run the application.

```java -jar drone.management.service-<version>.jar```

## Testing the application

To test this application, you can use different API client applications such as CURL, Postman, IntelliJ IDEA REST Client etc.

You can use this postman script or below curl commands for testing the API endpoints

[ Postman Script] (https://elements.getpostman.com/redirect?entityId=9989156-a4a35c13-6e01-4d82-91f6-eef75f4efbdc&entityType=collection).