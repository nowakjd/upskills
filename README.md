# upskills.sii.pl - conference management app

<table>
<tr>
<td>
A project to develop best app for managing conferences - online or local ones
</td>
</tr>
</table>

## Table of contents

* [About the components](#about-the-components)
* [Developing locally](#developing-locally)
* [Setup](#setup)
* [Deploying the app](#deploying-the-app)
* [Contact us](#contact-us)

## About the components

This app is developed with [Java](https://www.java.com/), uses [Spring](https://spring.io/) framework, for database
engine we decided to use [PostgreSQL](https://www.postgresql.org/) version 14.1.

## Developing locally

If you would like to develop locally we recommend using docker, it is used for starting database server.

### Setup

You need to set file .env (you should set username and password) and build maven with this command:
./mvnw clean package then use docker-compose up -d db and when db is ready you can run docker-compose up, which is a
part of Docker desktop [Docker](https://www.docker.com/). Then you can access database from `http://localhost:8090`.
Also, database credentials are needed, report to our team for the appropriate database data.

### Jacoco plugins

The plugin has been added as a dependency to the pom.xml file
Adding the @Generated annotation over a particular method will exclude it from the report.
More information on method and class exclusion: https://www.baeldung.com/jacoco-report-exclude

### Deploying the app

To deploy to **upskills.sii.pl** you need to wait - we will add app server soon.

### Contact us

Use this [Email](mailto:email@example.com)

