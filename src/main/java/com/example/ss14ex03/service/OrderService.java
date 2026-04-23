package com.example.ss14ex03.service;

import com.example.ss14ex03.entity.Order;
import com.example.ss14ex03.entity.Product;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.StaleObjectStateException;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public class OrderService {
    private SessionFactory sessionFactory;

    public String buyNow(Long productId, Long userId) {

        try (Session session = sessionFactory.getCurrentSession()) {

            // 1. Lấy product
            Product product = session.get(Product.class, productId);

            if (product == null) {
                return "Sản phẩm không tồn tại";
            }

            // 2. Check stock
            if (product.getStock() <= 0) {
                return "Hết hàng";
            }

            // 3. Trừ kho
            product.setStock(product.getStock() - 1);
            session.update(product);

            // 4. Tạo order
            Order order = new Order();
            order.setUserId(userId);
            order.setProductId(productId);
            order.setStatus("SUCCESS");

            session.save(order);


            return "Mua thành công";

        } catch (StaleObjectStateException | jakarta.persistence.OptimisticLockException e) {
            return "Hệ thống đang bận, vui lòng thử lại sau";

        } catch (Exception e) {
            return "Có lỗi xảy ra";

        }
    }
}