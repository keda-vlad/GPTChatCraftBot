# GPTChatCraftBot: Elevating Your Chat Experience

Welcome to the GPTChatCraftBot project! Our innovative Telegram bot, developed using Java Spring Boot, is designed to revolutionize the way users interact with ChatGPT. By supporting multiple contexts and seamless context switching, we aim to provide users with a dynamic and engaging conversational experience. Below, we will introduce you to the key features and functional capabilities of our application.

## Features:

### Security:

GPTChatCraftBot utilizes JWT tokens for secure authentication and authorization. This ensures that only verified users can access sensitive features, maintaining a high level of security.

### Chat Contexts:

Users can engage in conversations with ChatGPT while maintaining multiple contexts. The ability to switch between contexts enhances the natural flow of conversations on various topics.

### Admin Functionality:

Administrators have access to a RESTful API, allowing them to view user Telegram chats and respond to them directly. This functionality provides admins with a convenient way to manage and engage with users.

## Used Technologies:

- Spring Boot: Dependency and library management through Maven.
- Spring Data JPA: Executing CRUD operations on data models.
- Spring Security: Ensuring application and user data security.
- Spring MVC: Developing a web application using the Model-View-Controller (MVC) pattern for better code organization and separation of business logic, views, and control.
- MapStruct: Efficient mapping of models to Data Transfer Objects (DTOs).
- Docker: Containerization for easy deployment and scalability.
- OpenAPI: Documenting our RESTful API to provide clear information about available resources, requests, and response formats.
- Liquibase: Managing and versioning the database schema for easy schema changes and data schema consistency in different environments.

## Functionality:

API functionality is divided for Admin:

| Controller               | Admin                                             |
|--------------------------|---------------------------------------------------|
| AuthenticationController | Login                                             |
| ContextController        | Ability to track contexts                         |
| UserController           | Ability to register new admins, update and delete |
| ChatController           | Ability to track chats and send messages to chat  |


## Installation
### Prerequisites

Before you begin, ensure you have the following prerequisites installed on your system:

- [Java 17](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html)
- [Maven](https://maven.apache.org/download.cgi)
- [Docker](https://docs.docker.com/get-docker/)

Follow these steps to set up and run the Book Store API on your local machine:

**Clone the Repository:**

   Via IDE:
    - Open IntelliJ IDEA.
    - Select "File" -> "New Project from Version Control."
    - Paste the link: https://github.com/keda-vlad/GPTChatCraftBot.git
    - Run the following commands:

        mvn clean package
        docker build -t your_image_name
        docker-compose up --build

   Via the console:
    - Execute the command: `git clone https://github.com/keda-vlad/GPTChatCraftBot.git`
    - Run the following commands:

        mvn clean install
        docker build -t your_image_name
        docker-compose up --build

   You can use Postman, if you completed installation:

   - Open Postman.
   - Import the [file](TGBOT.postman_collection.json) with requests.

## Video Presentation
In this [video](https://www.youtube.com/watch?v=-pbpx3cbx18), you'll get a comprehensive demonstration of how the application functions. We'll explore the intricacies of engaging in conversations with ChatGPT, switching between contexts, and the admin's capacity to manage and respond to user chats.

## Contribution

   ### Issues and Solutions

   **Issue 1: Context Switching**
    - Solution: Implement a robust context-switching mechanism for users by entity management

   **Issue 2: Admin Functionality**
    - Solution: Develop a secure RESTful API for admin functionality.

   **Issue 3: JWT Token Handling**
    - Solution: Ensure proper handling and validation of JWT tokens for security.

# Conclusion:

   TThe GPTChatCraftBot API offers a dynamic and engaging platform for users to interact with ChatGPT. We welcome contributions from the community to enhance the GPTChatCraftBot project. Whether you want to fix a bug, improve an existing feature, or propose a new one, your contributions are valuable to us.

   ### Upcoming Expected Updates:

   Deployment on AWS

## Contact Us:

   For any inquiries or suggestions, please email us at vladyslav.keda@gmail.com.

   Thank you for your interest in the Bookstore API project and your contribution to its development!