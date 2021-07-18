package devanmejia.productshop.services.cart;

import devanmejia.productshop.models.order.CartProduct;
import devanmejia.productshop.models.order.Order;
import devanmejia.productshop.models.product.Product;
import devanmejia.productshop.models.stock.StockProduct;
import devanmejia.productshop.repositories.product.CartProductRepository;
import devanmejia.productshop.repositories.product.StockProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;



@Service
public class CartServiceImpl implements CartService {
    @Autowired
    private CartProductRepository cartProductRepository;
    @Autowired
    private StockProductRepository stockProductRepository;

    @Override
    @Transactional(rollbackFor = DataIntegrityViolationException.class, isolation = Isolation.READ_COMMITTED)
    public void addProductInCart(Order order, Product product, int newAmount){
        Long orderId = order.getId();
        String productName = product.getName();
        StockProduct stockProduct = stockProductRepository.findByProductName(productName)
                .orElseThrow(() -> new IllegalArgumentException(String.format("No such product in the stock (%s)", productName)));
        CartProduct cartProduct = cartProductRepository.getCartProductByOrder_IdAndProductName(orderId, productName)
                .orElse(createNewCartProduct(order, product));
        int stockProductAmount = stockProduct.getAmount();
        int cartProductAmount = cartProduct.getAmount();

        stockProduct.setAmount(stockProductAmount - (newAmount - cartProductAmount));
        cartProduct.setAmount(newAmount);
        stockProductRepository.save(stockProduct);
        cartProductRepository.save(cartProduct);
    }

    @Override
    public int getCartProductsAmount(Order order) {
        return order.getCart().stream().map(CartProduct::getAmount)
                .reduce(Integer::sum).orElse(0);
    }

    @Override
    public int getCartProductAmount(Order order, String productName) {
        return order.getCart().stream().filter(cartProduct -> cartProduct.getProduct().getName().equals(productName))
                .findFirst().map(CartProduct::getAmount).orElse(0);
    }

    private CartProduct createNewCartProduct(Order order, Product product){
        return CartProduct.builder()
                .order(order)
                .product(product)
                .amount(0).build();
    }
}
