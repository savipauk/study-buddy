DROP TABLE IF EXISTS Users CASCADE;
DROP TABLE IF EXISTS Students CASCADE;
DROP TABLE IF EXISTS Professors CASCADE;
DROP TABLE IF EXISTS Admins CASCADE;
DROP TABLE IF EXISTS StudyGroups CASCADE;
DROP TABLE IF EXISTS GroupMembers CASCADE;
DROP TABLE IF EXISTS Lessons CASCADE;
DROP TABLE IF EXISTS LessonParticipants CASCADE;
DROP TABLE IF EXISTS Reports CASCADE;
DROP TABLE IF EXISTS Reviews CASCADE;
DROP TABLE IF EXISTS Materials CASCADE;

CREATE TABLE Users (
    user_id INT NOT NULL AUTO_INCREMENT,
    email VARCHAR(100),
    password VARCHAR(255),
    oauth_provider VARCHAR(50) NOT NULL,
    oauth_id VARCHAR(255) NOT NULL,
    first_name VARCHAR(100) NOT NULL,
    last_name VARCHAR(100) NOT NULL,
    username VARCHAR(100) NOT NULL,
    profile_picture VARCHAR(255),
    description TEXT,
    access_token VARCHAR(255),
    refresh_token VARCHAR(255),
    role ENUM('STUDENT', 'PROFESSOR', 'ADMIN', 'UNASSIGNED') NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (user_id),
    UNIQUE (email, oauth_id)
);

CREATE TABLE Students (
    student_id INT NOT NULL AUTO_INCREMENT,
    user_id INT NOT NULL, -- foreign key prema Users
    date_of_birth DATE,
    gender ENUM('M', 'F', 'Other'),
    city VARCHAR(100),
    PRIMARY KEY (student_id),
    UNIQUE (user_id),
    FOREIGN KEY (user_id) REFERENCES Users(user_id) ON DELETE CASCADE
);

CREATE TABLE Professors (
    professor_id INT NOT NULL AUTO_INCREMENT,
    user_id INT NOT NULL, -- foreign key prema Users
    date_of_birth DATE,
    gender ENUM('M', 'F', 'Other'),
    city VARCHAR(100),
    PRIMARY KEY (professor_id),
    UNIQUE (user_id),
    FOREIGN KEY (user_id) REFERENCES Users(user_id) ON DELETE CASCADE
);


CREATE TABLE StudyGroups (
    group_id INT NOT NULL AUTO_INCREMENT,
    creator_id INT NOT NULL, -- foreign key prema Students
    group_name VARCHAR(255) NOT NULL,
    location VARCHAR(255),
    date DATE NOT NULL,
    max_members INT NOT NULL,
    description TEXT,
    expiration_date DATE NOT NULL,
    PRIMARY KEY (group_id),
    FOREIGN KEY (creator_id) REFERENCES Students(student_id) ON DELETE CASCADE
);

CREATE TABLE GroupMembers (
    group_id BIGINT NOT NULL,
    member_id BIGINT NOT NULL,
    PRIMARY KEY (group_id, member_id),
    CONSTRAINT fk_group
        FOREIGN KEY (group_id)
        REFERENCES StudyGroups(group_id)
        ON DELETE CASCADE,
    CONSTRAINT fk_member
        FOREIGN KEY (member_id)
        REFERENCES Students(student_id)
        ON DELETE CASCADE
);

CREATE TABLE Lessons (
    lesson_id INT NOT NULL AUTO_INCREMENT,
    professor_id INT NOT NULL, -- foreign key prema Professors
    lesson_type ENUM('Mass', 'One-on-One') NOT NULL,
    location VARCHAR(255),
    price FLOAT,
    date DATE NOT NULL,
    duration INT NOT NULL, -- trajanje u minutama
    max_participants INT NOT NULL,
    min_participants INT NOT NULL,
    registration_deadline DATE,
    PRIMARY KEY (lesson_id),
    FOREIGN KEY (professor_id) REFERENCES Professors(professor_id) ON DELETE CASCADE
);

CREATE TABLE LessonParticipants (
    lesson_id INT NOT NULL, -- foreign key prema Lessons
    participant_id INT NOT NULL, -- foreign key prema Students
    PRIMARY KEY (lesson_id, participant_id),
    FOREIGN KEY (lesson_id) REFERENCES Lessons(lesson_id) ON DELETE CASCADE,
    FOREIGN KEY (participant_id) REFERENCES Students(student_id) ON DELETE CASCADE
);

