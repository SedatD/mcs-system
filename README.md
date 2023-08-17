# Read Me First

This application contains:
* Client API
  * Enables to interact with whole mcs-system by using REST Api.
  * Detailed explanation of endpoints can be found below in 'Testing' section.
  * [Swagger UI](http://localhost:8080/swagger-ui/index.html) can be a helpful visualization for endpoints and it's parameters. Don't forget that this will require to run docker first! 
* MCS
  * Mocks to try connecting satellite every 5 seconds and satellite will be reachable in every other try.
  * Checks 'Request' table to see if there is any pending imaging request comes from the client and passes to satellite
  * Periodically downloads all the images stored on satellite.
* Satellite
  * Communicates with MCS using REST Api.
  * Receives an image capture request and dump all locally stored images to MCS.
  * Includes an H2 embedded database to store images temporarily.
* Postgres Database
  * Client and MCS will connect to this database.

# Getting Started

### Prerequisites

- Docker: Make sure you have Docker installed on your system. You can download it from the [Docker website](https://www.docker.com/get-started).
- Clone the repository:
 ```bash
 git clone https://github.com/SedatD/mcs-system.git
 cd ./mcs-system
```

### Running Docker Container
- Use following command in cloned repository directory to run [docker compose](docker-compose.yml).
 ```bash
docker-compose up --build
 ```

### Testing

- Examples of each request can be found in [postman collection](turion.postman_collection.json)

1. We need to add a satellite by using the request sample in postman collection. (addSatellite)
2. After adding the satellite now we can add a client with (addClient)
3. Then it will be ready to put an imaging request with (addRequest)
4. Adding a new request will give the request id to see the image taken by satellite. We need to make a call to getAllImagesByRequestId with the request id. Note: We might need to wait for a while for satellite to get the request, capture image and gives back to mcs. 
