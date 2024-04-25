
# RentalApp

RentalApp is a Java Spring Boot application for managing tool rentals. It provides RESTful endpoints for CRUD operations on tools and allows users to check out tools for rental, generating rental agreements.

## Table of Contents
- [Getting Started](#getting-started)
- [Prerequisites](#prerequisites)
- [Installation](#installation)
- [Database Configuration](#database-configuration)
- [API Endpoints](#api-endpoints)
- [Testing](#testing)
- [Sample Data](#sample-data)

## Getting Started

To run the application locally, follow these steps:

### Prerequisites

- Java JDK (11 or higher)
- Maven
- H2 Database (embedded)

### Installation

1. Clone the repository:

    ```bash
    git clone https://github.com/din-jk/rentall-app.git
    ```

2. Navigate to the project directory:

    ```bash
    cd RentalApp
    ```

3. Build the application using Maven:

    ```bash
    mvn clean install
    ```

4. Run the application:

    ```bash
    java -jar target/RentalApp.jar
    ```

The application will start on `http://localhost:8080`.

## Database Configuration

The application uses an H2 in-memory database by default. Database properties can be configured in the `application.properties` file.

```properties
spring.datasource.url=jdbc:h2:mem:rentalappdb
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=password
spring.jpa.database-platform=org.hibernate.dialect.H2Dialect
```

## API Endpoints

### Tools

-`GET /api/v1/tool`: Retrieve all tools.<br/>
-`GET /api/v1/tool/{toolId}`: Retrieve a tool by ID.<br/>
-`GET /api/v1/tool/code/{toolCode}`: Retrieve a tool by code.<br/>
-`POST /api/v1/tool`: Create a new tool.<br/>
-`PUT /api/v1/tool/{toolId}`: Update a tool by ID.<br/>
-`DELETE /api/v1/tool/{toolId}`: Delete a tool by ID.

### Tool Rental

-`POST /api/v1/tool/checkoutTool`: Checkout a tool for rental.

  | Parameter              | Type    | Description                                 |
  |------------------------|---------|---------------------------------------------|
  | rentalDays             | Integer | Number of days the tool is rented for.      |
  | discountPercentage     | Integer  | Discount percentage applied to the rental.  |
  | toolCode               | String  | Code of the tool to be rented.              |
  | checkoutDate           | Date    | Date when the tool is checked out for rental in (YYYY-MM-DD) format. |

  **Request Body Example:**
  ```json
  {
    "rentalDays": 5,
    "discountPercentage": 10.0,
    "toolCode": "TOOL001",
    "checkoutDate": "2024-04-25"
  }
```
**Response Example:**
  ```json
  {
  "toolCode": "TOOL001",
  "rentalDays": 5,
  "checkoutDate": "2024-04-25",
  "dueDate": "2024-04-30",
  "dailyRentalCharge": 1.99,
  "discountPercentage": 10.0,
  "discountAmount": 9.95,
  "preDiscountCharge": 9.95,
  "finalCharge": 0.0
}

```

## Testing

Unit tests are provided to ensure the functionality of the `RentalAppService`. You can run the tests using Maven:

```bash
mvn test
```

## Sample Data

Upon application startup, sample tool data is loaded into the database using ToolData.java. You can customize or extend this data as needed.
