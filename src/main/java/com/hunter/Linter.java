package com.hunter;

public class Linter {

  public static void main(String[] args) {
    int exitCode = 0;
    if (args.length < 1) {
      System.out.println("✗ Invalid commit message");
      System.out.println("Error: No arguments provided");
      exitCode = -1;
      System.exit(exitCode);
    }
    String input = args[0];

    try {
      Linter.checkFirstLine(input);
    } catch (IllegalArgumentException e) {
      System.out.println("✗ Invalid commit message");
      System.out.println("Error: " + e.getMessage());
      exitCode = -1;
      System.exit(exitCode);
    }

    System.out.println("✓ Valid commit message");
    System.exit(exitCode);
  }

  public static boolean checkFirstLine(String input) throws IllegalArgumentException {
    int index = input.indexOf(":");
    if (index < 0) {
      throw new IllegalArgumentException("Invalid input, no colon provided.");
    }
    String type = input.substring(0, index);
    String description = input.substring(index + 1);

    return checkType(type) && checkDescription(description);
  }

  private static boolean checkType(String type) throws IllegalArgumentException {
    if (type.contains(" ")) {
      throw new IllegalArgumentException("Type cannot contain spaces.");
    }

    int index = type.indexOf("(");
    String enumType = type;
    String scope = "";
    if (index > -1) {
      enumType = type.substring(0, index);
      scope = type.substring(index + 1);
    }

    try {
      Type.valueOf(enumType.toUpperCase());
    } catch (IllegalArgumentException e) {
      throw new IllegalArgumentException("Invalid type: (feat, bug, chore, etc.)");
    }

    if (scope.length() > 0 && !scope.endsWith(")") || scope.contains("(")) {
      throw new IllegalArgumentException("Invalid scope: Must end with parentheses and be one word");
    }

    return true;
  }

  private static boolean checkDescription(String description) throws IllegalArgumentException {
    if (description.startsWith(" ") && description.trim().length() > 1) {
      return true;
    }
    throw new IllegalArgumentException(
        "Invalid description: Must contain a space after the colon and at least one character after.");
  }
}
