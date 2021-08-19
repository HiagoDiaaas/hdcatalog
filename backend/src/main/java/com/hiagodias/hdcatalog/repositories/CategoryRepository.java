package com.hiagodias.hdcatalog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hiagodias.hdcatalog.entities.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
	

}
