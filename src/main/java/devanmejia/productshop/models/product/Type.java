package devanmejia.productshop.models.product;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum Type {
    VEGETABLES,
    DAIRY,
    FRUITS,
    DRINKS,
    SWEETS;

    public List<String> getAllProductTypes(){
        return Arrays.stream(Type.values())
                .map(Enum::name).collect(Collectors.toList());
    }
}
