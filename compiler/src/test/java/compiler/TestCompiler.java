package compiler;

import static org.junit.Assert.*;

import org.junit.Test;

public class TestCompiler {

  @Test
  public void test() {
    String[] args = {
        "../../scm2vonda/src/test/resources/introduction/sceneflow.xml",
        "../vonda_example_project", "true", "false"
    };
    Main.main(args);
    assertTrue(true);
  }

}
