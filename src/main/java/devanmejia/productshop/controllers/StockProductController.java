package devanmejia.productshop.controllers;

import devanmejia.productshop.models.stock.StockProduct;
import devanmejia.productshop.repositories.product_filter.ProductFilterRepository;
import devanmejia.productshop.services.stock_product.StockProductService;
import devanmejia.productshop.transfer.filter.FilterParam;
import devanmejia.productshop.transfer.filter.ProductRange;
import devanmejia.productshop.transfer.product.StockProductDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/shop")
public class StockProductController {
    @Autowired
    private StockProductService stockProductService;
    @Autowired
    private ProductFilterRepository productFilterRepository;

    @PostMapping("/stockProducts/amount")
    public Long getAvailableStockProductAmount(@RequestBody FilterParam filterParam){
        return productFilterRepository.getFilteredProductsAmount(filterParam);
    }

    @GetMapping("/stockProducts/price/min")
    public int getAvailableStockProductMinPrice(){
        return stockProductService.getMinStockProductPrice();
    }

    @GetMapping("/stockProducts/price/max")
    public int getAvailableStockProductManPrice(){
        return stockProductService.getMaxStockProductPrice();
    }

    @GetMapping("/admin/stockProducts/amount")
    public int getAllStockProductAmount(){
        return stockProductService.getAllStockProductAmount();
    }

    @PostMapping("/stockProducts/{first}/{last}")
    public List<StockProduct> getAvailableStockProductsByFilter(@PathVariable int first, @PathVariable int last, @RequestBody FilterParam filterParam){
        ProductRange productRange = new ProductRange(first, last);
        return productFilterRepository.getFilteredProductsInRange(filterParam, productRange);
    }

    @GetMapping("/admin/stockProducts/{first}/{last}")
    public List<StockProduct> getAllStockProducts(@PathVariable int first, @PathVariable int last){
        return stockProductService.getAllStockProductsInRange(first, last);
    }

    @PatchMapping("/admin/stockProduct")
    public void addProductAmount(@RequestBody StockProductDTO stockProductDTO) {
        stockProductService.addProductToStock(stockProductDTO);
    }
}
