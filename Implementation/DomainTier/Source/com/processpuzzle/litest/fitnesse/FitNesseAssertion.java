package com.processpuzzle.litest.fitnesse;

import java.util.Map;

public interface FitNesseAssertion<T> {
   public boolean evaluateAssert( Map<String, T> variables, String variable );
}
