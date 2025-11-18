-- Seed users
INSERT INTO USERS (id, username, password,email, roles) VALUES
                                                      (1, 'Max', '$2a$10$tOh4qS.p1mjaz2EiLPG/0Ou.YrJjteHmk8ecV0rPBHYtBHGWtWD3O',  'max@example.com','ROLE_USER'),
                                                      (2, 'Bulelani', '$2a$10$tOh4qS.p1mjaz2EiLPG/0Ou.YrJjteHmk8ecV0rPBHYtBHGWtWD3O','bulelani@gmail.com', 'ROLE_USER'),
                                                      (3, 'Munkie', '$2a$10$tOh4qS.p1mjaz2EiLPG/0Ou.YrJjteHmk8ecV0rPBHYtBHGWtWD3O','munkie@yahoo.com', 'ROLE_USER');

-- Seed transactions
INSERT INTO TRANSACTION (id, external_id, account_number, amount, description, user_id) VALUES
                                                                                            (100, 'tx-100', '41001111', 250.00, 'Groceries', 1),
                                                                                            (101, 'tx-101', '41001111',  50.00, 'Coffee', 1),
                                                                                            (103, 'tx-103', '41001111', 180.50, 'Fuel', 1),
                                                                                            (107, 'tx-107', '41001111',  78.00, 'Take-away', 1),
                                                                                            (110, 'tx-110', '41001111',  35.00, 'Parking', 1),
                                                                                            (102, 'tx-102', '41009999', 1200.00, 'Salary', 2),
                                                                                            (104, 'tx-104', '41009999',  320.00, 'Rent', 2),
                                                                                            (108, 'tx-108', '41009999', 1550.00, 'Bonus', 2),
                                                                                            (105, 'tx-105', '41002222',   99.99, 'Streaming', 3),
                                                                                            (106, 'tx-106', '41002222',  450.00, 'Electricity', 3),
                                                                                            (109, 'tx-109', '41002222',  210.75, 'Internet', 3),
                                                                                            (111, 'tx-111', '41002222',  620.00, 'Medical aid', 3);

-- Seed disputes
INSERT INTO DISPUTE (transaction_id, reason, status, created_at, user_id) VALUES
                                                                                  ( 100, 'Incorrect amount', 'OPEN', '2025-11-16 09:30:00+02', 1),
                                                                                  ( 102, 'Unknown transaction', 'PENDING', '2025-11-16 12:15:00+02', 2),
                                                                                  ( 105, 'Duplicate charge', 'APPROVED', '2025-11-16 14:45:00+02', 3);