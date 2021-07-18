package devanmejia.productshop.services.products;

import devanmejia.productshop.models.product.Product;
import devanmejia.productshop.repositories.product.ProductRepository;
import devanmejia.productshop.transfer.product.ProductDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductRepository productRepository;

    @Override
    public Product getProductByName(String productName){
        return productRepository.findByName(productName)
                .orElseThrow(() -> new IllegalArgumentException(String.format("No such product (%s)", productName)));
    }

    @Override
    public Product createNewProduct(ProductDTO productDTO){
        String productName = productDTO.getName();
        Optional<Product> productOptional = productRepository.findByName(productName);
        if (productOptional.isEmpty()){
            Product product = Product.builder()
                    .name(productName)
                    .price(productDTO.getPrice())
                    .description(productDTO.getDescription())
                    .type(productDTO.getType()).build();
            return productRepository.save(product);
        }
        else {
            throw new IllegalArgumentException(String.format("%s has already been created", productName));
        }
    }

    @Override
    public void updateProductPrice(String productName, int price){
        Product product = productRepository.findByName(productName)
                .orElseThrow(() -> new IllegalArgumentException(String.format("No such product (%s)", productName)));
        product.setPrice(price);
        productRepository.save(product);
    }
}
