/**
 * $Id$
 *
 * Created: Oct 3, 2019
 */
package com.interview.myRetail.Service;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.interview.myRetail.ExternalService.RedskyClient;
import com.interview.myRetail.Repositories.ProductRepoitory;

/**
 * 
 */
@Service
public final class ProductService
{

    protected Logger logger = LoggerFactory.getLogger(ProductService.class);
    
    @Autowired
    private ProductRepoitory productRepoitory;
    
    public String getProductInfoByID(final String id) throws JsonParseException, JsonMappingException, IOException
    {
        return RedskyClient.getProductInfoByID(id).getBody();
    }
    
}
