package ch.epfl.sweng.defensive.code.coverage;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ch.epfl.sweng.defensive.code.coverage.CaseStudy;

import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class CaseStudyTest {
  private ByteArrayOutputStream out = new ByteArrayOutputStream();
  private PrintStream stdout;

  @BeforeEach
  public void before() {
    stdout = System.out;
    System.setOut(new PrintStream(out));
  }

  @AfterEach
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
