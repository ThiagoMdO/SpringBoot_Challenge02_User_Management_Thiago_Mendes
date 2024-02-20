  <h1 align="center">
    <img src = "https://github.com/ThiagoMdO/SpringBoot_Challenge02_User_Management_Thiago_Mendes/assets/128644651/e73dd9ce-0d27-41ef-b1d2-4513af40faee" style="margin-top='30px' width:"50px"">
		<p>Simple project - Back-end - User - User Registration</p>
	</h1> 
 <h1>About 🧑‍💻</h1>


**This challenge was proposed to create a user microservice and another microservice for notifications**

**The project User Registration** aims to help manage user registration in the company, improving the efficiency of control logistics control.

## What does the project do 
Currently on the platform it is possible to register, log in, edit username, edit password and consult other users In the new versions, the Notification Microservice will be increased, using messaging with RabbitMQ and the NoSQL database MongoDB
  
<br/>

## Tools 🔨
- [Java 17](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html)
- [Spring Boot](https://start.spring.io/)
- [MySQL](https://www.mysql.com/downloads/)
- [Postman](https://www.postman.com/downloads/)

Some information about the platform 
- Spring Boot Security with JWT to generate access tokens for some endpoints such as getting some users, updating user or password, at the moment only users can access them

 ## How use it ⚙️
  1 . Instal JDK 17, link abolve
  <br/>
  2 . Download Spring, link abolve
  <br/>
  <br/>
  2.2 Set the configuration Spring, below
  <br/>
 ![image](https://github.com/ThiagoMdO/SpringBoot_Challenge02_User_Management_Thiago_Mendes/assets/128644651/f36f0b95-573d-41e6-a136-0f780acb2184)
 
  2.3 . Add the dependencies below:
  
  ![Captura de tela 2023-11-13 230915](https://github.com/ThiagoMdO/SpringBoot_Challenge02_User_Management_Thiago_Mendes/assets/128644651/cd762fe2-ba50-49e7-80bb-971a0adb2000)

  3 . Fork this project
  <br/>  
  4 . Configure this code in application.properties
  <br/>
  ```bash
        spring.datasource.url=jdbc:mysql://localhost:3306/db_user_management?createDatabaseIfNotExist=true&severTimezone=UTC
        spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
        spring.datasource.username=root
        spring.datasource.password=root
        spring.jpa.properties.format_sql=true
        spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL8Dialect
        spring.jpa.properties.hibernate.format_sql=true
        spring.jpa.hibernate.ddl-auto=create
        spring.jpa.defer-datasource-initialization=true
        spring.jpa.generate-ddl=true
        spring.jpa.show-sql=true
        
        spring.sql.init.mode=always        
        
        api.security.token.secret=${JWT_SECRET:my-secret-key}
   ```
5 . So you can view the results in a database, instal MySQL in your machine, use door 3306, username=root and password=root, or change the properties file, as it was installed on your machine
<br/>
6 . To be able to use the available endpoints, it is recommended to download a collaboration platform for API development, such as Postman.
<br/>
7 . You can test API development on your platform, each endpoint addresses below:
 <br/>
  ```bash
      #Create a new user
      http://localhost:8080/v1/users

      #Login user
      http://localhost:8080/v1/login

      #Get a user
      http://localhost:8080/v1/users/{id}

      #Update a user
      http://localhost:8080/v1/users/{id}

      #Update a user password
      http://localhost:8080/v1/users/{id}/password

  ```  
  7.1 For endpoints that do not need an access token, their bodies, example.
  <br/>
  ```bash
      #Create a new user
      {
        "firstName": "John",
        "lastName": "Doe",
        "cpf": "123.453.719-31",
        "birthdate": "1999-11-12",
        "email": "john.doe@example.dom",
        "password": "secretpassword"
      }

      #Login user
      {
        "email": "john.doe@example.dom",
        "password": "secretpassword"
      }

  ``` 
  7.2 You need the token that is generated to be able to access the endpoints, and paste the token in the field available in, Bearer Token.
  ![image](https://github.com/ThiagoMdO/SpringBoot_Challenge02_User_Management_Thiago_Mendes/assets/128644651/221caf50-76cd-4922-b441-400a074870cd)

  
  7.3 The endpoints will be possible with access token for their access, their bodies, example:
  <br/>
  ```bash

      #Get a user
      #No body required, just the id in the example URL
      http://localhost:8080/v1/users/1

      #Update a user
      #You must indicate the user ID to make the authentication
      http://localhost:8080/v1/users/1
      {
        "firstName": "11JoaAnna",
        "lastName": "Silvsdasa",
        "cpf": "123.456.129-08",
        "birthdate": "2012-02-02",
        "email": "anna@exampleasd.com"
      }

      #Update a user password
      #You must indicate the user ID to make the authentication
      http://localhost:8080/v1/users/1/password
      {
        "password": "newPassword"
      }

  ``` 
