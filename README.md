Chức năng chính
Level 1: Khởi động
Cấu hình giá điện theo bậc thang.

Nhập lịch sử sử dụng điện (ngày, số điện, số tiền).

API tự động tính tiền dựa trên số điện nhập vào.

Level 2: Mở rộng
Quản lý người dùng và thợ điện.

Người dùng: xem được số điện và số tiền của chính mình.

Thợ điện: nhập số điện hàng tháng cho từng người dùng.

API đăng nhập để phân quyền (người dùng chỉ xem dữ liệu của mình).

🔐 Các API chính
POST /config → cấu hình giá điện theo bậc thang.

POST /usage → nhập ngày và số điện, hệ thống tính tiền và lưu lịch sử.

GET /history → người dùng xem lịch sử sử dụng điện của mình.

POST /login → đăng nhập để xác định quyền truy cập.
