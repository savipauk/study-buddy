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
    date_of_birth DATE,
    gender ENUM('M', 'F', 'NOTDEFINED'),
    city VARCHAR(100),
    role ENUM('STUDENT', 'PROFESSOR', 'ADMIN', 'UNASSIGNED') NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (user_id),
    UNIQUE (email, oauth_id)
);

CREATE TABLE Students (
    student_id INT NOT NULL AUTO_INCREMENT,
    user_id INT NOT NULL, -- foreign key prema Users
    PRIMARY KEY (student_id),
    UNIQUE (user_id),
    FOREIGN KEY (user_id) REFERENCES Users(user_id) ON DELETE CASCADE
);

CREATE TABLE Professors (
    professor_id INT NOT NULL AUTO_INCREMENT,
    user_id INT NOT NULL, -- foreign key prema Users
    PRIMARY KEY (professor_id),
    UNIQUE (user_id),
    FOREIGN KEY (user_id) REFERENCES Users(user_id) ON DELETE CASCADE
);


CREATE TABLE StudyGroups (
    group_id INT NOT NULL AUTO_INCREMENT,
    creator_id INT NOT NULL, -- foreign key prema Users
    group_name VARCHAR(255) NOT NULL,
    location VARCHAR(255),
    x_coordinate VARCHAR(255),
    y_coordinate VARCHAR(255),
    date DATE NOT NULL,
    time TIME NOT NULL,
    max_members INT NOT NULL,
    description TEXT,
    expiration_date DATE NOT NULL,
    PRIMARY KEY (group_id),
    FOREIGN KEY (creator_id) REFERENCES Students(student_id)

    --Use this if want to completely delete all studyGroup created by student with creator_id
    -- FOREIGN KEY (creator_id) REFERENCES Students(student_id) ON DELETE CASCADE
);

CREATE TABLE GroupMembers (
    group_id BIGINT NOT NULL,
    member_id BIGINT NOT NULL,
    join_date TIMESTAMP,
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
    subject VARCHAR(255),
    duration VARCHAR(255 )NOT NULL, -- trajanje u minutama
    max_participants INT NOT NULL,
    min_participants INT NOT NULL,
    x_coordinate VARCHAR(255),
    y_coordinate VARCHAR(255),
    location VARCHAR(255),
    lesson_type ENUM('MASS', 'ONE_ON_ONE') NOT NULL,
    price FLOAT,
    date DATE NOT NULL,
    time TIME NOT NULL,
    registration_deadline DATE,
    PRIMARY KEY (lesson_id),
    FOREIGN KEY (professor_id) REFERENCES Professors(professor_id)

    --Use this if we want to completely delete all lessons created by professor with professor_id
    -- FOREIGN KEY (professor_id) REFERENCES Professors(professor_id) ON DELETE CASCADE
);

