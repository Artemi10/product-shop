package devanmejia.productshop.repositories.product;

import devanmejia.productshop.models.order.CartProduct;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CartProductRepository extends JpaRepository<CartProduct, Long> {
    @EntityGraph(attributePaths = {"product", "order"})
    List<CartProduct> getAllBy();
    Optional<CartProduct> getCartProductByOrder_IdAndProductName(Long orderId, String productName);
}
