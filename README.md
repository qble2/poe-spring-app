# POE Spring 

A Java [Spring Boot](https://spring.io/projects/spring-boot) web application that acts as a proxy to a [Third party API](https://www.pathofexile.com/developer/docs/reference).  
The application allows the user to fetch data from a [Third party API](https://www.pathofexile.com/developer/docs/reference) and stores it for an offline access.  
The data is made available to the user through two means:
- A REST API exchanging data using the JSON format.  
- A simple yet modern graphical user interface.  

The persistence layer is managed by [Spring Data JPA](https://spring.io/projects/spring-data-jpa) and [Hibernate](https://hibernate.org).  
The JSON serialization/deserialization is handled by [Jackson](https://github.com/FasterXML/jackson).  


The frontend is built using [Thymeleaf](https://www.thymeleaf.org) coupled with [Thymeleaf Layout Dialect](https://ultraq.github.io/thymeleaf-layout-dialect).  
The frontend form-based authentication process is managed by [Spring Security](https://docs.spring.io/spring-security/reference/index.html) using a custom authentication provider (to be eventually replaced by OATH 2).  
Reloading partial views of a page is made possible using [HTMX](https://htmx.org).  
The styling is done with the Minimal CSS Framework [Pico.css](https://picocss.com).  
Frontend dependencies, such as JavaScript and CSS libraries, are managed through [Maven](https://maven.apache.org) via [Webjars](https://www.webjars.org).  


[Lombok](https://projectlombok.org) is used to reduce boilerplate code.  
The mapping between Java bean types is done using [MapStruct](https://mapstruct.org).  
The project's build is managed by [Maven](https://maven.apache.org).  

## Features:
The application allows the user to fetch
- The active leagues
- His account's characters
- The items of a character
- His stash tabs
- The items of a stash tab

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
- [Webjars](https://www.webjars.org)
- [Lombok](https://projectlombok.org)
- [MapStruct](https://mapstruct.org)
- [Maven](https://maven.apache.org)
