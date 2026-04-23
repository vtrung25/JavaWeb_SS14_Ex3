🔹 Input (Dữ liệu đầu vào)

Khi người dùng nhấn “Mua ngay”, hệ thống nhận:

userId: ID khách hàng
productId: ID sản phẩm (iPhone 15)
(Có thể có thêm)
quantity: số lượng mua (mặc định = 1)
requestTime: thời điểm request (phục vụ log/concurrency)
🔹 Output (Kết quả mong đợi)

Hệ thống có 3 trường hợp chính:

1. Thành công

Khi còn hàng và không bị tranh chấp:

Tạo Order
Giảm stock
Trả về:
"Mua thành công"
2. Hết hàng

Khi stock <= 0:

Không tạo đơn
Không trừ kho
Trả về:
"Hết hàng"
3. Tranh chấp (Concurrency)

Khi nhiều người mua cùng lúc → xảy ra xung đột:

Transaction bị fail (OptimisticLockException / Lock)
Rollback
Trả về:
"Hệ thống đang bận, vui lòng thử lại sau"