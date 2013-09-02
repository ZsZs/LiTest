package com.processpuzzle.litest.fitnesse;

import fit.Fixture;
import fit.FixtureLoader;
import fitlibrary.DoFixture;

public class GenericApplicationTest extends DoFixture {
   
   public Fixture instantiate( String fixtureName ) throws Throwable {
      Fixture fixture = FixtureLoader.instance().disgraceThenLoad( fixtureName );
      return fixture;
   }
}
