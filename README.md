# Omni Trade Application

This project is part of the final assignment submission for the Graduate
Certificate in Architecting Scalable Systems

## The Tech Stack™

### Backend

- [Spring Boot](https://spring.io/) for Orders and Accounts Service
- [Helidon SE](https://helidon.io/) for Forums Service
- [MySQL](https://www.mysql.com/) for Database
- [ElasticMQ](https://github.com/softwaremill/elasticmq) as a replacement of AWS
  SQS for local development/vendor-neutral configuration

### Frontend

- [VueJS](https://vuejs.org/)

### Cloud Platforms and Infrastructure

- AWS SQS
- Firebase Hosting
- GitHub Actions
- Google App Engine
- Google Cloud SQL

## Development Setup

### Common (Backend + Frontend)

- [Docker](https://www.docker.com/)

### Backend

- [JDK 11+](https://www.oracle.com/technetwork/java/javase/downloads/jdk11-downloads-5066655.html)
- [Maven 3.8+](https://maven.apache.org/download.html)
- [Helidon CLI](https://helidon.io/) - only for Forums service

If you are using VS Code, the
[Lombok Annotations](https://marketplace.visualstudio.com/items?itemName=GabrielBB.vscode-lombok)
extension is required.

### Frontend

- [Node.js v16 LTS](https://nodejs.org/en/download/)
- [Vue CLI](https://cli.vuejs.org/)

### DevOps and Deployment (optional)

- [Google Cloud CLI](https://cloud.google.com/sdk/docs/install)

## Development Loop

Simply run `docker-compose -f ./docker-compose.yml up -d` in the root project
folder to start the containers.

To develop on the frontend, simply run `npm run serve` in the `frontend` folder.
(Assuming you've already installed the dependencies with `npm install`.)
