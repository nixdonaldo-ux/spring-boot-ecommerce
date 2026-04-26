# E-Commerce API

A powerful REST API for a large-scale e-commerce system built with Spring Boot.

## Features

- **User Authentication & Authorization**: JWT-based authentication with role-based access control
- **Product Management**: CRUD operations for products with categories and images
- **Order Management**: Complete order lifecycle management
- **Admin Panel**: Administrative endpoints for system management
- **Security**: Spring Security with JWT tokens
- **Database**: H2 in-memory database (configurable for production)
- **Validation**: Comprehensive input validation
- **Error Handling**: Global exception handling with meaningful error messages

## Technologies Used

- Spring Boot 3.2.0
- Spring Security
- Spring Data JPA
- JWT (JSON Web Tokens)
- H2 Database
- Maven
- Java 17

## Getting Started

### Prerequisites

- Java 17 or higher
- Maven 3.6+

### Installation

1. Clone the repository:
```bash
git clone https://github.com/nixdonaldo-ux/spring-boot-ecommerce.git
cd spring-boot-ecommerce
```

2. Build the project:
```bash
mvn clean install
```

3. Run the application:
```bash
mvn spring-boot:run
```

The API will be available at `http://localhost:8080`

## API Endpoints

### Authentication

- `POST /api/auth/register` - Register a new user
- `POST /api/auth/login` - Login and get JWT token

### Products (Public)

- `GET /api/products` - Get all products (paginated)
- `GET /api/products/{id}` - Get product by ID
- `GET /api/products/search?keyword={keyword}` - Search products
- `GET /api/products/category/{categoryId}` - Get products by category
- `GET /api/products/price-range?minPrice={min}&maxPrice={max}` - Get products by price range

### Products (Admin Only)

- `POST /api/products` - Create new product
- `PUT /api/products/{id}` - Update product
- `DELETE /api/products/{id}` - Delete product
- `GET /api/products/low-stock?threshold={threshold}` - Get low stock products

### Orders

- `POST /api/orders` - Create new order (User)
- `GET /api/orders` - Get user's orders (User)
- `GET /api/orders/{id}` - Get order by ID (User/Admin)
- `DELETE /api/orders/{id}` - Cancel order (User)
- `PUT /api/orders/{id}/status` - Update order status (Admin)

### Admin

- `GET /api/admin/users` - Get all users
- `GET /api/admin/users/{id}` - Get user by ID
- `PUT /api/admin/users/{id}` - Update user
- `DELETE /api/admin/users/{id}` - Delete user
- `GET /api/admin/dashboard/stats` - Get dashboard statistics

## Authentication

Most endpoints require authentication. Include the JWT token in the Authorization header:

```
Authorization: Bearer <your-jwt-token>
```

## Database

The application uses H2 in-memory database by default. You can access the H2 console at:
`http://localhost:8080/h2-console`

- JDBC URL: `jdbc:h2:mem:ecommerce`
- Username: `sa`
- Password: `password`

## Configuration

Key configuration properties in `application.properties`:

- `jwt.secret`: Base64 encoded secret key for JWT
- `jwt.expiration`: JWT token expiration time in milliseconds
- Database configuration
- Server port

## Project Structure

```
src/main/java/com/ecommerce/
├── config/           # Configuration classes
├── controller/       # REST controllers
├── dto/             # Data Transfer Objects
├── entity/          # JPA entities
├── exception/       # Exception handlers
├── repository/      # Data access layer
├── security/        # Security configuration
└── service/         # Business logic
```

## Default Users

After startup, the system creates default roles. You can register users through the API.

## API Documentation

The API includes comprehensive validation and error handling. All responses follow a consistent format.

### Error Response Format

```json
{
  "timestamp": "2024-01-01T12:00:00",
  "status": 400,
  "error": "Bad Request",
  "message": "Validation failed",
  "errors": {
    "field": "error message"
  }
}
```

## Contributing

1. Fork the repository
2. Create a feature branch
3. Commit your changes
4. Push to the branch
5. Create a Pull Request

## License

This project is licensed under the MIT License.