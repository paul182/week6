package com.leszko.calculator;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

 /**
 * The class Calculator
 */ 
@Service
public class Calculator {
        final static int UML_NUMBER1 = 3;
	@Cacheable("sum")

	/** 
	 *
	 * Sum
	 *
	 * @param a  the a
	 * @param b  the b
	 * @return int
	 */
	public int sum(int a, int b) { 

		return a + b;
	}
}
