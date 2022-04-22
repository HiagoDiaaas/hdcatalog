package com.hiagodias.hdcatalog.services;


import com.hiagodias.hdcatalog.dto.ProductDTO;
import com.hiagodias.hdcatalog.entities.Category;
import com.hiagodias.hdcatalog.entities.Product;
import com.hiagodias.hdcatalog.repositories.CategoryRepository;
import com.hiagodias.hdcatalog.repositories.ProductRepository;
import com.hiagodias.hdcatalog.services.exceptions.DatabaseException;
import com.hiagodias.hdcatalog.services.exceptions.ResourceNotFoundException;
import com.hiagodias.hdcatalog.tests.Factory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@ExtendWith(SpringExtension.class)
public class ProductServiceTests {

    @InjectMocks
    private  ProductService service;

    @Mock
    private ProductRepository repository;


    @Mock
    private CategoryRepository categoryRepository;

    private long existingId;
    private long nonExistingId;
    private long dependentId;
    private PageImpl<Product> page;
    private Product product;
    private Category category;
    ProductDTO productDTO;


    @BeforeEach
    void setUp() throws Exception {
        existingId = 1L;
        nonExistingId = 2L;
        dependentId = 3L;
        product = Factory.createProduct();
        page = new PageImpl<>(List.of(product));
        category = Factory.createCategory();
        productDTO = Factory.createProductDTO();


        Mockito.when(repository.getOne(existingId)).thenReturn(product);
        Mockito.when(repository.getOne(nonExistingId)).thenThrow(EntityNotFoundException.class);

        Mockito.when(categoryRepository.getOne(existingId)).thenReturn(category);
        Mockito.when(categoryRepository.getOne(nonExistingId)).thenThrow(EntityNotFoundException.class);


        Mockito.when(repository.findById(existingId)).thenReturn(Optional.of(product));
        Mockito.when(repository.findById(nonExistingId)).thenReturn(Optional.empty());

        Mockito.when(repository.save(ArgumentMatchers.any())).thenReturn(product);


        Mockito.when(repository.findAll((Pageable)ArgumentMatchers.any())).thenReturn(page);


        Mockito.doNothing().when(repository).deleteById(existingId);
        Mockito.doThrow(EmptyResultDataAccessException.class).when(repository).deleteById(nonExistingId);
        Mockito.doThrow(DataIntegrityViolationException.class).when(repository).deleteById(dependentId);



    }




    @Test
    public void updateShouldReturnProductDTOWhenIdExists() {



        ProductDTO result = service.update(existingId, productDTO);

        Assertions.assertNotNull(result);


    }



    @Test
    public void updateShouldThrowResourceNotFoundExceptionWhenIdDoesNotExist() {

        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            service.update(nonExistingId, productDTO);

        });


    }



    @Test
    public void findByIdShouldThrowResourceNotFoundExceptionWhenIdDoesNotExist() {


        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            service.findById(nonExistingId);

        });


    }



    @Test
    public void findByIdShouldReturnProductDTOWhenIdExists() {

        ProductDTO result = service.findById(existingId);
        Assertions.assertNotNull(result);


    }



    @Test
    public void findAllPagedShouldReturnPage(){

        Pageable pageable = PageRequest.of(0, 10);

        Page<ProductDTO> result = service.findAllPaged(pageable);

        Assertions.assertNotNull(result);

        //Mockito.verify(repository.findAll(pageable));




    }



    @Test
    public void deleteShouldThrowDatabaseExceptionWhenIdIsDependent() {


        Assertions.assertThrows(DatabaseException.class, () -> {
            service.delete(dependentId);
        });

        Mockito.verify(repository, Mockito.times(1)).deleteById(dependentId);

    }



    @Test
    public void deleteShouldThrowEmptyResourceNotFoundExceptionWhenIdDoesNotExist() {


        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            service.delete(nonExistingId);
        });

        Mockito.verify(repository, Mockito.times(1)).deleteById(nonExistingId);

    }



    @Test
    public void deleteShouldDoNothingWhenIdExists() {

        Assertions.assertDoesNotThrow(()-> {
            service.delete(existingId);
        });

        Mockito.verify(repository, Mockito.times(1)).deleteById(existingId);

    }






























}
