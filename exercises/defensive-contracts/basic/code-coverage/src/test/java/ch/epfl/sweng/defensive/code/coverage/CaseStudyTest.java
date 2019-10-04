package ch.epfl.sweng.defensive.code.coverage;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import ch.epfl.sweng.defensive.code.coverage.CaseStudy;

import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class CaseStudyTest {
  private ByteArrayOutputStream out = new ByteArrayOutputStream();
  private PrintStream stdout;

  @Before
  public void before() {
    stdout = System.out;
    System.setOut(new PrintStream(out));
  }

  @After
  public void after() {
    System.setOut(stdout);
  }

  @Test
  public void demonstrateTest() {
    CaseStudy.demonstrate();
    String expected = String.format("The lecturer%s", System.lineSeparator());
    String obtained = out.toString();
    assertEquals(obtained, expected);
  }
}