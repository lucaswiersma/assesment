package nl.jdriven.assesment.repository;

import nl.jdriven.assesment.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    @Query("SELECT p FROM Product p WHERE LOWER(p.name) LIKE %:searchTerm% OR LOWER(p.description) LIKE %:searchTerm%")
    Page<Product> searchByNameOrDescription(String searchTerm, Pageable pageable);
}
