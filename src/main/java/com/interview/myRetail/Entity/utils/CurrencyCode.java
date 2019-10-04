package com.interview.myRetail.Entity.utils;

import com.fasterxml.jackson.annotation.JsonValue;

/**
 *
 * @author Andrew Hunkele
 */
public enum CurrencyCode
{
    USD,
    EUR,
    JPY,
    MUR;
	
	@JsonValue
	public String getCurrencyCode()
	{
		return this.name();
	}
    
}
