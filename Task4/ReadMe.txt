quoco.ws
Running the Project
To run this project, you need to have Docker and Maven installed on your machine. Follow these steps:

Build and Run the Docker Containers: To build and run all services, navigate to the project root directory and execute:

    docker-compose up --build

Run the Client: Once the services are running, you can run the client by executing:

    mvn compile exec:java -pl client

Make sure you are using Java 8 to ensure compatibility with the services when running client.

Overview of Services
Broker Service
Functionality: The Broker Service acts as an intermediary that handles requests from the client and delegates them to the appropriate quotation services (e.g., Auld Fellas, Dodgy Geezers, Girls Allowed). It retrieves quotations based on client information and returns the results back to the client.

Web Service Package: The Broker Service uses JAX-WS (Java API for XML Web Services) to expose its functionalities as a SOAP web service. This allows clients to interact with the service using standard web protocols.

Auld Fellas Service
Functionality: The Auld Fellas Service provides quotations based on specific client criteria. It implements the logic for generating quotes, applying discounts based on client age and gender, and produces a reference for each quotation.

Web Service Package: Like the Broker Service, it also uses JAX-WS to expose its operations as a SOAP web service, enabling clients to request quotations through the defined endpoints.

Dodgy Geezers Service
Functionality: Similar to the Auld Fellas Service, the Dodgy Geezers Service provides its own implementation of quotation logic, tailored to its criteria for generating quotations. It operates independently and can be called by the Broker Service.

Web Service Package: This service also utilizes JAX-WS to expose its SOAP web service functionality, allowing for seamless integration with the Broker Service.

Girls Allowed Service
Functionality: The Girls Allowed Service, like the other quotation services, generates quotes based on defined business rules. It contributes to the overall system by offering alternative quotation options for clients.

Web Service Package: This service also uses JAX-WS, providing SOAP web service capabilities for communication with the Broker Service.

Technologies Used
Java 8: The project is built using Java 8, ensuring compatibility with all services.
Maven: For project management and dependency handling.
Docker: To containerize the services for easier deployment and management.
JAX-WS: For implementing SOAP web services that facilitate communication between clients and services.
Conclusion
This project demonstrates the use of microservices architecture with Docker and SOAP web services. Each service is responsible for its own functionality, while the Broker Service coordinates requests and responses between the client and the quotation services.