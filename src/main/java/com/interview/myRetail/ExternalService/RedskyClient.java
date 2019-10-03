package com.interview.myRetail.ExternalService;

import java.io.IOException;

import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

/**
 * 
 */
public class RedskyClient
{
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
