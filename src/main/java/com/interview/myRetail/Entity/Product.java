package com.interview.myRetail.Entity;


import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Product 
 * @author Andy
 *
 */
@Document(collection = "Product")
public final class Product
{
    
    @Id
    private Long id;

    @Transient
    private String name;
    
    @JsonProperty
    private Price price;
    
    /**
     * @return the id
     */
    public Long getId()
    {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Long id)
    {
        this.id = id;
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
