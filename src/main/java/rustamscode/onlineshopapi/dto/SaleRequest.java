package rustamscode.onlineshopapi.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SaleRequest {
    @NotNull
    @Size(min = 1, max = 255, message = "Название не должно быть пустым или превышать 255 символов!")
    String name;

    @NotNull
    Long productId;

    @Min(1)
    int amount;
}
