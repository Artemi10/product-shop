package devanmejia.productshop.transfer.filter;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PriceRange {
    private int maxValuePrice;
    private int minValuePrice;

    public boolean isValid(){
        return maxValuePrice >= minValuePrice;
    }
}
