INSERT INTO users (email, name)
VALUES
('testuserA@mail.io', 'Test UserA'),
('testuserB@mail.io', 'Test UserB');

INSERT INTO items (available, description, name, owner_id)
VALUES (TRUE, 'This is default useful thing', 'test item 0', 1);

INSERT INTO bookings (start_date, end_date, status, booker_id, item_id)
VALUES
(now() - interval '2 day', now() - interval '1 day', 'WAITING', 1, 1),
(now() - interval '2 day', now() - interval '1 day', 'APPROVED', 1, 1);