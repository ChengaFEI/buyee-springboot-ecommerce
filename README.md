# springboot-ecommerce

E-Commerce website built with Spring Boot and Amazon S3

## Author

Cheng Fei

## Introduction

Buyee is an administration system for the online shopping mall. It contains "Users", "Categories", "Brands" modules. Users can login to the system and manage data.

## Technology Stack

Front-end: Thymeleaf (HTML), Bootstrap (CSS), jQuery (Javascript) </br>
Back-end: Spring Framework (Java) -- Spring Data JPA, Spring Security, Spring RESTful API </br>
Database: MySQL, Amazon S3 </br>
Deploy: Heroku

## Authentication

The authentication system is secured with Spring Security framework. Users can only access modules open to their roles.

Users module requires Admin role <br />
Categories module requires Admin/Editor role <br />
Brands module requires Admin/Editor role

## Usage

Please open the root folder buyee-app in an IDE and start the Spring Boot Application in the buyee-web/buyee-backend folder. Then please open the following URL:

```bash
open http://localhost:8080/buyee-admin
```

Then, you are in the login webpage. Please use the following admin account to login.

```bash
Username: test_user@test.com
Password: test2022
```

Then please enjoy exploring the buyee e-commerce app!
