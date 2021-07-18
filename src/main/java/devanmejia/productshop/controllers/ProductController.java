package devanmejia.productshop.controllers;

import devanmejia.productshop.models.product.Product;
import devanmejia.productshop.services.products.ProductService;
import devanmejia.productshop.services.stock_product.StockProductService;
import devanmejia.productshop.transfer.product.ProductDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/shop")
public class ProductController {
    @Autowired
    private ProductService productService;
    @Autowired
    private StockProductService stockProductService;

    @PostMapping("/admin/product")
    public Product addNewProduct(@RequestBody ProductDTO productDTO) {
        Product product = productService.createNewProduct(productDTO);
        stockProductService.addNewProductToStock(product);
        return product;
    }

    @PatchMapping("/admin/product/{name}/price/{price}")
    public void updateProductPrice(@PathVariable String name, @PathVariable int price){
        productService.updateProductPrice(name, price);
    }

}
