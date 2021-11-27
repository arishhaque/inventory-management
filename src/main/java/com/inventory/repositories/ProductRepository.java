package com.inventory.repositories;

import com.inventory.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query(value = "select * from product where product_Id = :productId", nativeQuery = true)
    Product findByProductId(@Param("productId") String productId);

    @Query(value = "select * from product", nativeQuery = true)
    List<Product> findAll();
}
