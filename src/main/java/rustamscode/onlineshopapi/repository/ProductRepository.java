package rustamscode.onlineshopapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import rustamscode.onlineshopapi.model.Product;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    @Query("SELECT p FROM Product p " +
            "WHERE (LOWER(p.name) LIKE LOWER(CONCAT('%', :name, '%'))) OR :name IS NULL " +
            "AND (:minPrice IS NULL OR p.price >= :minPrice) " +
            "AND (:maxPrice IS NULL OR p.price <= :maxPrice) " +
            "AND (:available IS NULL OR p.available = :available) " +
            "ORDER BY " +
            "CASE WHEN :sortBy = 'name' AND :sortDirection = 'asc' THEN p.name END ASC, " +
            "CASE WHEN :sortBy = 'name' AND :sortDirection = 'desc' THEN p.name END DESC, " +
            "CASE WHEN :sortBy = 'price' AND :sortDirection = 'asc' THEN p.price END ASC, " +
            "CASE WHEN :sortBy = 'price' AND :sortDirection = 'desc' THEN p.price END DESC " +
            "LIMIT :limit")
    List<Product> filterAndSort(@Param("name") String name,
                                @Param("minPrice") Double minPrice,
                                @Param("maxPrice") Double maxPrice,
                                @Param("available") Boolean available,
                                @Param("sortBy") String sortBy,
                                @Param("sortDirection") String sortDirection,
                                @Param("limit") Integer limit);

}
