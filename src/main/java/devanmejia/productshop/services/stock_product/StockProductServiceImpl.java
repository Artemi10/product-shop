package devanmejia.productshop.services.stock_product;

import devanmejia.productshop.models.product.Product;
import devanmejia.productshop.models.stock.StockProduct;
import devanmejia.productshop.repositories.product.StockProductRepository;
import devanmejia.productshop.transfer.product.StockProductDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StockProductServiceImpl implements StockProductService{
    @Autowired
    private StockProductRepository stockProductRepository;

    @Override
    public int getAvailableStockProductAmount(){
        return stockProductRepository.getAvailableStockProductAmount();
    }

    @Override
    public int getAllStockProductAmount(){
        return stockProductRepository.getAllStockProductAmount();
    }

    @Override
    public void addProductToStock(StockProductDTO stockProductDTO){
        String productName = stockProductDTO.getProductName();
        StockProduct stockProduct = stockProductRepository
                .findByProductName(productName)
                .orElseThrow(() -> new IllegalArgumentException(String.format("No such product in the stock(%s)", productName)));
        stockProduct.setAmount(stockProductDTO.getAmount());
        stockProductRepository.save(stockProduct);
    }

    @Override
    public void addNewProductToStock(Product product){
        String productName = product.getName();
        Optional<StockProduct> stockProductOptional =
                stockProductRepository.findByProductName(productName);
        if (!stockProductOptional.isPresent()){
            StockProduct stockProduct = StockProduct.builder()
                    .product(product)
                    .amount(0).build();
            stockProductRepository.save(stockProduct);
        }
        else {
            throw new IllegalArgumentException(String.format("%s has been already added to the stock", productName));
        }
    }

    @Override
    public List<StockProduct> getAvailableStockProductsInRange(int firstPosition, int lastPosition){
        if(firstPosition < lastPosition){
            int amount = lastPosition - firstPosition;
            Pageable pageable = PageRequest.of(firstPosition, amount);
            return stockProductRepository.getAvailableStockProducts(pageable);
        }
        throw new IllegalArgumentException("Range is invalid");
    }

    @Override
    public List<StockProduct> getAllStockProductsInRange(int firstPosition, int lastPosition){
        if(firstPosition < lastPosition){
            int amount = lastPosition - firstPosition;
            Pageable pageable = PageRequest.of(firstPosition, amount);
            return stockProductRepository.getAllStockProducts(pageable);
        }
        throw new IllegalArgumentException("Range is invalid");
    }

    @Override
    public int getMinStockProductPrice() {
        return stockProductRepository.getMinStockProductPrice();
    }

    @Override
    public int getMaxStockProductPrice() {
        return stockProductRepository.getMaxStockProductPrice();
    }
}
