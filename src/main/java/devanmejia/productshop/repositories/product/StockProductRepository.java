package devanmejia.productshop.repositories.product;

import devanmejia.productshop.models.stock.StockProduct;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface StockProductRepository extends JpaRepository<StockProduct, Long> {
    Optional<StockProduct> findByProductName(String productName);

    @EntityGraph(value = "product-stock")
    List<StockProduct> getAllBy();

    @Query(value = "SELECT count(stock_products.id) FROM stock_products", nativeQuery = true)
    int getAllStockProductAmount();

    @Query(value = "SELECT count(stock_products.id) FROM stock_products\n" +
            "WHERE stock_products.amount > 0", nativeQuery = true)
    int getAvailableStockProductAmount();

    @EntityGraph(value = "product-stock")
    @Query(value = "SELECT stockProduct FROM StockProduct stockProduct")
    List<StockProduct> getAllStockProducts(Pageable pageable);

    @EntityGraph(value = "product-stock")
    @Query(value = "SELECT stockProduct FROM StockProduct stockProduct " +
            "WHERE stockProduct.amount > 0")
    List<StockProduct> getAvailableStockProducts(Pageable pageable);

    @Query(value = "SELECT min(p.product_price)\n" +
            "FROM stock_products\n" +
            "LEFT JOIN products p on p.id = stock_products.product_id\n" +
            "WHERE stock_products.amount > 0", nativeQuery = true)
    int getMinStockProductPrice();

    @Query(value = "SELECT max(p.product_price)\n" +
            "FROM stock_products\n" +
            "LEFT JOIN products p on p.id = stock_products.product_id\n" +
            "WHERE stock_products.amount > 0", nativeQuery = true)
    int getMaxStockProductPrice();
}
