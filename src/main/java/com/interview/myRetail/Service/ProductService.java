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

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.interview.myRetail.Entity.Price;
import com.interview.myRetail.Entity.Product;
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
	ProductRepoitory productRepoitory;

	/**
	 * 
	 * @return
	 */
	public Collection<Product> getAllProducts()
	{
		return productRepoitory.findAll();
	}

	/**
	 * 
	 * @param id
	 * @return
	 * @throws IOException
	 * @throws JsonMappingException
	 * @throws JsonParseException
	 */
	public Product findProductByID(final Integer id) throws JsonParseException, JsonMappingException, IOException
	{
		final String productName = grabProductName(RedskyClient.getProductInfoByID(id));
		
		final Product product = productRepoitory.findById(id).get();
		product.setName(productName);

		return product;
	}

	/**
	 * 
	 * @param product
	 * @return
	 * @throws IOException 
	 * @throws JsonMappingException 
	 * @throws JsonParseException 
	 */
	public Product addProduct(final Product product) throws JsonParseException, JsonMappingException, IOException
	{
		final String productName = grabProductName(RedskyClient.getProductInfoByID(product.getId()));
		product.setName(productName);
		
		return productRepoitory.insert(product);
	}

	/**
	 * 
	 * @param id
	 * @param price
	 * @return
	 */
	public Product updateProductPriceById(final Integer id, final Price price)
	{
		Product product = productRepoitory.findById(id).get();
		product.setPrice(price);
		return productRepoitory.save(product);
	}

	/**
	 * 
	 * @param id
	 */
	public void deleteProductById(final Integer id)
	{
		productRepoitory.deleteById(id);
	}

	/**
	 * 
	 * @param response
	 * @return
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
