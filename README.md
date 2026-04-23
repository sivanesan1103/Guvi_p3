# Hotel Booking System

A full-stack hotel booking application built with Spring Boot (Java) backend and React frontend.

## Tech Stack

- **Backend:** Spring Boot 4.0.5, Spring Security, Spring Data JPA
- **Database:** MySQL
- **Frontend:** React, Vite, React Router, Axios
- **Authentication:** JWT (JSON Web Tokens)

## Project Structure

```
project3/
├── project3/                    # Backend (Spring Boot)
│   ├── src/main/java/com/hotel/
│   │   ├── config/              # Security & JWT config
│   │   ├── user/                # User authentication
│   │   ├── admin/               # Admin endpoints
│   │   ├── hotel/               # Hotel management
│   │   ├── room/                # Room management
│   │   ├── booking/             # Booking handling
│   │   ├── payment/             # Payment processing
│   │   └── search/              # Hotel search
│   └── src/test/                # Unit tests
└── frontend/                    # React frontend
    └── src/
        ├── api/                 # Axios configuration
        ├── components/          # Reusable components
        ├── context/             # Auth context
        └── pages/               # Page components
```

## Prerequisites

- Java 21+
- Node.js 18+
- MySQL 8.0+

## Setup

### Backend

1. Configure database in `src/main/resources/application.properties`:
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/hotel_db
spring.datasource.username=root
spring.datasource.password=your_password
```

2. Build and run:
```bash
cd project3
./mvnw spring-boot:run
```

### Frontend

```bash
cd frontend
npm install
npm run dev
```

## Default Admin Account

- Email: `admin@stayease.com`
- Password: `Admin@123`

## API Endpoints

### Authentication
- `POST /api/auth/register` - Register new user
- `POST /api/auth/login` - Login

### Hotels (Public)
- `GET /api/hotels` - List all hotels
- `GET /api/hotels/{id}` - Get hotel details

### Search
- `GET /api/search?city=&checkIn=&checkOut=` - Search available rooms

### Bookings (Authenticated)
- `POST /api/bookings` - Create booking
- `GET /api/bookings` - Get user bookings
- `DELETE /api/bookings/{id}` - Cancel booking

### Payments (Authenticated)
- `POST /api/payments` - Process payment
- `GET /api/payments/{transactionId}` - Get payment details

### Admin Endpoints (Requires ADMIN role)
- `GET /api/admin/dashboard` - Dashboard stats
- `GET /api/admin/bookings` - All bookings
- `GET /api/admin/users` - All users
- `POST /api/hotels` - Create hotel
- `PUT /api/hotels/{id}` - Update hotel
- `DELETE /api/hotels/{id}` - Delete hotel
- `POST /api/rooms` - Create room
- `PUT /api/rooms/{id}` - Update room
- `DELETE /api/rooms/{id}` - Delete room

## Security

- JWT-based authentication
- Role-based authorization (USER, ADMIN)
- Passwords encrypted with BCrypt
- CORS configured for frontend (http://localhost:5173)

## Running Tests

```bash
cd project3
./mvnw test
```

## Environment Variables

### Backend (application.properties)
- `jwt.secret` - JWT signing key
- `jwt.expiration-ms` - Token expiration time (default: 24 hours)

### Frontend
- Backend runs on `http://localhost:8080`
- Frontend runs on `http://localhost:5173`
