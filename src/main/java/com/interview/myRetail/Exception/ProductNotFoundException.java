package com.interview.myRetail.Exception;

import org.springframework.core.NestedRuntimeException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * 
 * @author Andy
 *
 */
@ResponseStatus(value = HttpStatus.NOT_FOUND)
public final class ProductNotFoundException extends NestedRuntimeException
{

	private static final long serialVersionUID = -7266369166998226580L;

	public ProductNotFoundException(final String message)
	{
		super(message);
	}
}
