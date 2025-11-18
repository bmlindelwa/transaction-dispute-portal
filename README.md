# Transaction Dispute Portal

A full-stack production-grade application for customers to raise transaction disputes on transactions.

Built with **Spring Boot 3 + React 19 + PostgreSQL + Docker**.

## Features
- User registration & login (uses Spring Security session)
- Create disputes on transactions
- View dispute history and real-time status
- File storage in `uploads/` with cleanup on dispute deletion
- Comprehensive validation and error handling
- 85%+ test coverage

## Tech Stack
- Backend: Java 17, Spring Boot 3, Spring Security + JWT, Spring Data JPA, Hibernate
- Frontend: React 18 + Vite + Tailwind CSS
- Database: PostgreSQL
- Testing: JUnit 5, Mockito, MockMvc
- Containerization: Docker + Docker Compose

## Assumptions
- Users/customers already exist with their transactions
- User/customer logs in and sees their transactionns on the landing page
- User can click on 'Dispute' button to dispute a Transaction
- User can view history of hisr/her disputed transactions on the 'Dispute History' tab
- On the landing page 'Transactions' tab user can dispute transaction that has not been disputed before
- I used default login page
- App does not provide option to register new user on the frontend (assuming that existing users can login)
- The endpoint to register new users exist but can be accessed via POSTMAN (http://localhost:8080) - JSON body {
    "username": "TestUser",
    "password": "mypassword123",
	"email": "TestUser@example.com"
}

## How to login
- Username + password
- Preloaded users: username:- Bulelani, password:- password
				  username:- Max, password:- password
				  username:- Munkie, password:- password
- You can you any of these users to login, they all have preloaded transactions
- When Docker is up and running the application can be accessed at http://localhost:8080/

## Modifications
- Would have been nice to have admin dashboard to view disputes and perform approvals with comments
- Would have been also nice to allow users to be able to edit their disputes or cancel/delete

## Prerequisites
- Docker & Docker Compose
- Node.js 18+ (For the frontend

## Quick Start (Recommended - Docker)

```bash
# Clone and run everything with one command
-git clone <the-repo>
-cd transaction-dispute-portal

# Start PostgreSQL + Backend
docker-compose up --build