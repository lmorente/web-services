This project contains 3 different web applications.

#Book services-web
Contains 3 basic web applications, 2 developed with Java 17 + Spring Boot 2.6.0 (no persistence, JPA persistence) and a web application with node.js and express 4.17.1

#Message websocket application
This project is a distributed application consisting of different services that communicate with each other using REST API and gRPC. The application provides a web interface that communicates with the server using GraphQL. 

Some services are implemented with Node.js/Express and others with Java/Spring. These technologies must be installed on the host to build and run the services. Docker is also required to run the auxiliary services (MySQL and MongoDB).

Scripts implemented in Node.js are used to build the services and run them, as well as to run the required ancillary services. 

#Multimodule-web-application
Based on the previous project, the "server" service is divided into two "server and planner". They communicate with each other by means of rabbitmq queues. The server server is developed in node while the planner with Java and Spring Cloud Stream. 

