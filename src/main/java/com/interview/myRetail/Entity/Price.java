package com.interview.myRetail.Entity;

import java.math.BigDecimal;

import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Price object for Product
 */
@Document(collection = "Price")
public final class Price
{

    @JsonIgnore
    @Id
    private Integer id;

	@Field("price_value")
	@JsonProperty("value")
    private BigDecimal value;

	// Only allow code to be 3 chars
	@Length( max = 3, message = "Code can not be more than 3 characters")
	@Pattern(regexp = "^[^0-9]+$", message = "The currencyCode cannot contain digits")
	@Field("currency_code")
    @JsonProperty("currency_code")
    private String currencyCode;

    public Integer getId()
    {
        return id;
    }

    public void setId(final Integer id)
    {
        this.id = id;
    }

    public String getCurrencyCode()
    {
        return currencyCode;
    }

    public void setCurrencyCode(final String currencyCode)
    {
        this.currencyCode = currencyCode;
    }

    public BigDecimal getValue()
    {
        return value;
    }

    public void setValue(final BigDecimal value)
    {
        this.value = value;
    }

}
