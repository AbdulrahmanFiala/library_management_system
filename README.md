# Library Management System

A comprehensive REST API for managing library operations including books, patrons, and borrowing records. Built with Spring Boot 3.2.5, this system provides secure authentication using JWT tokens and supports full CRUD operations for library management.

## Features

- **Book Management**: Create, read, update, and delete books with details like title, author, publication year, and ISBN
- **Patron Management**: Manage library patrons with contact information
- **Borrowing System**: Track book borrowing and returning operations
- **JWT Authentication**: Secure API access with JSON Web Tokens
- **Data Validation**: Comprehensive input validation using Jakarta Bean Validation
- **Caching**: Built-in caching for improved performance
- **AOP Logging**: Aspect-oriented logging for better monitoring
- **MySQL Database**: Persistent data storage with JPA/Hibernate

## Tech Stack

- **Backend**: Spring Boot 3.2.5
- **Java Version**: 21
- **Database**: MySQL
- **Security**: Spring Security with JWT
- **ORM**: Spring Data JPA with Hibernate
- **Build Tool**: Maven
- **Validation**: Jakarta Bean Validation
- **Logging**: Spring AOP
- **Caching**: Spring Cache

## Prerequisites

Before running this application, make sure you have the following installed:

- **Java 21** or higher
- **Maven 3.6+**
- **MySQL 8.0+**
- **Git**

## Setup Instructions

### 1. Clone the Repository

```bash
git clone <repository-url>
cd library_management_system
```

### 2. Database Setup

1. Create a MySQL database for the application:
```sql
CREATE DATABASE library_management_system;
```

2. Create a `.env` file in the root directory with your database credentials:
```env
MYSQL_DATABASE="library_management_system"
MYSQL_USER=your_username
MYSQL_PASSWORD=your_password
JWT_SECRETKEY="your_secretkey"
```

### 3. Build and Run

#### Option 1: Using Maven Wrapper (Recommended)

```bash
# Build the project
./mvnw clean install

# Run the application
./mvnw spring-boot:run
```

#### Option 2: Using Maven (if installed globally)

```bash
# Build the project
mvn clean install

# Run the application
mvn spring-boot:run
```

### 4. Application Access

The application will start on **port 7000** with the base path `/api`. 

- **Base URL**: `http://localhost:7000/api`
- **Health Check**: The application will be available at `http://localhost:7000/api`

## API Documentation

### Authentication

All API endpoints (except authentication endpoints) require a valid JWT token in the Authorization header.

#### Register a New User
```http
POST /api/auth/register
Content-Type: application/json

{
    "firstname": "John",
    "lastname": "Doe",
    "email": "john.doe@example.com",
    "password": "securePassword123"
}
```

#### Login
```http
POST /api/auth/authenticate
Content-Type: application/json

{
    "email": "john.doe@example.com",
    "password": "securePassword123"
}
```

**Response:**
```json
{
    "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
}
```

### Book Management

All book endpoints require authentication. Include the JWT token in the Authorization header.

#### Get All Books
```http
GET /api/books
```

#### Get Book by ID
```http
GET /api/books/{id}
```

#### Create a New Book
```http
POST /api/books
Content-Type: application/json

{
    "title": "The Great Gatsby",
    "author": "F. Scott Fitzgerald",
    "publicationYear": 1925,
    "isbn": "9780743273565"
}
```

#### Update a Book
```http
PUT /api/books/{id}
Content-Type: application/json

{
    "title": "The Great Gatsby (Updated)",
    "author": "F. Scott Fitzgerald",
    "publicationYear": 1925,
    "isbn": "9780743273565"
}
```

#### Delete a Book
```http
DELETE /api/books/{id}
```

### Patron Management

All patron endpoints require authentication. Include the JWT token in the Authorization header.

#### Get All Patrons
```http
GET /api/patrons
```

#### Get Patron by ID
```http
GET /api/patrons/{id}
```

#### Create a New Patron
```http
POST /api/patrons
Content-Type: application/json

{
    "name": "Jane Smith",
    "email": "jane.smith@example.com",
    "phoneNumber": "+1234567890"
}
```

#### Update a Patron
```http
PUT /api/patrons/{id}
Content-Type: application/json

{
    "name": "Jane Smith (Updated)",
    "email": "jane.smith@example.com",
    "phoneNumber": "+1234567890"
}
```

#### Delete a Patron
```http
DELETE /api/patrons/{id}
```

### Borrowing Operations
All borrow endpoints require authentication. Include the JWT token in the Authorization header.

#### Borrow a Book
```http
POST /api/borrow/{bookId}/patron/{patronId}
```

#### Return a Book
```http
PUT /api/return/{bookId}/patron/{patronId}
```

## Testing

Run the test suite using Maven:

```bash
./mvnw test
```

The project includes comprehensive unit tests for controllers and services.

## Contributing

1. Fork the repository
2. Create a feature branch
3. Make your changes
4. Add tests for new functionality
5. Submit a pull request

## License

This project is licensed under the MIT License.