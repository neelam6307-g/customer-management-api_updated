## Customer Management API

This is a Spring Boot RESTful API project that manages customer data.  
It supports CRUD operations (Create, Read, Update, Delete) and calculates a customer's membership tier based on their annual spending and last purchase date.

---

## Features

- Add, view, update, and delete customer details.
- Auto-generate unique UUID for each customer.
- Validate email and name before saving.
- Calculate customer tier (Silver / Gold / Platinum) dynamically
- Use H2 in-memory database for testing
- Proper error handling for missing or invalid data

---

## Technologies Used

- Java 21
- Spring Boot 3.5.7
- Spring Data JPA
- H2 Database
- Maven
- Jakarta Validation

---

##  How to Run the Application

1. Open the project in your IDE (IntelliJ / Eclipse / VS Code).
2. Make sure Java 21 and Maven are installed.
3. Run the main class: "CustomerManagementApiApplication.java"
4. The app will start on `http://localhost:8080`

-Sample Request

**Add Customer (POST)**

POST:http://localhost:8080/customers

{
"name": "Neelam",
"email": "neelam@example.com",
"annualSpend": 1000,
"lastPurchaseDate": "2025-06-10T00:00:00Z"
}


**VIEW THE DETAILS(GET)**

2.GET:http://localhost:8080/customers

Response Example-

{
"id": "5e4c723b-dacb-4770-977a-ac2914299f1b",
"name": "Neelam Gautam",
"email": "neelam@example.com",
"annualSpend": 12000.00,
"lastPurchaseDate": "2025-06-01T00:00:00Z",
"tier": "Platinum"
}

3.Get Customer by ID

   -GET /customers/{id}

4.Get Customer by Name

  -GET /customers?name=Neelam

5.Get Customer by Email 

  -GET /customer?email=neelam@example.com

**UPDATE RECORDS**

6.PUT: http://localhost:8080/customers/id

**DELETE RECORDS**

7.DELETE: http://localhost:8080/customers/id


---

## API Endpoints

| Method | Endpoint          | Description           |
| ------ |-------------------| --------------------- |
| POST   | /customers        | Add a new customer    |
| GET    | /customers/{id}   | Get customer by ID    |
| GET    | /customers?name=  | Get customer by name  |
| GET    | /customers?email= | Get customer by email |
| PUT    | /customers/{id}   | Update a customer     |
| DELETE | /customers/{id}   | Delete a customer     |



## Tier Logic


- Silver: annual spend < $1000
- Gold: annual spend >= $1000 and last purchase within 12 months
- Platinum: annual spend > $10,000 and last purchase within 6 months

## H2 Database


- URL: http://localhost:8080/h2-console
- JDBC URL: jdbc:h2:mem:customersDB
- Username: sa
- Password: 









