>*Disclaimer:* 
>- *This project main purpose was a learning experience to tackle Thymeleaf and HTMX integration with Spring Boot.*
>- *It may contain unpolished, experimental or rushed code that needs to be refactored and modernized.*

# POE Spring App

A Java [Spring Boot](https://spring.io/projects/spring-boot) web application that acts as a bridge between a user and two third party APIs [[1]](https://www.pathofexile.com/developer/docs/reference) [[2]](https://github.com/5k-mirrors/misc-poe-tools/blob/master/doc/poe-ninja-api.md).  
This application allows the user to fetch and store data locally, for a later offline access.  
The data is made available to the user through two means:
- A REST API exchanging data using the JSON format.  
- A simple yet modern graphical user interface.  

The persistence layer is managed by [Spring Data JPA](https://spring.io/projects/spring-data-jpa) and [Hibernate](https://hibernate.org).  
The JSON serialization/deserialization is handled by [Jackson](https://github.com/FasterXML/jackson).  


The frontend is built using [Thymeleaf](https://www.thymeleaf.org) coupled with [Thymeleaf Layout Dialect](https://ultraq.github.io/thymeleaf-layout-dialect).  
The frontend form-based authentication process is managed by [Spring Security](https://docs.spring.io/spring-security/reference/index.html) using a custom authentication provider (to be eventually replaced by OATH 2).  
Reloading partial views of a page is made possible using [HTMX](https://htmx.org).  
The styling is done with the Minimal CSS Framework [Pico.css](https://picocss.com).  
[Bootstrap Table](https://bootstrap-table.com) adds sorting and searching capabilities to some tables.  
Frontend dependencies, such as JavaScript and CSS libraries, are managed through [Maven](https://maven.apache.org) via [Webjars](https://www.webjars.org).  


[Lombok](https://projectlombok.org) is used to reduce boilerplate code.  
The mapping between Java bean types is done using [MapStruct](https://mapstruct.org).  
The project's build is managed by [Maven](https://maven.apache.org).  

## Features:
The application allows the user to access:
- The active leagues.
- The characters of his account.
- The inventory of a character (2D view).
- The stash tabs of his account.
- The content of a stash tab (table view and 2D view).
- The market overview of a specified league.
- The ladder of a specified league.

## Built with:
- Java 17
- [Spring Boot 2.7.8](https://spring.io/projects/spring-boot)
- [Spring Security](https://docs.spring.io/spring-security/reference/index.html)
- [Spring Data JPA](https://spring.io/projects/spring-data-jpa)
- [Hibernate](https://hibernate.org)
- [Jackson](https://github.com/FasterXML/jackson)
- [Thymeleaf](https://www.thymeleaf.org)
- [Thymeleaf Layout Dialect](https://ultraq.github.io/thymeleaf-layout-dialect)
- [HTMX](https://htmx.org)
- [Pico.css](https://picocss.com)
- [Bootstrap Table](https://bootstrap-table.com)
- [Webjars](https://www.webjars.org)
- [Lombok](https://projectlombok.org)
- [MapStruct](https://mapstruct.org)
- [Maven](https://maven.apache.org)

## Screenshot:
![poe-spring-app-screenshot-1](https://user-images.githubusercontent.com/76587083/220020146-547c4c86-8493-4ce6-8141-fbcb4e51f5bf.png)  

![poe-spring-app-screenshot-2](https://user-images.githubusercontent.com/76587083/220020148-8c83adbd-338a-49fc-9142-8f978ba51abd.png)  

![poe-spring-app-screenshot-3](https://user-images.githubusercontent.com/76587083/220020149-d0b84e19-46fa-4842-8ac3-281e5cdb790a.png)  

![poe-spring-app-screenshot-4](https://user-images.githubusercontent.com/76587083/220021162-dcb97e7c-d487-4fdc-9d90-c5022ebcda60.png)  


