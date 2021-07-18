package devanmejia.productshop.repositories.product_filter.predicate;


import devanmejia.productshop.models.product.Type;
import devanmejia.productshop.models.stock.StockProduct;
import devanmejia.productshop.transfer.filter.FilterParam;
import devanmejia.productshop.transfer.filter.PriceRange;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

public class ProductPredicate {
    private final CriteriaBuilder criteriaBuilder;
    private final Root<StockProduct> stockRoot;

    public ProductPredicate(CriteriaBuilder criteriaBuilder, Root<StockProduct> stockRoot) {
        this.criteriaBuilder = criteriaBuilder;
        this.stockRoot = stockRoot;
    }

    public Predicate[] getAllFilterPredicates(FilterParam filterParam) {
        Predicate[] predicates = new Predicate[3];
        predicates[0] = createProductTypePredicate(filterParam.getProductTypes());
        predicates[1] = createPricePredicate(filterParam.getPriceRange());
        predicates[2] = createAmountPredicate();
        return predicates;
    }

    private Predicate createProductTypePredicate(List<Type> types) {
        Predicate productTypePredicate = criteriaBuilder.conjunction();
        if(types != null && types.size() != 0){
            productTypePredicate = criteriaBuilder.or(types.stream()
                    .map(type -> criteriaBuilder.equal(stockRoot.get("product").get("type"), type))
                    .toArray(Predicate[]::new));
        }
        return productTypePredicate;
    }

    private Predicate createPricePredicate(PriceRange range) {
        Predicate statusPredicate = criteriaBuilder.conjunction();
        if(range != null && range.isValid()){
            statusPredicate = criteriaBuilder.between(stockRoot.get("product").get("price"), range.getMinValuePrice(), range.getMaxValuePrice());
        }
        return statusPredicate;
    }

    private Predicate createAmountPredicate() {
        return criteriaBuilder.greaterThan(stockRoot.get("amount"), 0);
    }
}
