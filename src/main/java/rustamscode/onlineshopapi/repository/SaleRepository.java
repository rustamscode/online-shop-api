package rustamscode.onlineshopapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rustamscode.onlineshopapi.model.Sale;

@Repository
public interface SaleRepository extends JpaRepository<Sale, Long> {
}
