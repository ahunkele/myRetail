package com.interview.myRetail.Entity;

import javax.validation.Valid;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * The Product object.
 * 
 * @author Andy
 *
 */
@Document(collection = "Product")
public final class Product
{

	@Id
	private Integer id;

	@Field("product_name")
	@JsonProperty("name")
	private String name;

	@Valid
	@JsonProperty("current_price")
	private Price price;

	/**
	 * @return the id
	 */
	public Integer getId()
	{
		return id;
	}

	/**
	 * @return the name
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name)
	{
		this.name = name;
	}

	/**
	 * @return the price
	 */
	public Price getPrice()
	{
		return price;
	}

	/**
	 * @param price the price to set
	 */
	public void setPrice(Price price)
	{
		this.price = price;
	}
}
