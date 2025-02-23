RentalBooks API Documentation

Overview

RentalBooks is a book rental system that allows users to register, log in, save books, update books, rent books, and return books.

Base URL

http://localhost:30080

Authentication

User Registration

Endpoint:

POST /auth/register

Body (JSON):

{
    "userName": "Ram",
    "email": "ram@test.com",
    "password": "ram123",
    "role": "USER"
}

User Login

Endpoint:

POST /auth/login

Body (JSON):

{
    "userName": "Ram",
    "password": "ram123"
}

Book Management

Save a Book

Endpoint:

POST /books/saveBook

Body (JSON):

{   "name": "Kj",
    "author": "Ram",
    "genre": "Test",
    "available": true,
    "users": []
}

Update a Book

Endpoint:

PUT /books/update/{bookId}

Authorization: Basic Auth
Body (JSON):

{  
    "name": "Scott Boland 2",
    "author": "F. Scott Boland",
    "genere": "Classic Fiction",
    "is_available": true,
    "users": null
}

Delete a Book

Endpoint:

DELETE /books/delete/{bookId}

Book Rental System

Rent a Book

Endpoint:

PUT /books/rent/{customerId}

Authorization: Basic Auth
Body (JSON):

{
    "customerId": 3,
    "bookIds": [3]
}

Return a Book

Endpoint:

PUT /books/return/{customerId}

Authorization: Basic Auth
Body (JSON):

{
    "customerId": 3,
    "bookIds": [3]
}

Steps to Run JAR Files

Build the JAR File:

If using Maven:

mvn clean package

If using Gradle:

gradle build

Navigate to the Target/Build Directory:

cd target  # For Maven
cd build/libs  # For Gradle

Run the JAR File:

java -jar rentalbooks.jar

(Replace rentalbooks.jar with the actual JAR file name generated during the build process.)

Verify the Application is Running:

Open a browser and navigate to:

http://localhost:30080

Check API endpoints using Postman or cURL.

Notes

All API calls requiring authentication use Basic Auth.

Ensure correct JSON format in the request body.

Update and delete operations require valid bookId or customerId parameters.

More features like book search and user role management can be added in future updates.

