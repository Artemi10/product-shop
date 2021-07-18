package devanmejia.productshop.transfer.filter;

import devanmejia.productshop.models.product.Type;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FilterParam {
    private List<Type> productTypes;
    public PriceRange priceRange;
    public SortType sortType;
}
