# springCamel

This application is built on SpringBoot, with camel incorporated

A REST endpoint has been exposed for a POST request which takes in a JSON and validates it.
Camel then transforms this into a XML message using JaxB, from a XSD.

The output is validated again, before sending it back as a response from the POST

The REST endpoint is :
>http://localhost:8080/invoice/transform

To run the build we will need to run the command
>mvn clean install

This will execute the complie, and also run a few tests which involve tests for the Controller, for the actual route, and a end to end test which involves checks to make sure the transformation happens.
A few negative cases have also been added in

This build was created, tested and deployed on openjdk11, but should be compliant with jdk 8
 
## Running the application :

>mvn spring-boot:run

Once this is run please try the below to check if the app is running
>http://localhost:8080/invoice/info

## Docker

This project also includes a Dockerfile which could be used for deploying into a Docker container