package devanmejia.productshop.repositories.product_filter;

import devanmejia.productshop.models.stock.StockProduct;
import devanmejia.productshop.repositories.product_filter.predicate.ProductPredicate;
import devanmejia.productshop.transfer.filter.FilterParam;
import devanmejia.productshop.transfer.filter.ProductRange;
import devanmejia.productshop.transfer.filter.SortType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ProductFilterRepositoryImpl implements ProductFilterRepository {
    @Autowired
    private EntityManager entityManager;
    private CriteriaBuilder criteriaBuilder;

    @PostConstruct
    private void init(){
        this.criteriaBuilder = entityManager.getCriteriaBuilder();
    }

    @Override
    public List<StockProduct> getFilteredProductsInRange(FilterParam filterParam, ProductRange range){
        CriteriaQuery<StockProduct> query = criteriaBuilder.createQuery(StockProduct.class);
        Root<StockProduct> stockProductRoot = query.from(StockProduct.class);

        ProductPredicate predicateService = new ProductPredicate(criteriaBuilder, stockProductRoot);
        query.where(predicateService.getAllFilterPredicates(filterParam));

        List<Order> orderList = getSortParams(stockProductRoot, filterParam.getSortType());
        query.orderBy(orderList);

        TypedQuery<StockProduct> finalQuery = entityManager.createQuery(query)
                .setFirstResult(range.getStart()).setMaxResults(range.getEnd());
        return finalQuery.getResultList();
    }

    @Override
    public Long getFilteredProductsAmount(FilterParam filterParam){
        CriteriaQuery<Long> query = criteriaBuilder.createQuery(Long.class);
        Root<StockProduct> stockProductRoot = query.from(StockProduct.class);

        ProductPredicate predicateService = new ProductPredicate(criteriaBuilder, stockProductRoot);
        query.select(criteriaBuilder.count(stockProductRoot));
        query.where(predicateService.getAllFilterPredicates(filterParam));

        TypedQuery<Long> finalQuery = entityManager.createQuery(query);
        return finalQuery.getSingleResult();
    }

    private List<Order> getSortParams(Root<StockProduct> root, SortType sortType){
        List<Order> orderList = new ArrayList<>();
        if (sortType.equals(SortType.LOW_HIGH)){
            orderList.add(criteriaBuilder.asc(root.get("product").get("price")));
        }
        if (sortType.equals(SortType.HIGH_LOW)){
            orderList.add(criteriaBuilder.desc(root.get("product").get("price")));
        }
        return orderList;
    }
}
