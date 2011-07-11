package hu.itkodex.litest.testcase;

import hu.itkodex.litest.fixture.TestFixture;

public interface ObjectTestSuite<S, F extends TestFixture<S>> {
   public <B extends TestFixture<?>> B acquireFixture( Class<B> requiredType ) throws NoSuchFixtureDefinitionException;
   public <B extends TestFixture<?>> B acquireFixture( String beanName, Class<B> requiredType ) throws NoSuchFixtureDefinitionException;
   public void afterAllTests();
   public void afterEachTest();
   public void beforeAllTests();
   public void beforeEachTest();
   public void executeTestCases();
   public F getFixture();
   public Class<F> getFixtureClass();
   public S getSUT();
   public Class<S> getSUTClass();
}
