<p align="center">

![Java](https://img.shields.io/badge/Java-21-orange?style=for-the-badge)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.x-brightgreen?style=for-the-badge)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-Database-336791?style=for-the-badge)
![Supabase](https://img.shields.io/badge/Supabase-Backend-3ECF8E?style=for-the-badge)
![Railway](https://img.shields.io/badge/Railway-Deployment-0B0D0E?style=for-the-badge)
![Vercel](https://img.shields.io/badge/Vercel-Frontend-black?style=for-the-badge)

</p>


# 🛒 CreatorStore

A **Full-Stack E-Commerce Store Management System** built using **Spring Boot, Spring Data JPA, Hibernate, PostgreSQL, Supabase, and Vanilla JavaScript**.

CreatorStore enables administrators to manage products, maintain inventory, process customer orders, and track delivery status through a clean and responsive admin dashboard.

This project helped me gain practical experience with **REST APIs, Spring Boot, JPA, Hibernate, PostgreSQL, Supabase, deployment, and full-stack development.**

---

# 🚀 Live Demo

### 🌐 Frontend

https://spring-boot-creator-store.vercel.app/

### ⚙️ Backend API

https://springboot-creatorstore-production.up.railway.app/

---

# 📸 Screenshots

## Dashboard

![Dashboard](screenshots/dashboard.png)

## Order History

![Order History](screenshots/order-history.png)

---

# ✨ Features

## 📦 Product Management

* Add Products
* Update Products
* Delete Products
* Manage Inventory
* Automatic Stock Updates

---

## 📊 Dashboard

* Total Products
* In-Stock Products
* Out-of-Stock Products
* Inventory Overview

---

## 🛒 Order Management

* Place Customer Orders
* Auto Calculate Total Amount
* Automatic Inventory Deduction
* Prevent Invalid Orders

---

## 🚚 Order History

* View Previous Orders
* Track Order Status
* Update Status

```
Pending
   ↓
Confirmed
   ↓
Delivered
```

---

# 🛠 Tech Stack

## Backend

- Java 21
- Spring Boot
- Spring Web
- Spring Data JPA
- Hibernate
- REST APIs
- Maven

## Database

- PostgreSQL
- Supabase

## Frontend

- HTML5
- CSS3
- JavaScript (Vanilla)

## Deployment

- Railway (Backend)
- Vercel (Frontend)
- Supabase (Database)

---

# 📂 Project Structure

```text
CreatorStore
│
├── frontend
│   ├── css
│   ├── js
│   └── index.html
│
├── src
│   └── main
│       ├── java
│       │    └── org/pranay/creatorstore
│       │         ├── controller
│       │         ├── service
│       │         ├── repository
│       │         ├── entity
│       │         └── dto
│       │
│       └── resources
│
├── pom.xml
└── README.md
```

---

# 🔌 REST API

## Products

| Method | Endpoint             |
| ------ | -------------------- |
| GET    | `/api/products`      |
| POST   | `/api/products`      |
| PUT    | `/api/products/{id}` |
| DELETE | `/api/products/{id}` |

---

## Orders

| Method | Endpoint                  |
| ------ | ------------------------- |
| GET    | `/api/orders`             |
| POST   | `/api/orders`             |
| PATCH  | `/api/orders/{id}/status` |

---

# ⚙️ Getting Started

## Clone Repository

```bash
git clone https://github.com/PRANAYKHOJARE/SpringBoot-CreatorStore.git
```

## Backend

```bash
cd CreatorStore
mvn spring-boot:run
```

## Configure Database

Update your `application.properties`.

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/creatorstore
spring.datasource.username=postgres
spring.datasource.password=your_password
```

---

# 💡 Challenges Solved

* Fixed Hibernate Lazy Loading Exception
* Resolved CORS Issues
* Connected Railway with PostgreSQL (Supabase)
* Solved JSON Serialization Problems
* Managed Frontend Deployment on Vercel
* Implemented Automatic Inventory Updates

---

# 📚 What I Learned

* Building REST APIs using Spring Boot
* Spring Data JPA & Hibernate
* Entity Relationships
* PostgreSQL Integration with Supabase
* Frontend & Backend Communication
* Deployment on Railway & Vercel
* Debugging Production Issues
* Clean Project Architecture

---

# 🚀 Future Improvements

* Spring Security
* JWT Authentication
* Role-Based Access Control
* Customer Login
* Product Search
* Pagination
* Analytics Dashboard
* Payment Gateway
* Docker Support

---

# 👨‍💻 Author

**Pranay Khojare**

Aspiring Java Backend Developer



* 🌐 Portfolio: https://pranaykhojare-portfolio.vercel.app/
* 💼 LinkedIn: https://www.linkedin.com/in/pranay-khojare-a23505211/
* 💻 GitHub: https://github.com/PRANAYKHOJARE

---

# ⭐ Support
