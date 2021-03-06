package com.hiagodias.hdcatalog.tests;

import com.hiagodias.hdcatalog.dto.ProductDTO;
import com.hiagodias.hdcatalog.entities.Category;
import com.hiagodias.hdcatalog.entities.Product;

import java.time.Instant;

public class Factory {

    public static Product createProduct() {
        Product product = new Product(1L, "Phone", "Good Phone", 800.0, "http://img.com/img.png", Instant.parse("2020-10-20T03:00:00Z"));

        product.getCategories().add(new Category(2L, "Eletronics"));

        return product;
    }


    public static ProductDTO createProductDTO() {
        Product product = createProduct();
        return new ProductDTO(product, product.getCategories());

    }


    public static Category createCategory() {
        return new Category(2L, "Eletronics");


    }

}
