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

[Postman Script](https://elements.getpostman.com/redirect?entityId=9989156-a4a35c13-6e01-4d82-91f6-eef75f4efbdc&entityType=collection).

__Please note these endpoints are secured with basic authentication. Therefore, you need to provide the basic authentication header for the default user when invoking the endpoint. The default username is **user** and password is **password**. You can change them from here if you wish to use unique credentials.__ 

1. Adding a drone
```
curl -X POST -H "Content-type: application/json" -d '{
"serialNumber": "14",
"model": "Heavyweight",
"weightLimit": 499.1,
"batteryLevel": "100.0"
}' -u user:password 'http://localhost:8080/api/v1/drone'
```
You will receive a 201 Created response with a response as below to indicate the created drone
```
{
    "serialNumber": "14",
    "model": "Heavyweight",
    "weightLimit": 499.1,
    "batteryLevel": 100.0,
    "state": "IDLE"
}
```
2. Check available medications

```
curl -X GET -H "Content-type: application/json" -u user:password 'http://localhost:8080/api/v1/medication'

```
You will get a `200 OK` response with a payload with medications as below.

```
[
    {
        "weight": 73.74,
        "code": "1234",
        "name": "Paracetamol"
    },
    {
        "weight": 64.1,
        "code": "1235",
        "name": "Ibuprofen"
    },
    {
        "weight": 70.3,
        "code": "1236",
        "name": "Aspirin"
    },
    {
        "weight": 55.17,
        "code": "1237",
        "name": "Caffeine"
    }
]
```

3. Load a drone with medication. ( Creating a delivery)

We created a drone with serial number 14 in the first call. We can note the medication IDs from the above 2nd call. With that information, we can construct this request to load a drone with medications as below.

```
curl -XPOST -H "Content-type: application/json" -d '{
    "drone": {
        "serialNumber": "14"
    },
    "medicationDeliveries": [
        {
            "medication": {
                "code": "1234"
            }
        },
        {
            "medication": {
                "code": "1236"
            }
        }
    ]
}'  -u user:password 'http://localhost:8080/api/v1/delivery/drone'

```
You will get a `201 Created` response with response like below. 

```
{
    "id": 128,
    "drone": {
        "serialNumber": "14",
        "model": "Heavyweight",
        "weightLimit": 499.1,
        "batteryLevel": 100.0,
        "state": "IDLE"
    },
    "medicationDeliveries": [
        {
            "medication": {
                "weight": 73.74,
                "code": "1234",
                "name": "Paracetamol"
            }
        },
        {
            "medication": {
                "weight": 70.3,
                "code": "1236",
                "name": "Aspirin"
            }
        }
    ]
}
```
4. Checking the drone for loaded Medications

```
curl -X GET -H "Content-type: application/json" -u user:password 'http://localhost:8080/api/v1/delivery/drone/12'
```
If the `droneSerialNumber` is a valid value ( to which you loaded medications previously), you will receive a `200 OK` with a response to indicate the drone information and loaded medication information. 

```
{
    "id": 128,
    "drone": {
        "serialNumber": "12",
        "model": "Heavyweight",
        "weightLimit": 499.1,
        "batteryLevel": 100.0,
        "state": "LOADED"
    },
    "medicationDeliveries": [
        {
            "medication": {
                "weight": 73.74,
                "code": "1234",
                "name": "Paracetamol"
            }
        },
        {
            "medication": {
                "weight": 70.3,
                "code": "1236",
                "name": "Aspirin"
            }
        }
    ]
}
```
If you sent an invalid serial Number, you will receive an `400 Bad Request` error response as follows.

```
{
    "message": "Drone for the given serial number 100 is not loaded with medications"
}
```
5. Check for available drones ( for loading medication)

```
curl -X GET -H "Content-type: application/json" -u user:password 'http://localhost:8080/api/v1/drone/available'
```
You will receive a `200 OK` response with a set of available drones as a JSON Array as below. Please note the above loaded drones are missing from this list as they are no longer available for loading.

```
[
    {
        "serialNumber": "0",
        "model": "Lightweight",
        "weightLimit": 395.33,
        "batteryLevel": 77.08,
        "state": "IDLE"
    },
    {
        "serialNumber": "1",
        "model": "Middleweight",
        "weightLimit": 220.13,
        "batteryLevel": 29.91,
        "state": "IDLE"
    },
    {
        "serialNumber": "2",
        "model": "Cruiserweight",
        "weightLimit": 422.07,
        "batteryLevel": 80.95,
        "state": "IDLE"
    },
    {
        "serialNumber": "3",
        "model": "Cruiserweight",
        "weightLimit": 396.17,
        "batteryLevel": 64.27,
        "state": "IDLE"
    },
    {
        "serialNumber": "4",
        "model": "Cruiserweight",
        "weightLimit": 458.66,
        "batteryLevel": 84.13,
        "state": "IDLE"
    },
   
    {
        "serialNumber": "15",
        "model": "Heavyweight",
        "weightLimit": 499.1,
        "batteryLevel": 100.0,
        "state": "IDLE"
    }
]
```
6. Check the battery level of a drone

```
curl -X GET -H "Content-type: application/json" -u user:password 'http://localhost:8080/api/v1/drone/11/battery'

```
If the serial number is you sent as a path parameter is a valid serial number of a registered drone, you will receive a `200 OK` success response as below.
```
{
    "batteryLevel": 96.4
}
```

If the serial number is invalid, you will receive a `400 Bad Request` error message as below.

```
{
    "message": "Drone with serial number 11 not found"
}
```