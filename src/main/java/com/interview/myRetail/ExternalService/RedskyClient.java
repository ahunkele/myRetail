package com.interview.myRetail.ExternalService;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.interview.myRetail.Service.ProductService;

/**
 * Client for making external calls to Redsky.
 */
public class RedskyClient
{
	protected Logger logger = LoggerFactory.getLogger(ProductService.class);
	
	static final String BASE_URL = "https://redsky.target.com/v2/pdp/tcin/";

	static final String EXCLUSION_QUERY = "?excludes=taxonomy,price,promotion,bulk_ship,rating_and_review_reviews,rating_and_review_statistics,question_answer_statistics";

	/**
	 * Call redsky and grab the product by id.
	 * @param id
	 * @return {@link ResponseEntity}
	 * @throws JsonParseException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	public static ResponseEntity<String> getProductInfoByID(final Integer id) throws Exception
	{
		RestTemplate restTemplate = new RestTemplate();
		String productInformationURL = BASE_URL + id + EXCLUSION_QUERY;

		ResponseEntity<String> response = null;
		try
		{
			response = restTemplate.getForEntity(productInformationURL, String.class);
		} catch (final HttpClientErrorException e)
		{
			System.out.println(e.getStatusCode());
			System.out.println(e.getResponseBodyAsString());
		}

		return response;
	}
}
