package com.interview.myRetail.Repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.interview.myRetail.Entity.Product;

public interface ProductRepoitory extends MongoRepository<Product, String>
{
//
//	/**
//	 * Retrieves {@code Product} by ID 
//	 * @param id
//	 * @return Product
//	 */
//	public Product getProductById(final String id);
//	
//	/**
//	 * updates {@code Product} with a new Price by ID.
//	 * @param id
//	 * @param price
//	 * @param currencyCode
//	 * @return the updated Product.
//	 */
//	public Product updateProductPriceById(final String id, final Price price);
	
}
