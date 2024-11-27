package rustamscode.onlineshopapi.exception;

public class InsufficientStockException extends RuntimeException {
    public InsufficientStockException(Long id) {
        super("Товара с ID " + id + " недостаточно для совершения продажи");
    }
}
