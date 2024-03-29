package com.interview.myRetail.Repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.interview.myRetail.Entity.Product;

/**
 * Product MongoDB repository.
 * 
 * @author Andy
 *
 */
public interface ProductRepoitory extends MongoRepository<Product, Integer>
{
	
}
