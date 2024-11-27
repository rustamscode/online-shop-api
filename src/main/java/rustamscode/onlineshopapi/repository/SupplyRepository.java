package rustamscode.onlineshopapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rustamscode.onlineshopapi.model.Supply;

@Repository
public interface SupplyRepository extends JpaRepository<Supply, Long> {
}
