package com.interview.myRetail.Controller;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.interview.myRetail.Entity.Price;
import com.interview.myRetail.Entity.Product;
import com.interview.myRetail.Service.ProductService;

/**
 * Product Controller for {@link ProductService}
 * 
 * @author Andy
 *
 */
@RestController
@RequestMapping("/products")
public class ProductController
{
	@Autowired
	private ProductService productService;

	/**
	 * {@link GetMapping} for all products.
	 * 
	 * @return {@link ResponseEntity}
	 */
	@GetMapping
	public ResponseEntity<Collection<Product>> getAllProducts()
	{
		Collection<Product> productList = productService.getAllProducts();
		
		return new ResponseEntity<Collection<Product>>(productList, HttpStatus.OK);
	}

	/**
	 * {@link PostMapping} to Add a product.
	 * 
	 * @param product
	 * @return {@link ResponseEntity}
	 * @throws Exception
	 */
	@RequestMapping(value = "/add", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Product> addProduct(@RequestBody final Product product) throws Exception
	{
		final Product addedProduct = productService.addProduct(product);

		return new ResponseEntity<Product>(addedProduct, HttpStatus.OK);
	}

	/**
	 * {@link GetMapping} to retrieve the product.
	 * 
	 * @param id the product id.
	 * @return {@link ResponseEntity}
	 * @throws Exception
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Product> getProduct(@PathVariable final Integer id) throws Exception
	{
		final Product product = productService.findProductByID(id);

		return new ResponseEntity<Product>(product, HttpStatus.OK);
	}

	/**
	 * {@link PostMapping} to update a product price.
	 * 
	 * @param id the id of the product.
	 * @param price the price to update
	 * @return {@link ResponseEntity}
	 * @throws Exception
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Product> updateProduct(@PathVariable final Integer id, @RequestBody final Price price)
			throws Exception
	{
		final Product product = productService.updateProductPriceById(id, price);

		return new ResponseEntity<Product>(product, HttpStatus.OK);
	}
	
	/**
	 * {@link PostMapping} to delete a product.
	 * 
	 * @param id the id of the product.
	 * @return {@link ResponseEntity}
	 * @throws Exception
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	public void deleteProduct(@PathVariable final Integer id)
			throws Exception
	{
		productService.deleteProductById(id);
	}
}
