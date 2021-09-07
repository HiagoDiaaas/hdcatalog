package com.hiagodias.hdcatalog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hiagodias.hdcatalog.entities.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
	

}
