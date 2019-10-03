package com.interview.myRetail.Entity;

import java.math.BigDecimal;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.interview.myRetail.Entity.utils.CurrencyCode;

/**
 * 
 */
@Document(collection = "Price")
public final class Price
{

    @JsonIgnore
    @Id
    private String id;

    private BigDecimal value;

    @JsonProperty("currency_code")
    @Field
    private CurrencyCode currencyCode;

    public String getId()
    {
        return id;
    }

    public void setId(final String id)
    {
        this.id = id;
    }

    public CurrencyCode getCurrencyCode()
    {
        return currencyCode;
    }

    public void setCurrencyCode(final CurrencyCode currencyCode)
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
