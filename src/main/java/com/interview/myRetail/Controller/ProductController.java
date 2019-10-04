/**
 * $Id$
 *
 * Created: Oct 3, 2019
 */
package com.interview.myRetail.Controller;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.interview.myRetail.Entity.Price;
import com.interview.myRetail.Entity.Product;
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

	@RequestMapping(value = "/all", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Collection<Product>> getAllProducts()
	{
		Collection<Product> productList = productService.getAllProducts();
		return new ResponseEntity<Collection<Product>>(productList, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/add", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Product> addProduct(@RequestBody final Product product) throws Exception
	{
		final Product addedProduct = productService.addProduct(product);
		
		return new ResponseEntity<Product>(addedProduct, HttpStatus.OK);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Product> getProduct(@PathVariable final Integer id) throws Exception
	{
		final Product product = productService.findProductByID(id);

		return new ResponseEntity<Product>(product, HttpStatus.OK);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Product> updateProduct(@PathVariable final Integer id, @RequestBody final Price price)
			throws Exception
	{
		final Product product = productService.updateProductPriceById(id, price);

		return new ResponseEntity<Product>(product, HttpStatus.OK);
	}
}
