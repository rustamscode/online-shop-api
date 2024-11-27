package rustamscode.onlineshopapi.exception;

public class OutOfStockException extends RuntimeException {
    public OutOfStockException(Long id) {
        super("Товара с ID " + id + " нет в наличии");
    }
}