CREATE TABLE Reports (
    report_id INT NOT NULL AUTO_INCREMENT,
    reporter_id INT NOT NULL, -- ID korisnika koji prijavljuje
    reported_user_id INT NOT NULL, -- ID korisnika koji je prijavljen
    reason VARCHAR(255) NOT NULL, -- Razlog prijave
    report_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (report_id),
    FOREIGN KEY (reporter_id) REFERENCES Users(user_id) ON DELETE CASCADE,
    FOREIGN KEY (reported_user_id) REFERENCES Users(user_id) ON DELETE CASCADE
);

CREATE TABLE Reviews (
    review_id INT NOT NULL AUTO_INCREMENT,
    student_id INT NOT NULL, -- ID studenta koji daje recenziju
    professor_id INT NOT NULL, -- ID profesora kojeg se recenzira
    rating INT CHECK (rating BETWEEN 1 AND 5), -- Ocjena, npr. 1 do 5
    comment TEXT,
    review_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (review_id),
    FOREIGN KEY (student_id) REFERENCES Students(student_id) ON DELETE CASCADE,
    FOREIGN KEY (professor_id) REFERENCES Professors(professor_id) ON DELETE CASCADE
);

CREATE TABLE Materials (
    material_id INT NOT NULL AUTO_INCREMENT,
    user_id INT NOT NULL, -- ID korisnika koji je postavio materijal
    group_id INT, -- Ako je materijal postavljen za grupu
    lesson_id INT, -- Ako je materijal postavljen za lekciju
    description TEXT,
    upload_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (material_id),
    FOREIGN KEY (user_id) REFERENCES Users(user_id) ON DELETE CASCADE,
    FOREIGN KEY (group_id) REFERENCES StudyGroups(group_id) ON DELETE SET NULL,
    FOREIGN KEY (lesson_id) REFERENCES Lessons(lesson_id) ON DELETE SET NULL
);

-- User entries
INSERT INTO Users (email, password, username,oauth_provider, oauth_id, first_name, last_name, description, access_token, refresh_token, role)
VALUES ('student1@example.com', '$2a$10$BD1piSn8s8QgTo6lqegAJurHPkI4H6psG12L1JrKUJz6KYYfiXDue', 'student1', '', '', 'Alice', 'Johnson', 'Physics enthusiast', 'accessToken1', 'refreshToken1', 'STUDENT'),
    ('student2@example.com', '$2a$10$FFLAIEctq8RB.mp1LlXuKuZ7Un9cIUsLlVhsYY310LUVA0tBDloMm','student2', '', '', 'Bob', 'Smith', 'Aspiring physicist', 'accessToken2', 'refreshToken2', 'STUDENT'),
    ('professor1@example.com', '$2a$10$CJa71bFBwtMyFLwtIm/ysOlriZyoCinsBZr3WntEkRMg.l8LOO8TO','professor1', 'Google', 'oauth_prof1', 'Dr. Carol', 'Davis', 'Professor of Quantum Mechanics', 'accessToken3', 'refreshToken3', 'Professor'),
    ('professor2@example.com', '$2a$10$sA1LGAPyLVRJNGGH7n5NcuXywbDXYMe08pgfNtnPHXoYrnhNS1gVO','professor2' ,'Google', 'oauth_prof2', 'Dr. David', 'Lee', 'Professor of Theoretical Physics', 'accessToken4', 'refreshToken4', 'Professor'),
    ('admin1@example.com', '$2a$10$DNGjWLtWGf2MUejpWbZL/eJhsnzgXug9oFZaXfw5lRDaj4QhT1VsW','admin1' ,'Google', 'oauth_admin1', 'Emma', 'Thomas', 'Admin with full access', 'accessToken5', 'refreshToken5', 'Admin'),
    ('admin2@example.com', '$2a$10$UyzZZ4Mb4FYBm027NI0mo.ZyePtoh4KbGwipgnsM/XzGaMCyLHcnS','admin2' ,'Google', 'oauth_admin2', 'Frank', 'White', 'Responsible for managing users', 'accessToken6', 'refreshToken6', 'Admin');
-- student1 password: 'password123'
-- student2 password: 'password345'
-- professor1 password: 'password789'
-- professor2 password: 'password012'
-- admin1 password: 'password345'
-- admin2 password: 'password678'