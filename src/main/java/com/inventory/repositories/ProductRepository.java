package com.inventory.repositories;

import com.inventory.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query(value = "select * from product where product_id = :productId ", nativeQuery = true)
    Product findByProductId(@Param("productId") String productId);

    @Query(value = "select * from product LIMIT :startRange, :endRange ", nativeQuery = true)
    List<Product> findAll(@Param("startRange") Integer startRange, @Param("endRange") Integer endRange);

    @Query(value = "select * from product where active = :active LIMIT :startRange, :endRange ", nativeQuery = true)
    List<Product> findByActive(@Param("active") Boolean active,
                               @Param("startRange") Integer startRange, @Param("endRange") Integer endRange);

    @Query(value = "select * from product where upper(product_id) like upper(:searchKey) \n" +
            "OR upper(name) like upper(:searchKey) ", nativeQuery = true)
    List<Product> searchProducts(@Param("searchKey") String searchKey);
}
