CREATE TABLE client_class_schedule (
    id SERIAL PRIMARY KEY,
    user_id INTEGER NOT NULL REFERENCES users(id),
    class_session_id INTEGER NOT NULL REFERENCES class_sessions(id),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    UNIQUE (user_id, class_session_id)
);

CREATE TABLE payments (
    payment_id SERIAL PRIMARY KEY,
    client_id INT REFERENCES clients(client_id),
    amount DECIMAL(10,2) NOT NULL,
    payment_method VARCHAR(50),
    payment_date DATE NOT NULL DEFAULT CURRENT_DATE
);

CREATE TABLE attendance (
    attendance_id SERIAL PRIMARY KEY,
    client_id INT REFERENCES clients(client_id),
    session_id INT REFERENCES class_sessions(session_id),
    attendance_date DATE NOT NULL DEFAULT CURRENT_DATE,
    status VARCHAR(20) NOT NULL
);

CREATE TABLE progress_reports (
    report_id SERIAL PRIMARY KEY,
    client_id INT REFERENCES clients(client_id),
    performance_level VARCHAR(50),
    comments TEXT,
    report_date DATE NOT NULL DEFAULT CURRENT_DATE
);

INSERT INTO class_sessions (session_name, session_day, session_time) VALUES
('Beginner Swim Class', 'Monday', '4:00 PM'),
('Intermediate Swim Class', 'Tuesday', '5:00 PM'),
('Advanced Swim Training', 'Thursday', '3:30 PM'),
('Kids Swim Session', 'Saturday', '10:00 AM');