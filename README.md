[![Java CI](https://github.com/dimuthnc/drones-for-medication-delivery/actions/workflows/maven.yml/badge.svg)](https://github.com/dimuthnc/drones-for-medication-delivery/actions/workflows/maven.yml)

# Drone Management Service

## Introduction

Even though drones have been introduced as a mechanism to surveil things from higher elevations, they are now used for different purposes, such as photography and transportation.

The aim of this project is to develop a REST API using Spring Boot, allowing clients to communicate with medication delivery drones.

This application uses the H2 in-memory database as the default database. 

## Building the project

To build this project locally on your machine, please follow the below steps.

1. Install git, maven (3.x.x) and JDK ( 11 or above) on your machine
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

__Please note these endpoints are secured with basic authentication. Therefore, you need to provide the basic authentication header for the default user when invoking the endpoint. The default username is `user` and password is `password`. You can change them from [here](https://github.com/dimuthnc/drones-for-medication-delivery/blob/6c50e5e6de4f8d6c421b8b0b41f3725a56fe2008/src/main/java/com/drones/dimuth/drone/management/config/SecurityConfig.java#L22) if you wish to use unique credentials.__ 

### Registering a drone
```
curl -X POST -H "Content-type: application/json" -d '{
"serialNumber": "14",
"model": "Heavyweight",
"weightLimit": 499.1,
"batteryLevel": "100.0"
}' -u user:password 'http://localhost:8080/api/v1/drone/register'
```
You will receive a 201 Created response with a response as below to indicate the created drone
```json
{
    "serialNumber": "14",
    "model": "Heavyweight",
    "weightLimit": 499.1,
    "batteryLevel": 100.0,
    "state": "IDLE"
}
```
### Check available medications

```
curl -X GET -H "Content-type: application/json" -u user:password 'http://localhost:8080/api/v1/medication'

```
You will get a `200 OK` response with a payload with medications as below.

```json
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

### Loading a drone with medication items. ( Creating a delivery)

We created a drone with serial number 14 in the first call. We can note the medication IDs from the above 2nd call. With that information, we can construct this request to load a drone with medications as below.

```
curl -X POST -H "Content-type: application/json" -d '{
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
}'  -u user:password 'http://localhost:8080/api/v1/delivery/register'

```
You will get a `201 Created` response with response like below. 

```json
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
If the sum of all medication items you are trying to load into the drone is greater than than the maximum allowed weight, you will receive a `400 Bad Request` error response as below.

```json
{
    "message": "Drone 19 can carry only 100.0 grams but the request load weight more"
}
```
### Checking loaded medication items for a given drone

```
curl -X GET -H "Content-type: application/json" -u user:password 'http://localhost:8080/api/v1/delivery/status?droneId=14'
```
If the `droneSerialNumber` is a valid value ( to which you loaded medications previously), you will receive a `200 OK` with a response to indicate the drone information and loaded medication information. 

```json
{
    "id": 128,
    "drone": {
        "serialNumber": "14",
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

```json
{
    "message": "Drone for the given serial number 100 is not loaded with medications"
}
```
### Checking available drones for loading.

```
curl -X GET -H "Content-type: application/json" -u user:password 'http://localhost:8080/api/v1/drone/status?filter=available'
```
You will receive a `200 OK` response with a set of available drones as a JSON Array as below. Please note the above loaded drones are missing from this list as they are no longer available for loading.

```json
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
### Check drone battery level for a given drone

```
curl -X GET -H "Content-type: application/json" -u user:password 'http://localhost:8080/api/v1/drone/status/14?filter=battery'

```
If the serial number is you sent as a path parameter is a valid serial number of a registered drone, you will receive a `200 OK` success response as below.
```json
{
    "batteryLevel": 96.4
}
```

If the serial number is invalid, you will receive a `400 Bad Request` error message as below.

```json
{
    "message": "Drone with serial number 11 not found"
}
```

## periodic task to check drones battery levels and create history/audit event log for this.

A periodic task is running ( in every 10 minutes according to default configurations. You can change this from [here](https://github.com/dimuthnc/drones-for-medication-delivery/blob/6c50e5e6de4f8d6c421b8b0b41f3725a56fe2008/src/main/java/com/drones/dimuth/drone/management/util/DroneManagementConstants.java#L9)) to check the battery status of each drone and create a log event into the database. To view this information, an API has been implemented. Use the below curl commands to retrieve event logs.

### Retrieve all battery audit events

```
curl -X GET -H "Content-type: application/json" -u user:password 'http://localhost:8080/api/v1/audit/battery'

```
If the curl command is successful, you will see a JSON response like below.

```json
[
    {
        "id": 1,
        "droneSerialNumber": "0",
        "time": "2023-02-15T12:33:02.348451",
        "batteryLevel": 77.08
    },
    {
        "id": 2,
        "droneSerialNumber": "1",
        "time": "2023-02-15T12:33:02.365975",
        "batteryLevel": 29.91
    },
    {
        "id": 3,
        "droneSerialNumber": "2",
        "time": "2023-02-15T12:33:02.367227",
        "batteryLevel": 80.95
    },
    {
        "id": 4,
        "droneSerialNumber": "3",
        "time": "2023-02-15T12:33:02.367844",
        "batteryLevel": 64.27
    },
    {
        "id": 5,
        "droneSerialNumber": "4",
        "time": "2023-02-15T12:33:02.36847",
        "batteryLevel": 84.13
    },
    {
        "id": 6,
        "droneSerialNumber": "5",
        "time": "2023-02-15T12:33:02.369068",
        "batteryLevel": 22.39
    },
    {
        "id": 7,
        "droneSerialNumber": "6",
        "time": "2023-02-15T12:33:02.36965",
        "batteryLevel": 49.53
    },
    {
        "id": 8,
        "droneSerialNumber": "7",
        "time": "2023-02-15T12:33:02.370194",
        "batteryLevel": 96.13
    },
    {
        "id": 9,
        "droneSerialNumber": "8",
        "time": "2023-02-15T12:33:02.370728",
        "batteryLevel": 67.5
    },
    {
        "id": 10,
        "droneSerialNumber": "9",
        "time": "2023-02-15T12:33:02.371226",
        "batteryLevel": 62.72
    },
    ...
    {
        "id": 111,
        "droneSerialNumber": "9",
        "time": "2023-02-15T12:43:02.363632",
        "batteryLevel": 77.08
    }
]
```
### Retrieve battery audit events for a selected drone

```
curl -X GET -H "Content-type: application/json" -u user:password 'http://localhost:8080/api/v1/audit/battery?droneSerialNumber=1'

```
If the curl command is successful, you will see a JSON response like below.

```json
[
    {
        "id": 7,
        "droneSerialNumber": "1",
        "time": "2023-02-15T15:38:59.440866",
        "batteryLevel": 57.92
    },
    {
        "id": 19,
        "droneSerialNumber": "1",
        "time": "2023-02-15T15:48:59.424383",
        "batteryLevel": 57.92
    },
    {
        "id": 31,
        "droneSerialNumber": "1",
        "time": "2023-02-15T15:58:59.418785",
        "batteryLevel": 57.92
    },
    {
        "id": 43,
        "droneSerialNumber": "1",
        "time": "2023-02-15T16:08:59.456179",
        "batteryLevel": 57.92
    },
    {
        "id": 55,
        "droneSerialNumber": "1",
        "time": "2023-02-15T16:18:59.469152",
        "batteryLevel": 57.92
    },
    ...
    {
        "id": 139,
        "droneSerialNumber": "1",
        "time": "2023-02-15T17:28:59.326265",
        "batteryLevel": 57.92
    }
]
```

