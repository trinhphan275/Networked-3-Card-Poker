# Networked-3-Card-Poker

## Overview
This project implements a networked version of the popular casino game 3 Card Poker using Java Sockets. The game consists of a server that manages game sessions and multiple clients that connect to it to play. Each client plays an independent game against the server. This project is developed using JavaFX and follows event-driven programming principles.

## Project Structure
This project is divided into two separate Maven projects:
- **projectThreeServer** (Server application)
- **projectThreeClient** (Client application)

## Features
- The server can handle multiple clients simultaneously, with each client playing a separate game.
- The server runs on a separate thread from the JavaFX application thread.
- Each client establishes and maintains a connection to the server on a separate thread from its JavaFX application thread.
- Java Sockets are used for communication between the server and clients.
- Each client has its own deck and game history.

## Requirements
- Java 17 or later
- Maven
- JavaFX

## Installation and Setup
1. Download the **JavaFX Maven Project** template from the course's Blackboard page.
2. Create two separate Maven projects:
   - Rename the `artifactId` in the POM file of one project to `projectThreeServer`.
   - Rename the `artifactId` in the POM file of the other project to `projectThreeClient`.
3. Implement the server and client logic following Java networking principles.

## Running the Project
### Running the Server
1. Navigate to the `projectThreeServer` directory.
2. Compile and run the server:
   ```sh
   mvn clean install
   mvn javafx:run
   ```
3. The server will start and listen on a specified port for incoming client connections.

### Running the Client
1. Navigate to the `projectThreeClient` directory.
2. Compile and run the client:
   ```sh
   mvn clean install
   mvn javafx:run
   ```
3. The client will attempt to connect to the server and start a new game session.

## Author
Trinh Phan 