CREATE TABLE LessonParticipants (
    lesson_id INT NOT NULL, -- foreign key for Lessons
    participant_id INT NOT NULL, -- foreign key for Students
    participation_date TIMESTAMP,
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
INSERT INTO Users (email, password, username,oauth_provider, oauth_id, first_name, last_name, description, access_token, refresh_token, role, gender, date_of_birth, city)
VALUES ('student1@example.com', '$2a$10$BD1piSn8s8QgTo6lqegAJurHPkI4H6psG12L1JrKUJz6KYYfiXDue', 'student1', '', '', 'Alice', 'Johnson', 'Physics enthusiast', 'accessToken1', 'refreshToken1', 'STUDENT', 'M', '2003-06-20', 'Zagreb'),
    ('student2@example.com', '$2a$10$FFLAIEctq8RB.mp1LlXuKuZ7Un9cIUsLlVhsYY310LUVA0tBDloMm','student2', '', '', 'Bob', 'Smith', 'Aspiring physicist', 'accessToken2', 'refreshToken2', 'STUDENT', 'F', '2003-06-03', 'Zagreb'),
    ('professor1@example.com', '$2a$10$CJa71bFBwtMyFLwtIm/ysOlriZyoCinsBZr3WntEkRMg.l8LOO8TO','professor1', 'Google', 'oauth_prof1', 'Dr. Carol', 'Davis', 'Professor of Quantum Mechanics', 'accessToken3', 'refreshToken3', 'Professor','M', '1974-01-15', 'Zagreb'),
    ('professor2@example.com', '$2a$10$sA1LGAPyLVRJNGGH7n5NcuXywbDXYMe08pgfNtnPHXoYrnhNS1gVO','professor2' ,'Google', 'oauth_prof2', 'Dr. David', 'Lee', 'Professor of Theoretical Physics', 'accessToken4', 'refreshToken4', 'Professor','F', '1971-11-12', 'Zagreb'),
    ('admin1@example.com', '$2a$10$DNGjWLtWGf2MUejpWbZL/eJhsnzgXug9oFZaXfw5lRDaj4QhT1VsW','admin1' ,'Google', 'oauth_admin1', 'Emma', 'Thomas', 'Admin with full access', 'accessToken5', 'refreshToken5', 'Admin', 'M', '1965-12-02', 'Zagreb'),
    ('admin2@example.com', '$2a$10$UyzZZ4Mb4FYBm027NI0mo.ZyePtoh4KbGwipgnsM/XzGaMCyLHcnS','admin2' ,'Google', 'oauth_admin2', 'Frank', 'White', 'Responsible for managing users', 'accessToken6', 'refreshToken6', 'Admin', 'F', '1980-03-04', 'Zagreb');
-- student1 password: 'password123'
-- student2 password: 'password345'
-- professor1 password: 'password789'
-- professor2 password: 'password012'
-- admin1 password: 'password345'
-- admin2 password: 'password678'


-- Student entires
INSERT INTO Students(user_id)
VALUES ('1'),
       ('2');

-- Professor entires
INSERT INTO Professors(user_id)
VALUES ('3'),
       ('4');

-- Lessons entries
INSERT INTO Lessons (professor_id, subject, duration, max_participants, min_participants, x_coordinate, y_coordinate, location, lesson_type, price, date, time, registration_deadline)
VALUES (1,'Math', '60 minutes', 20, 5, '45.8132', '15.9770', 'Zagreb', 'MASS', 25.00, '2025-01-29', '09:00:00', '2025-01-27'),
    (2,'Physics', '90 minutes', 15, 3, '43.5081', ' 16.4402', 'Split', 'ONE_ON_ONE', 50.00, '2025-01-28', '14:30:00', '2025-01-26'),
    (1,'English', '45 minutes', 10, 2, '42.6410', '18.1106', 'Dubrovnik', 'MASS', 20.00, '2025-01-27', '11:00:00', '2025-01-25'),
    (1,'Chemistry', '120 minutes', 25, 10, '45.3271', '14.4422', 'Rijeka', 'MASS', 35.00, '2025-01-26', '15:00:00', '2025-01-24'),
    (2,'History', '50 minutes', 30, 8, '44.1178', '15.2272', 'Zadar', 'ONE_ON_ONE', 15.00, '2025-01-25', '10:00:00', '2025-01-23');

-- StudyGroups entries
INSERT INTO StudyGroups (group_name, location, x_coordinate, y_coordinate, date, time, max_members, description, expiration_date, creator_id)
VALUES ('Math Study Group', 'Zagreb', '45.8150', '15.9819', '2025-01-20', '10:00:00', 15, 'Focus on algebra and calculus', '2025-01-18', 1),
     ('Physics Enthusiasts', 'Split', '43.5081', '16.4402', '2025-02-05', '14:30:00', 10, 'Discuss quantum mechanics and experiments', '2025-02-01', 2),
     ('Chemistry Basics', 'Rijeka', '45.3271', '14.4422', '2025-01-25', '16:00:00', 12, 'Introduction to organic and inorganic chemistry', '2025-01-22', 1),
     ('History Buffs', 'Osijek', '45.5600', '18.6758', '2025-03-01', '09:30:00', 8, 'Exploring World War II topics', '2025-02-28', 2),
     ('Programming Fundamentals', 'Dubrovnik', '42.6507', '18.0944', '2025-04-10', '11:00:00', 20, 'Learn the basics of Python and Java', '2025-04-05', 1);

-- GroupMembers entries
INSERT INTO GroupMembers(group_id, member_id, join_date)
VALUES (1,1, NOW()),
       (2,2, NOW()),
       (3,1, NOW()),
       (4,2, NOW()),
       (5,1, NOW());

-- Insert default user with ID = 0
INSERT INTO Users (user_id, email, password, oauth_provider, oauth_id, first_name, last_name, username, profile_picture, description,
        access_token, refresh_token, date_of_birth, gender, city, role)
    VALUES ( 0, 'default@example.com', '', 'NONE', '0', 'Default', 'User', 'default', NULL, NULL,
        NULL, NULL, NULL, 'NOTDEFINED', NULL, 'UNASSIGNED'
);

-- Insert default student with student_id = 0
INSERT INTO Students(student_id, user_id)
    VALUES (0,0);

-- Insert default professor with profesor_id = 0
INSERT INTO Professors(professor_id, user_id)
    VALUES (0,0);