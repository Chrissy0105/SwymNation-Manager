# 🏊‍♂️ Swym Nation Management System

A modern Java-based desktop application for managing a swim studio.  
This system supports multiple user roles and provides features for registration, scheduling, payments, notifications, and reporting — all backed by a PostgreSQL database.

---

## 🚀 Features

### 🔐 Authentication & Roles
- Secure login system
- Role-based dashboards:
  - Admin
  - Receptionist
  - Instructor
  - Client

---

### 👤 User Management
- Register:
  - Clients
  - Instructors
  - Receptionists
  - Admins
- Role-based access control

---

### 💳 Payments
- Record payments for clients and staff
- Supports:
  - Cash
  - Debit Card
  - Credit Card
- Dynamic form validation for card details
- View payment history
- Generate receipts

---

### 📅 Scheduling
- Create class sessions
- Assign clients to sessions
- View upcoming schedules (client-specific)
- Smart schedule retrieval from database

---

### 📊 Attendance & Progress
- Record attendance
- Track student progress
- View reports per user

---

### 🔔 Notifications
- Send notifications manually (Admin, Instructor, Receptionist)
- Automatic notifications:
  - Payment confirmations
  - Upcoming classes

---

### 📄 Reports
- Generate full system reports including:
  - Users
  - Payments
  - Attendance
  - Sessions
  - Notifications

- Export reports as:
  - `.txt`
  - `.pdf` (using Apache PDFBox)

---

## 🎨 UI/UX

- Modern dashboard design
- Role-based color themes:
  - Admin → Blue
  - Receptionist → Teal
  - Instructor → Orange
  - Client → Purple
- Card-based layout
- Hover effects on buttons
- Responsive scrollable pages
- Clean typography (Segoe UI)

---

## 🛠️ Technologies Used

- **Java (Swing GUI)**
- **PostgreSQL (Database)**
- **JDBC (Database Connectivity)**
- **Apache PDFBox (PDF Export)**

---

## 🗄️ Database Structure

Main tables include:

- `users`
- `payments`
- `receipts`
- `attendance_records`
- `student_progress`
- `class_sessions`
- `notifications`
- `client_class_schedule`

---

## ⚙️ Setup Instructions

### 1. Clone or Download Project

```bash
git clone <your-repo-url>
cd JavaGUI
```
### 2. Install Dependencies

```bash
wget https://jdbc.postgresql.org/download/postgresql-42.7.3.jar
wget https://archive.apache.org/dist/pdfbox/3.0.2/pdfbox-app-3.0.2.jar
```

### 3. Configure Database

    Start PostgreSQL and connect:

        ```bash
        sudo -u postgres psql
        ```
    Create database:

            ```bash
            CREATE DATABASE swims;
            \c swims
            ```
    Run the schema .sql file to create tables. 

### 4.  Update DataBase Connection

    IN DBConnection.java, ensure:

    ```Java
    String url = "jdbc:postgresql://localhost:5432/swims";
    String user = "postgres";
    String password = "your_password";
    ```

### 5. Compile and Run

    ```bash
    rm -rf ui/*.class dao/*.class db/*.class java_reference/*.class *.class
      
    javac -cp ".:postgresql-42.7.3.jar:pdfbox-app-3.0.2.jar" -d . Main.java ui/*.java dao/*.java db/*.java       java_reference/*.java

    java -cp ".:postgresql-42.7.3.jar:pdfbox-app-3.0.2.jar" Main
    ```

🔑 Sample Login Accounts

      Role	              Email	                  Password
      Admin	              admin@test.com          123
      Instructor	        instructor@test.com     123
      Receptionist	      chrissy0105@gmail.com   123
      Client	            jjones123@gmail.com     123

📌 Future Improvements

    Password hashing (security)
    Dark mode toggle
    Email/SMS notifications
    Charts and analytics dashboard
    Mobile/web version

👨‍💻 Author

    Christina Blye
    UWI Mona – Information Technology


📄 License

    This project is for academic and educational purposes.

