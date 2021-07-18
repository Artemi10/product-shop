package devanmejia.productshop.services.products;

import devanmejia.productshop.models.product.Product;
import devanmejia.productshop.transfer.product.ProductDTO;
import org.springframework.stereotype.Service;


@Service
public interface ProductService {
    Product getProductByName(String productName);
    Product createNewProduct(ProductDTO productDTO);
    void updateProductPrice(String productName, int price);
}
