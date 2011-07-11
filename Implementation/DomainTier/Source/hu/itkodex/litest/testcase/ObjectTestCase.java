package hu.itkodex.litest.testcase;

public interface ObjectTestCase {
   public void setUpFixture();
   public void excerciseSut();
   public void verifyOutCome();
   public void tearDownFixture();
}
