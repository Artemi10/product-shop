package devanmejia.productshop.repositories.product_filter;

import devanmejia.productshop.models.stock.StockProduct;
import devanmejia.productshop.transfer.filter.FilterParam;
import devanmejia.productshop.transfer.filter.ProductRange;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductFilterRepository {
    List<StockProduct> getFilteredProductsInRange(FilterParam filterParam, ProductRange range);
    Long getFilteredProductsAmount(FilterParam filterParam);
}
