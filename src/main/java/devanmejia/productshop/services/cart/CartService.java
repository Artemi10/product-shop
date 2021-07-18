package devanmejia.productshop.services.cart;

import devanmejia.productshop.models.order.Order;
import devanmejia.productshop.models.product.Product;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

@Service
public interface CartService {
    @Transactional(rollbackFor = DataIntegrityViolationException.class, isolation = Isolation.READ_COMMITTED)
    void addProductInCart(Order order, Product product, int amount);
    int getCartProductsAmount(Order order);
    int getCartProductAmount(Order order, String productName);
}
