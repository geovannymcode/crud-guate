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

INSERT INTO public.customer
(id, first_name, last_name, email, number_phone, date_of_birth, profession, gender, picture_url, status)
VALUES(1, 'John', 'Doe', 'john.doe@example.com', '1234567890', '1985-05-15', 'Developer', 'Male', '', true);
INSERT INTO public.customer
(id, first_name, last_name, email, number_phone, date_of_birth, profession, gender, picture_url, status)
VALUES(2, 'Geovanny', 'Mendoza', 'geovanny.mendoza@example.com', '983992830843', '2010-01-07', 'Software Engineer', 'Male', '/image/Geovanny.png', true);
INSERT INTO public.customer
(id, first_name, last_name, email, number_phone, date_of_birth, profession, gender, picture_url, status)
VALUES(3, 'Elena', 'Aguirre', 'elena.aguirre@example.com', '3344556677', '2024-08-15', 'Software Engineer', 'Female', NULL, true);
INSERT INTO public.customer
(id, first_name, last_name, email, number_phone, date_of_birth, profession, gender, picture_url, status)
VALUES(4, 'Omar', 'Berroteran', 'omar.berroteran@gmail.com', '443556324234234', '2001-02-08', 'Software Engineer', 'Male', '/image/Omar.png', true);
INSERT INTO public.customer
(id, first_name, last_name, email, number_phone, date_of_birth, profession, gender, picture_url, status)
VALUES(5, 'Ricardo', 'Cantillo', 'richy.cantillo@gmail.com', '443355667788', '1977-07-27', 'Developer', 'Male', '/image/Ricardo.png', false);