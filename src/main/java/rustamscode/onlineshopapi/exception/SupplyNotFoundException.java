package rustamscode.onlineshopapi.exception;

public class SupplyNotFoundException extends RuntimeException {
    public SupplyNotFoundException(Long id) {
        super("Поставка с ID " + id + " не найдена");
    }
}
