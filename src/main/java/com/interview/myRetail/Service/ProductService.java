/**
 * $Id$
 *
 * Created: Oct 3, 2019
 */
package com.interview.myRetail.Service;

import static org.springframework.util.Assert.notNull;

import java.io.IOException;
import java.util.Collection;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.interview.myRetail.Entity.Price;
import com.interview.myRetail.Entity.Product;
import com.interview.myRetail.Exception.ProductNotFoundException;
import com.interview.myRetail.ExternalService.RedskyClient;
import com.interview.myRetail.Repositories.ProductRepoitory;

/**
 * 
 */
@Service
public class ProductService
{

	private static final Logger LOGGER = LoggerFactory.getLogger(ProductService.class);

	@Autowired
	private ProductRepoitory productRepoitory;

	/**
	 * 
	 * @return
	 */
	public Collection<Product> getAllProducts()
	{
		return productRepoitory.findAll();
	}

	/**
	 * find {@link Product} by ID 
	 * @param id the id
	 * @return the found Product
	 * @throws Exception
	 */
	public Product findProductByID(final Integer id) throws Exception
	{
		final String productName = grabProductName(RedskyClient.getProductInfoByID(id));
		if(productName == null)
		{
			LOGGER.error("Product was not found with id: " + id);
			throw new ProductNotFoundException("Product title could not be found with id: " + id);
		}
		
		final Product product = productRepoitory.findById(id).orElse(null);
		if(product == null)
		{
			LOGGER.error("Product was not found with id: " + id);
			throw new ProductNotFoundException("Product pricing could not be found with id: " + id);
		}
		product.setName(productName);

		return product;
	}

	/**
	 * add {@link Product}
	 * @param product the product to add.
	 * @return the Product that was added.
	 * @throws Exception 
	 */
	public Product addProduct(final Product product) throws Exception
	{
		final String productName = grabProductName(RedskyClient.getProductInfoByID(product.getId()));
		product.setName(productName);
		
		LOGGER.info("Added Product with id: " + product.getId());
		return productRepoitory.insert(product);
	}

	/**
	 * Update {@link Product} with a new {@link Price} by Product id.
	 * @param id the product id.
	 * @param price the update price.
	 * @return the updated product.
	 */
	public Product updateProductPriceById(final Integer id, final Price price)
	{
		Product product = productRepoitory.findById(id).orElse(null);
		if(product == null)
		{
			throw new ProductNotFoundException("Product could not be found with id: " + id);
		}
		
		product.setPrice(price);
		return productRepoitory.save(product);
	}

	/**
	 * Delete product by id.
	 * @param id the product id to delete.
	 */
	public void deleteProductById(final Integer id)
	{
		productRepoitory.deleteById(id);
	}

	/**
	 * Grab product name from JSON response.
	 * @param response the JSON response 
	 * @return the product name.
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private String grabProductName(final ResponseEntity<String> response)
	{
		final ObjectMapper mapper = new ObjectMapper();
		String productName = null;

		try
		{
			final Map<String, Map> details = mapper.readValue(response.getBody(), Map.class);
			final Map<String, Map> productDetails = details.get("product");
			final Map<String, Map> itemDetails = productDetails.get("item");
			final Map<String, String> productDescription = itemDetails.get("product_description");
			productName = productDescription.get("title");
		} catch (Exception e)
		{
			throw new RestClientException("Could not grab product's name from external service. Product name: " + e.getMessage());
		}

		// Verify product name is not null
		notNull(productName, "Product name was null.");

		return productName;

	}

}
