#### Requirements

## Prerequisites to run the application
 
## 1. Install open JDK 11
$ java --version

$ openjdk version "11.0.1" 2018-10-16
  OpenJDK Runtime Environment 18.9 (build 11.0.1+13)
  OpenJDK 64-Bit Server VM 18.9 (build 11.0.1+13, mixed mode)
 
## 2. Install MySQL Community Server
$ mysql --version (MacOs)
$ mysql  Ver 8.0.22 for macos10.15 on x86_64
 
$ mysqld --version (Ubuntu)
$ mysqld  Ver 8.0.22-0ubuntu0.18.04.1 for Linux on x86_64
 
## 3. Set up Database
a) Connect to MySQL using root and add new user
   $ mysql -u root -p
   $ Enter password: *****
   $ CREATE USER admin@'localhost' IDENTIFIED BY 'admin@123';
   $ GRANT ALL ON *.* TO 'admin'@'localhost';
   $ FLUSH PRIVILEGES;
(Match the username and password from src/main/resources/application.yml)

b) Connect to MySQL using new user
   $ mysql -u admin -p
   $ Enter password: *****
   $ create database inventory_db;
 
 C) Initialize Database
	$ mysql -u admin -p inventory_db < /path/inventory_db.sql
	$ Enter password: *****
 
## 4. Start the Application
   $ java -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=0.0.0.0:8000 -jar inventory-management-1.0-SNAPSHOT.jar
 
## 5. Verify using below CURL
	$ curl --location --request GET 'http://localhost:8080/inventory-service/api/v1.0/health-check'
	Response: success
 
### Thank You!
