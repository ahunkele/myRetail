/**
 * $Id$
 *
 * Created: Oct 3, 2019
 */
package com.interview.myRetail.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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
       return productService.getProductInfoByID(id);
    }
}
