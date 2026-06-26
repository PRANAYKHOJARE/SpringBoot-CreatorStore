# 🛒 CreatorStore

A full-stack e-commerce admin panel built using **Spring Boot + MySQL + Vanilla JavaScript**.

This is my **first Spring Boot project**, where I built a complete store management system with product management, order placement, inventory updates, and order history tracking.

---

## 🚀 Live Demo

🌐 Frontend: :contentReference[oaicite:0]{index=0}  
⚙️ Backend API: :contentReference[oaicite:1]{index=1}

---

## 📸 Screenshots

### Dashboard
![Dashboard](screenshots/dashboard.png)

### Order History
![Order History](screenshots/order-history.png)
---

## ✨ Features

✅ Product Management
- Add products
- Edit products
- Delete products
- Manage stock quantity

✅ Dashboard
- Total products
- In-stock products
- Out-of-stock products

✅ Order Management
- Place customer orders
- Auto-calculate totals
- Stock deduction after purchase

✅ Order History
- View all orders
- Track order status
- Update status (Confirmed → Delivered)

---

## 🛠 Tech Stack

### Backend
- Java 21
- Spring Boot
- Spring Web
- Spring Data JPA
- Hibernate
- MySQL
- Maven

### Frontend
- HTML
- CSS
- JavaScript

### Deployment
- Frontend: Vercel
- Backend: Railway
- Database: MySQL

---

## 📂 Project Structure

```bash
CreatorStore/
│
├── frontend/
│   ├── css/
│   ├── js/
│   └── index.html
│
├── src/
│   └── main/
│       ├── java/
│       └── resources/
│
├── pom.xml
└── README.md
```

---

## API Endpoints

### Products
```http
GET    /api/products
POST   /api/products
PUT    /api/products/{id}
DELETE /api/products/{id}
```

### Orders
```http
GET   /api/orders
POST  /api/orders
PATCH /api/orders/{id}/status
```

---

## Challenges I Solved

- Fixed Hibernate Lazy Loading issue
- Solved CORS issues between frontend and backend
- Managed frontend deployment on Vercel
- Connected Railway backend with MySQL database
- Fixed JSON serialization issues in Order entities

---

## Future Improvements

- Admin login authentication
- Customer login
- JWT security
- Search & filter products
- Analytics dashboard
- Payment integration

---

## What I Learned

Through this project I learned:

- Building REST APIs using Spring Boot
- Entity relationships (OneToMany / ManyToOne)
- Database integration with JPA
- Frontend-backend communication
- Deployment workflow
- Debugging real production issues

---

## 👨‍💻 Author

**Pranay Khojare**  
Aspiring Java Backend Developer

GitHub: :contentReference[oaicite:2]{index=2}

---

## ⭐ Support

If you like this project, consider giving it a star ⭐