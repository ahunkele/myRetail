/**
 * $Id$
 *
 * Created: Oct 3, 2019
 */
package com.interview.myRetail.Controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.interview.myRetail.Service.ProductService;


/**
 *
 */
@RestController
@RequestMapping("/products")
public class ProductController
{
    @Autowired
    private ProductService productService;
    
    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public String getProduct(@PathVariable final String id) throws Exception
    {
       System.out.println(getProductInfoByID(id).getHeaders());
       return productService.getProductInfoByID(id);
    }
    static final String BASE_URL = "https://redsky.target.com/v2/pdp/tcin/";
    static final String EXCLUSION_QUERY = "?excludes=taxonomy,price,promotion,bulk_ship,rating_and_review_reviews,rating_and_review_statistics,question_answer_statistics";
    
    public static ResponseEntity<String> getProductInfoByID(final String id) throws JsonParseException, JsonMappingException, IOException
    {
        RestTemplate restTemplate = new RestTemplate();
        String productInformationURL = BASE_URL + id + EXCLUSION_QUERY;
        ResponseEntity<String> response = restTemplate.getForEntity(productInformationURL, String.class);
       
        System.out.println(response.getBody());
        
        return response;
    }
    
}
