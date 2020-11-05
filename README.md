## Table of contents
* [General info](#general-info)
* [Technologies](#technologies)
* [Setup](#setup)

## General info
This is a telegram bot, that can search and add user to DB

License information can be found in the LICENSE file.

## Technologies
Project is created with:
* Java 11
* maven-4.0.0
* java-telegram-bot-api-full
* mysql-connector-java-8.0.20
* log4j-core:2.11.1
* log4j-api:2.11.1

## Setup
To run this project needs:

1) register a bot in Telegram-> get a token
2) run MySQL server -> create a table for example: /src/main/resources/Data
3) download the code -> set your token
4) set your login, password of the table in property file: /src/main/resources/config.properties
5) compile and run the code
