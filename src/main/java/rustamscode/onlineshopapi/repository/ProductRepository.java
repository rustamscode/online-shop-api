package rustamscode.onlineshopapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rustamscode.onlineshopapi.model.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
