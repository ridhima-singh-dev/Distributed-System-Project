# FAANGConnect

## Distributed-System-Project
Synopsis:
FAANGConnect is a scalable and distributed web application designed to streamline the job-seeking process within the software development domain. It consolidates the employment opportunities dispersed across the prominent FAANG companies – Facebook, Amazon, Apple, Netflix, and Google. The primary goal is to simplify the job application process for software developers by centralizing job listings from these organizations. Rather than navigating the individual portals of each company, users can access and apply for various job openings seamlessly via a single platform. By aggregating job postings, FAANGConnect offers a consolidated experience, eliminating the need for separate accounts on each FAANG company's website.

## Architecture

The system's architecture consists of five distinct and independent employer services, each representing a FAANG company. Additionally, there exists a dedicated user service that manages account creation, job applications, and provides a friendly user interface for applicants to review their submitted applications. The system manages user requests via a broker mechanism, acting as an intermediary layer. This broker leverages Netflix Eureka to facilitate communication between the user service and all registered employer services.



## System Overview
| Component          | Description                                                                                                                                                                                                                             |
|--------------------|-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| **apple**          | 1 of 5 distributed, independent employer services that posts jobs for users to apply to. Uses MongoDB as the database of choice to store posted jobs.                                                                                    |
| **amazon**         | 1 of 5 distributed, independent employer services that posts jobs for users to apply to. Uses MongoDB as the database of choice to store posted jobs.                                                                                    |
| **google**         | 1 of 5 distributed, independent employer services that posts jobs for users to apply to. Uses MongoDB as the database of choice to store posted jobs.                                                                                    |
| **netflix**        | 1 of 5 distributed, independent employer services that posts jobs for users to apply to. Uses MongoDB as the database of choice to store posted jobs.                                                                                    |
| **facebook**       | 1 of 5 distributed, independent employer services that posts jobs for users to apply to. Uses MongoDB as the database of choice to store posted jobs.                                                                                    |
| **employee**       | Represents the client or application’s user that interacts with the broker (eureka-server) to find the jobs posted by registered services and apply for them. Uses MongoDB as the database of choice to store the activity of every signed user (jobs applied to by this user). This service also contains the frontend code. |
| **eureka-server**  | Acts as the broker/intermediary between the employee and employers. Runs the Netflix Eureka server, where all employer services register themselves.                                                                                                |
| **login**          | Implements the register/sign-in user logic of the frontend. Uses MySQL as the database of choice to store registered user credentials.                                                                                                   |
| **notification**   | Implements MoM functionality by creating queues in a RabbitMQ server to send messages/notifications when the user applies for a job posted by an employer.                                                                                |
| **core**           | Contains all the common models and repositories (classes and methods) used by all employers and employees.   |


System Architecture
![WhatsApp Image 2024-01-05 at 17 45 04_80da1433](https://github.com/ridhima-singh-dev/Distributed-System-Project/assets/31386972/d87546bb-8159-4336-9200-2f83d6da4f4e)


# Architecture

## Cloud Interface Configuration
We have prepared an interface capable of adeptly managing incoming cloud traffic, indicative of the system's readiness for cloud-based operations.

## Adoption of Docker
Our team's strategy includes the adoption of Docker to encapsulate microservices, promoting deployment ease and environmental consistency.

## Client Interaction Layer
### Frontend
Utilizing JavaScript and jQuery, we have constructed a user interface that provides a responsive and interactive experience, reachable through the application's main access path.

### API Facilitation
The reliable Apache Tomcat is deployed as the API gateway in our setup, tasked with efficiently routing API traffic to the corresponding services.

## Discovery and Registration Mechanism
### Eureka Server Deployment
A crucial component for dynamic service discovery, the Eureka server plays a key role in the registration and discovery of microservices, ensuring smooth inter-service communication.

## Microservices Framework
### Service Broker Role
Integral to our design is a service broker that manages the flow of API requests, enabling effective communication between the frontend and backend services.

### Notification System
Incorporating RabbitMQ, we've established an effective message queueing system for handling notifications, pivotal for our event-driven architecture.

### API Documentation
Through the use of Swagger, we provide thorough documentation of our APIs, offering an intuitive interface for developers to interact with the service endpoints.

## Security Protocol
### Authentication Services
Our design includes a robust authentication service, showcasing our unwavering focus on secure access.

## Dedicated Microservices
### Segmented Services
Embracing microservices architecture, we've created individual services, each paired with a MongoDB database, to manage specific segments of business logic.

### Subscription Services
We have devised a service specifically for handling subscriptions to job-related events, underscoring the reactive design of our system.

## Data Storage and Persistence
### NoSQL Database Implementation
Aligned with our distributed data management strategy, MongoDB instances are allocated to each microservice, promoting autonomy.

### Persistent Volume Integration
We've ensured data retention and resilience across service restarts and downtimes by implementing persistent storage solutions.

### Relational Database Incorporation
To handle structured data requiring relational integrity, we've included a MySQL database, addressing transactional data management needs.

# How to Run?
Instructions on how to compile and run your code:

1. mvn clean install
2. mvn package
3. docker-compose build (We have only dockerize rabbitmq)
4. docker-compose up (To make the Rabbitmq up and running)
5. run each module as a springboot Application 
mvn compile spring-boot:run -pl eureka-server
mvn compile spring-boot:run -pl amazon
mvn compile spring-boot:run -pl apple
mvn compile spring-boot:run -pl facebook
mvn compile spring-boot:run -pl google
mvn compile spring-boot:run -pl netflix
mvn compile spring-boot:run -pl login
mvn compile spring-boot:run -pl notification

6. access the website in http://localhost:8080.
The website landing page will be login/signup page.
Signup to the login page.






