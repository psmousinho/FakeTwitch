# FakeTwitch

## About
This application is an REST API that simulates the functionalities of the gameplay streaming service called Twitch. 
A registered user can follow other users or categories(games) and, from that, have acess to livestreams related to their interest.

## Features

The API contains the following features:

- Channel
  - Create
  - List
  - Detail
  - Update
  - Delete
  - List active LiveStreams from channels followed
  - List past LiveStreams from channels followed
  - List categories followed
  - List all followed channels

- Connection
  - Create
  - List
  - List by type
  - Detail
  - Update
  - Delete

- Category
  - Create
  - List
  - List by genre
  - Detail
  - Update
  - Delete
  - List Followers
  - List Streams

- LiveStream
  - Create
  - List All (by status)
  - List by owner (and by status)
  - Update
  - Delete

- Twitch  
  - Search by name
  - Search by id

You can test the API features by using the documentation built automatically by Swagger. Run the project and acess `localhost:8080/swagger-ui.html`.

## Setup
The dependencies of this project are handle by Maven. So if you're using an IDE, it will probably handle the setup for you.
If you want to setup by yourself you can run the command `./mvnw spring-boot:run` in the root directory after installing Maven.

## Tools and technologies
This project was developed using the following tools and technologies:
- Spring Boot
- PostgreSQL ([ElephantSQL](https://www.elephantsql.com/))
- Authentication by JWT
- Swagger
