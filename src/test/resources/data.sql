INSERT INTO users (email, name)
VALUES
('testuserA@mail.io', 'Test UserA'),
('testuserB@mail.io', 'Test UserB');

INSERT INTO items (available, description, name, owner_id)
VALUES (TRUE, 'This is default useful thing', 'test item 0', 1);

INSERT INTO bookings (start_date, end_date, status, booker_id, item_id)
VALUES
(now()-2, now()-1, 'WAITING', 1, 1),
(now()-2, now()-1, 'APPROVED', 1, 1);