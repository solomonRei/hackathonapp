# Diaspora Dialog

## About the Project

Diaspora Dialog is an application by Team TechnoBees (team 9), aimed at enhancing the connection between the Moldovan diaspora and their homeland. This platform facilitates meaningful and direct communication between diaspora members and Moldova government officials.

### Key Features

- **Online Meetings with Officials**: Participate in online meetings with various Moldovan government officials.
- **Community-driven Agenda**: Users can submit questions prior to meetings and vote on questions submitted by others. The most popular questions will shape the meeting's agenda.
- **Engage and Connect**: A unique opportunity for the diaspora to directly engage with officials, fostering a stronger bond with Moldova.

## Installation Instructions

Diaspora Dialog can be easily set up using Docker. Below are the steps to install and run the application using Docker Compose.

1. Ensure you have Docker and Docker Compose installed on your system.
2. Create a Docker Compose file with the following configuration:

```yaml
version: '3'

services:
  postgres:
    image: postgres:latest
    container_name: postgres_db_new
    ports:
      - "5432:5432"
    environment:
      - POSTGRES_PASSWORD=postgres
      - POSTGRES_USER=postgres
      - POSTGRES_DB=postgres
    networks:
      - elc_wise

  app:
    image: smeloved/disaporadialog:v1.0.20
    container_name: app_new
    ports:
      - "8080:8080"
    depends_on:
      - postgres
    networks:
      - elc_wise
    environment:
      - POSTGRES_URL=jdbc:postgresql://postgres_db_new:5432/postgres
      - POSTGRES_USER=postgres
      - POSTGRES_PASSWORD=postgres

networks:
  elc_wise:
```

3. Run the application using the command:
   ```
   docker-compose up
   ```
4. Once the services are up, access the application at `http://localhost:8080`.
