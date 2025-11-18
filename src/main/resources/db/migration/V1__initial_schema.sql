-- Create tables
CREATE TABLE USERS (
                       id BIGSERIAL PRIMARY KEY,
                       username VARCHAR(255) UNIQUE NOT NULL,
                       password VARCHAR(255) NOT NULL,
                       email VARCHAR(55),
                       roles VARCHAR(255) NOT NULL
);

CREATE TABLE TRANSACTION (
                             id BIGSERIAL PRIMARY KEY,
                             external_id VARCHAR(255),
                             account_number VARCHAR(255),
                             amount DECIMAL(12,2) NOT NULL,
                             description VARCHAR(255),
                             user_id BIGINT,
                             FOREIGN KEY (user_id) REFERENCES USERS(id)
);

CREATE TABLE DISPUTE (
                         id BIGSERIAL PRIMARY KEY,
                         transaction_id BIGINT,
                         user_id BIGINT,
                         reason VARCHAR(255),
                         status VARCHAR(50),
                         created_at TIMESTAMP WITH TIME ZONE,
                         FOREIGN KEY (transaction_id) REFERENCES TRANSACTION(id),
                         FOREIGN KEY (user_id) REFERENCES USERS(id)
);