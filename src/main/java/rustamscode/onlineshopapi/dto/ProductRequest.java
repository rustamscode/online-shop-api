package rustamscode.onlineshopapi.dto;

import jakarta.validation.constraints.*;
import lombok.*;
import lombok.experimental.FieldDefaults;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductRequest {
    @NotNull
    @Size(min = 1, max = 255, message = "Название не должно быть пустым или превышать 255 символов!")
    String name;

    @Size(min = 1, max = 4096, message = "Описание не должно быть пустым или превышать 4096 символов!")
    String info;

    @Min(value = 0, message = "Цена должна быть больше 0!")
    double price;

    boolean available;
}
