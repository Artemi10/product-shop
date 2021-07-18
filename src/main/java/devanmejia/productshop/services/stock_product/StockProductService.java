package devanmejia.productshop.services.stock_product;

import devanmejia.productshop.models.product.Product;
import devanmejia.productshop.models.stock.StockProduct;
import devanmejia.productshop.transfer.product.StockProductDTO;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public interface StockProductService {
    int getMinStockProductPrice();
    int getMaxStockProductPrice();
    int getAvailableStockProductAmount();
    int getAllStockProductAmount();
    void addProductToStock(StockProductDTO stockProductDTO);
    void addNewProductToStock(Product product);
    List<StockProduct> getAvailableStockProductsInRange(int firstPosition, int lastPosition);
    List<StockProduct> getAllStockProductsInRange(int firstPosition, int lastPosition);
}
