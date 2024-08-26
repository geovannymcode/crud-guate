CREATE TABLE customer (
                          id BIGSERIAL PRIMARY KEY,
                          first_name VARCHAR(50) NOT NULL,
                          last_name VARCHAR(50) NOT NULL,
                          email VARCHAR(50),
                          number_phone VARCHAR(50) NOT NULL,
                          date_of_birth DATE NOT NULL,
                          profession VARCHAR(100),
                          gender VARCHAR(100),
                          picture_url VARCHAR(255),
                          status BOOLEAN NOT NULL
);

-- Insertar un registro inicial
INSERT INTO customer (first_name, last_name, email, number_phone, date_of_birth, profession, gender, picture_url, status)
VALUES ('John', 'Doe', 'john.doe@example.com', '1234567890', '1985-05-15', 'Developer','Male', '',true);
