package com.hunter;

import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class LinterTest {

  @Test
  public void testValidCommitWithTypeAndDescription() throws IllegalArgumentException {
    assertTrue(Linter.checkFirstLine("feat: this was added as our first test"));
  }

  @Test
  public void testValidCommitWithTypeScopeAndDescription() throws IllegalArgumentException {
    assertTrue(Linter.checkFirstLine("feat(bugs): this was added as our first test"));
  }

  @Test
  public void testInvalidWithMissingParentheses() {
    assertThrows(IllegalArgumentException.class,
        () -> Linter.checkFirstLine("feat(bugs: this was added as our first test"));
  }

  @Test
  public void testInvalidWithMissingParentheses2() {
    assertThrows(IllegalArgumentException.class,
        () -> Linter.checkFirstLine("featbugs): this was added as our first test"));
  }

  @Test
  public void testInvalidWithMissingDescription() {
    assertThrows(IllegalArgumentException.class, () -> Linter.checkFirstLine("feat: "));
  }

  @Test
  public void testInvalidWithMissingColon() {
    assertThrows(IllegalArgumentException.class, () -> Linter.checkFirstLine("feat this is a description"));
  }

  @Test
  public void testInvalidWithMissingType() {
    assertThrows(IllegalArgumentException.class, () -> Linter.checkFirstLine("this is a description"));
  }

  @Test
  public void testInvalidWithMissingSpaceAfterColon() {
    assertThrows(IllegalArgumentException.class, () -> Linter.checkFirstLine("feat:this is a description"));
  }

  @Test
  public void testInvalidWithExtraSpaceBeforeColon() {
    assertThrows(IllegalArgumentException.class, () -> Linter.checkFirstLine("feat : this is a description"));
  }

  @Test
  public void testInvalidWithSpacesInScope() {
    assertThrows(IllegalArgumentException.class,
        () -> Linter.checkFirstLine("feat(scope in scop): this is a description"));
  }

  @Test
  public void testInvalidWithTwoScopes() {
    assertThrows(IllegalArgumentException.class,
        () -> Linter.checkFirstLine("feat(scope)(scope): this is a description"));
  }
}
