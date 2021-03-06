## Inventory Management
The goal of this project is to develop an application which allows store owners to organize and manage their Product data. This repository contains REST APIs developed using Java and Spring boot, and MySQL is utilized for database management.

## Requirements

### Prerequisites to run the application
 
## 1. Install open JDK 11
	$ java --version

	openjdk version "11.0.1" 2018-10-16
	OpenJDK Runtime Environment 18.9 (build 11.0.1+13)
	OpenJDK 64-Bit Server VM 18.9 (build 11.0.1+13, mixed mode)
 
## 2. Install MySQL Community Server
	$ mysql --version (MacOs)

	mysql  Ver 8.0.22 for macos10.15 on x86_64
 
	$ mysqld --version (Ubuntu)

	mysqld  Ver 8.0.22-0ubuntu0.18.04.1 for Linux on x86_64
 
## 3. Set up Database
a) Connect to MySQL using root and add new user

   	$ mysql -u root -p
   
  	$ mysql> CREATE USER admin@'localhost' IDENTIFIED BY 'admin@123'; (Match the username and password from src/main/resources/application.yml file)
   
   	$ mysql> create database inventory_db;
   
   	$ mysql> GRANT ALL ON inventory_db.* TO 'admin'@'localhost';
   
   	$ mysql> FLUSH PRIVILEGES;
 
 b) Initialize Database
 
	$ mysql -u admin -p inventory_db < /path/inventory_db.sql
 
## 4. Start the Application

   	$ java -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=0.0.0.0:8000 -jar /path/inventory-management-1.0-SNAPSHOT.jar
 
## 5. Verify using below CURL

	$ curl --location --request GET 'http://localhost:8080/inventory-service/api/v1.0/health-check'
	  Response: success
 
### Thank You!
