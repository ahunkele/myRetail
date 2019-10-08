package com.interview.myRetail;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;
import static org.skyscreamer.jsonassert.JSONAssert.assertEquals;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import com.interview.myRetail.Controller.ProductController;
import com.interview.myRetail.Entity.Price;
import com.interview.myRetail.Entity.Product;
import com.interview.myRetail.Service.ProductService;

/**
*
*/
@WebMvcTest(value = ProductController.class)
@RunWith(SpringRunner.class)
public class ProductControllerTest
{
    private static final String PRODUCT_1_NAME = "The Big Lebowski (Blu-ray)";

    private static final String PRODUCT_2_NAME = "Conan the Barbarian (dvd_video)";

    private static final Integer PRUDUCT_1_ID = 13860428;

    private static final Integer PRUDUCT_2_ID = 13860427;

    private static final Price PRRICE_1 = new Price();

    private static final Price PRRICE_2 = new Price();
    
    private static final Price UPDATE_PRICE = new Price();

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    private Collection<Product> allProducts;

    private Product product;

    private Product product2;

    @Before
    public void setup()
    {
        MockitoAnnotations.initMocks(this);
        
        allProducts = new ArrayList<Product>();

        UPDATE_PRICE.setId(PRUDUCT_1_ID);
        UPDATE_PRICE.setCurrencyCode("CAD");
        UPDATE_PRICE.setValue(new BigDecimal(10.00));
        
        PRRICE_1.setCurrencyCode("USD");
        PRRICE_1.setId(PRUDUCT_1_ID);
        PRRICE_1.setValue(new BigDecimal(20.00));
        product = new Product(PRUDUCT_1_ID, PRODUCT_1_NAME, PRRICE_1);
        allProducts.add(product);

        PRRICE_2.setCurrencyCode("JPN");
        PRRICE_2.setId(PRUDUCT_2_ID);
        PRRICE_2.setValue(new BigDecimal(10000));
        product2 = new Product(PRUDUCT_2_ID, PRODUCT_2_NAME, PRRICE_2);
        allProducts.add(product2);
    }

    @Test
    public void getAllProducts_test() throws Exception
    {
        when(productService.getAllProducts()).thenReturn(allProducts);

        final MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/products")).andReturn();

        final String expected = "[{\"id\":13860428,\"name\":\"The Big Lebowski (Blu-ray)\",\"current_price\":{\"value\":20,\"currency_code\":\"USD\"}}," +
                                "{\"id\":13860427,\"name\":\"Conan the Barbarian (dvd_video)\",\"current_price\":{\"value\":10000,\"currency_code\":\"JPN\"}}]";

        assertEquals(expected, result.getResponse().getContentAsString(), false);
    }
    
    @Test
    public void getProductByID_test() throws Exception
    {
        when(productService.findProductByID(PRUDUCT_1_ID)).thenReturn(product);
        
        final MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/products/" + PRUDUCT_1_ID)).andReturn();
        
        final String expected = "{\"id\":13860428,\"name\":\"The Big Lebowski (Blu-ray)\",\"current_price\":{\"value\":20,\"currency_code\":\"USD\"}}";
        
        assertEquals(expected, result.getResponse().getContentAsString(), false);
    }

    @Test(expected = MethodArgumentTypeMismatchException.class)
    public void getProductByID_badArguments_test() throws Exception
    {
        final String badArgs = "bad";
        
        final MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/products/" + badArgs).accept(MediaType.APPLICATION_JSON)).andReturn();
        
        assertThat(result.getResponse().getStatus(), is(HttpStatus.BAD_REQUEST.value()));
        
        throw result.getResolvedException();
    }
    
    @Test
    public void addProduct_test() throws Exception
    {
        Mockito.when(productService.addProduct(Mockito.any())).thenReturn(product);
        
        final MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/products/add")
                                                 .accept(MediaType.APPLICATION_JSON)
                                                 .content("{\"id\":13860428,\"name\":\"The Big Lebowski (Blu-ray)\",\"current_price\":{\"value\":20,\"currency_code\":\"USD\"}}")
                                                 .contentType(MediaType.APPLICATION_JSON_VALUE)).andReturn();
        
       final String expected = "{\"id\":13860428,\"name\":\"The Big Lebowski (Blu-ray)\",\"current_price\":{\"value\":20,\"currency_code\":\"USD\"}}";

       assertEquals(expected, result.getResponse().getContentAsString(), false);
    }
    
    @Test
    public void updateProductPriceByID_test() throws Exception
    {
        Product prod = new Product(PRUDUCT_1_ID, PRODUCT_1_NAME, UPDATE_PRICE);
        Mockito.when(productService.updateProductPriceById(Mockito.anyInt(), Mockito.any())).thenReturn(prod);

        final MvcResult result = mockMvc.perform(MockMvcRequestBuilders.put("/products/" + PRUDUCT_1_ID)
                                                 .accept(MediaType.APPLICATION_JSON)
                                                 .content("{\"value\": 10.00, \"currency_code\": \"CAD\"}")
                                                 .contentType(MediaType.APPLICATION_JSON_VALUE)).andReturn();
        
        final String expected = "{\"id\":13860428,\"name\":\"The Big Lebowski (Blu-ray)\",\"current_price\":{\"value\":10,\"currency_code\":\"CAD\"}}";
        
        assertEquals(expected, result.getResponse().getContentAsString(), false);
    }
    
    @Test
    public void deleteProductByID_test() throws Exception
    {
        final MvcResult result = mockMvc.perform(MockMvcRequestBuilders.delete("/products/" + PRUDUCT_1_ID)).andReturn();
        
        assertThat(result.getResponse().getStatus(), is(HttpStatus.OK.value()));
        
    }

}
