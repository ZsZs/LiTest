package hu.itkodex.litest.fitnesse;

import java.util.Map;

public interface FitNesseAssertion<T> {
   public boolean evaluateAssert( Map<String, T> variables, String variable );
}
