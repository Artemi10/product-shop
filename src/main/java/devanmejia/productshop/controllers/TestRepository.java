package devanmejia.productshop.controllers;

import devanmejia.productshop.models.stock.StockProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TestRepository extends JpaRepository<StockProduct, Long> {
    @Query(value = "SELECT *\n" +
            "FROM pg_catalog.pg_tables\n", nativeQuery = true)
    String test();
}