
## User Management

A full-stack web application for managing users. The backend is built with Java Spring Boot, and the frontend uses React with TypeScript and Redux Toolkit. The application supports creating, deleting, and retrieving users.

### 1. Clone the project:
git clone https://github.com/oriaozeri/user-management

### 2. Build the project and run the server:
Build the project:
./gradlew clean build

Run the application:
From your IDE or Using the terminal:
./gradlew bootRun

http://localhost:8080

### 3. Build and run the app:

npm install
npm run dev

http://localhost:5173

### cURL Examples for Testing API

Create User:
curl --location --request POST 'http://localhost:8080/users/add' \
--header 'Content-Type: application/json' \
--data-raw '{
  "firstName": "Oria",
  "lastName": "Ratzabi",
  "password": "abjcsd",
  "emailAddress": "oriaozה@gmail.com"
}'

Get All Users:
curl --location --request GET 'http://localhost:8080/users' \
--header 'Content-Type: application/json'

Delete User:
Replace the ID with a valid user ID
curl --location --request DELETE 'http://localhost:8080/users/delete/a83f9778-b563-4493-ae76-020bbde74aa0' \
--header 'Content-Type: application/json'

Enjoy :)
