package rustamscode.onlineshopapi.exception;

public class SaleNotFoundException extends RuntimeException {
    public SaleNotFoundException(Long id) {
        super("Документ продажи с ID " + id + " не найден");
    }
}
