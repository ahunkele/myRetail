package com.interview.myRetail;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import com.interview.myRetail.Entity.Price;
import com.interview.myRetail.Entity.Product;
import com.interview.myRetail.Exception.ProductNotFoundException;
import com.interview.myRetail.ExternalService.RedskyClient;
import com.interview.myRetail.Repositories.ProductRepoitory;
import com.interview.myRetail.Service.ProductService;

/**
 * 
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductServiceTest
{

    private static final String PRODUCT_1_NAME = "The Big Lebowski (Blu-ray)";

    private static final Integer PRUDUCT_1_ID = 13860428;
    
    private final Price price = new Price();
    
    private Product product;
    
    private Optional<Product> optionalProduct;
    
    @MockBean
    private ProductRepoitory productRepoitory;
    
    @MockBean
    private RedskyClient redskyClient;
    
    @Autowired
    private ProductService productService;
    
    @Before
    public void setup()
    {
        price.setCurrencyCode("USD");
        price.setId(PRUDUCT_1_ID);
        price.setValue(new BigDecimal(20));
        
        product = new Product(PRUDUCT_1_ID, PRODUCT_1_NAME, price);
    }
    
    @Test
    public void grabAllProducts_test()
    {
        when(productRepoitory.findAll()).thenReturn(Collections.singletonList(product));
        
        final Collection<Product> allProducts = productService.getAllProducts();

        allProducts.forEach(actualProd -> assertThat(actualProd, is(product)));
    }
    
    @Test
    public void addProduct_HappyPath_test() throws Exception
    {
        when(productRepoitory.insert(product)).thenReturn(product);
        
        final Product actual = productService.addProduct(product);
        
        assertThat(actual, is(product));
    }
    
    @Test
    public void findProductById_HappyPath_test() throws Exception
    {
        optionalProduct = Optional.of(product);
        when(productRepoitory.findById(PRUDUCT_1_ID)).thenReturn(optionalProduct);
        
        final Product actual = productService.findProductByID(PRUDUCT_1_ID);
        
        assertThat(actual, is(product));
    }
    
    @Test(expected = ProductNotFoundException.class)
    public void findProductByID_doesntExist_test() throws Exception
    {
         productService.findProductByID(PRUDUCT_1_ID);
    }
    
    @Test
    public void updateProductByID_HappyPath_test()
    {
        final Price price = new Price();
        price.setId(PRUDUCT_1_ID);
        price.setCurrencyCode("CAD");
        price.setValue(new BigDecimal(20));
        
        optionalProduct = Optional.of(product);
        when(productRepoitory.findById(PRUDUCT_1_ID)).thenReturn(optionalProduct);
        when(productRepoitory.save(product)).thenReturn(product);
        
        final Product actual = productService.updateProductPriceById(PRUDUCT_1_ID, price);
        
        assertThat(actual, is(product));
    }
}
