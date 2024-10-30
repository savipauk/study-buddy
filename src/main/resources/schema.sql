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
    role ENUM('Student', 'Professor', 'Admin') NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (user_id),
    UNIQUE (email, oauth_provider, oauth_id)
);

CREATE TABLE Students (
    student_id INT NOT NULL AUTO_INCREMENT,
    user_id INT NOT NULL, -- foreign key prema Users
    username VARCHAR(100) NOT NULL,
    profile_picture VARCHAR(255),
    date_of_birth DATE,
    gender ENUM('M', 'F', 'Other'),
    city VARCHAR(100),
    description TEXT,
    PRIMARY KEY (student_id),
    UNIQUE (user_id),
    FOREIGN KEY (user_id) REFERENCES Users(user_id) ON DELETE CASCADE
);

CREATE TABLE Professors (
    professor_id INT NOT NULL AUTO_INCREMENT,
    user_id INT NOT NULL, -- foreign key prema Users
    username VARCHAR(100) NOT NULL,
    profile_picture VARCHAR(255),
    date_of_birth DATE,
    gender ENUM('M', 'F', 'Other'),
    city VARCHAR(100),
    description TEXT,
    PRIMARY KEY (professor_id),
    UNIQUE (user_id),
    FOREIGN KEY (user_id) REFERENCES Users(user_id) ON DELETE CASCADE
);

CREATE TABLE Admins (
    admin_id INT NOT NULL AUTO_INCREMENT,
    user_id INT NOT NULL, -- foreign key prema Users
    username VARCHAR(100) NOT NULL,
    profile_picture VARCHAR(255),
    gender ENUM('M', 'F', 'Other'),
    PRIMARY KEY (admin_id),
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
    group_id INT NOT NULL, -- foreign key prema StudyGroups
    member_id INT NOT NULL, -- foreign key prema Students
    PRIMARY KEY (group_id, member_id),
    FOREIGN KEY (group_id) REFERENCES StudyGroups(group_id) ON DELETE CASCADE,
    FOREIGN KEY (member_id) REFERENCES Students(student_id) ON DELETE CASCADE
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

