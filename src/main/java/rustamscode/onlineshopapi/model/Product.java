package rustamscode.onlineshopapi.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Product {
    Long id;
    String name;
    String info;
    BigDecimal price = BigDecimal.valueOf(0);
    boolean available = false;

}
