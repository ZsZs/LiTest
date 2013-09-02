package com.processpuzzle.litest.fixture;

import com.ibm.icu.text.MessageFormat;

public class DuplicationFixtureInstantiationException extends RuntimeException {
   private static final long serialVersionUID = -2138670058113000486L;
   private static final String message = "Composite fixture can't intantiate more than in component fixture from class: '{0}'";
   private Class<? extends TestFixture<?>> fixtureClass;
   
   DuplicationFixtureInstantiationException( Class<? extends TestFixture<?>> fixtureClass ) {
      super( MessageFormat.format( message, new Object[] {fixtureClass.getName()} ));
      this.fixtureClass = fixtureClass;
   }
   
   public Class<? extends TestFixture<?>> getFixtureClass() { return fixtureClass; } 
}
