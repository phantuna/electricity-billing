INSERT INTO role (id, name, decription) VALUES
(1, 'ADMIN', 'Quan tri vien'),
(2, 'USER', 'Nguoi dung');

-- USER
INSERT INTO user (username, password, role_id) VALUES
('khanh123', '123456', 2),
('admin', 'admin123', 1),
('hoang999', '123456', 2);

-- ELECTRICITY PRICE
INSERT INTO electricity_price (from_kwh, to_kwh, price) VALUES
(0, 50, 1806),
(51, 100, 1866),
(101, 200, 2167),
(201, 300, 2729),
(301, 400, 3050),
(401, NULL, 3151);
INSERT INTO permission (id,name ,description ) VALUES
(1, "VIEW_USER", "Xem so dien"),
(2, "UPDATE_EMPLOYEE", "Ghi so dien");

INSERT INTO role_permission (role_id,permission_id ) VALUES
(1,1),
(1,2),
(2,1);

