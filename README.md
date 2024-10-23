# study-buddy

## React/Spring Boot web application

An app made to connect students looking for study partners and 
allow mentors to share their teaching topics and expertise.

## Spring Boot + Maven

This template provides a minimal setup to get a Spring Boot project up and running using Maven. 
It includes a simple configuration to run the project with some basic dependencies.

### Prerequisites

#### 1. Java Development Kit (JDK)

Make sure you have JDK installed on your system. You can download it from [Oracle JDK](https://www.oracle.com/java/technologies/javase-jdk11-downloads.html) 
or use OpenJDK from [here](https://jdk.java.net/).

#### 2. Maven Installation

If you don't already have Maven installed globally, you can install it by downloading the 
latest version from the [Apache Maven website](https://maven.apache.org/download.cgi).
After downloading, extract the archive and follow the [installation guide](https://maven.apache.org/install.html)
to add Maven to your system's `PATH`.
  
Alternatively, this project provides a **Maven Wrapper** (`mvnw`), so you don't need to install Maven manually. 
You can use the wrapper scripts to run Maven commands without installing it globally.

### Build the Project

To compile and package the project as a JAR, run the following command in the project's root directory:
```
./mvnw clean install
```

After building the project, you can start the Spring Boot application using the Maven Wrapper:
```
./mvnw spring-boot:run
```

The application will be accessible at http://localhost:8080 by default

Made by:
Erik Kranjec
Ella Grković
Karlo Mezdić
Anamarija Kic
Tena Osredečki
Darian Begović
Mila Podrug
