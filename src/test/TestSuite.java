/*
 * Course: CSC1120
 * Lab 9: Call Stack
 * Unit Tests
 * Andrew keenan
 * 3-20-24
 */
package test;

import keenana.FileReaderUtils;
import keenana.IntStack;
import keenana.ProgramStack;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.EmptyStackException;
import java.util.Optional;
import java.util.OptionalInt;

/**
 * Unit tests for Call Stack Lab
 */
public class TestSuite {
    private IntStack is;
    private ProgramStack ps;

    /**
     * Tests isVoidReturn()
     */
    @Test
    @DisplayName("Testing FileReaderUtils.isVoidReturn()")
    public void testIsVoidReturn() {
        Assertions.assertTrue(FileReaderUtils.isVoidReturn("return"));
        Assertions.assertTrue(FileReaderUtils.isVoidReturn("  return"));
        Assertions.assertFalse(FileReaderUtils.isVoidReturn("return 5"));
        Assertions.assertFalse(FileReaderUtils.isVoidReturn(""));
        Assertions.assertFalse(FileReaderUtils.isVoidReturn("ome()"));
    }

    /**
     * tests parseReturnValue()
     */
    @Test
    @DisplayName("Testing FileReaderUtils.parseReturnValue()")
    public void testParseReturnValue() {
        final int[] values = {5, 7};
        Assertions.assertEquals(OptionalInt.of(values[0]),
                FileReaderUtils.parseReturnValue("return 5"));
        Assertions.assertEquals(OptionalInt.of(values[1]),
                FileReaderUtils.parseReturnValue("  return 7"));
        Assertions.assertEquals(OptionalInt.empty(), FileReaderUtils.parseReturnValue("return"));
        Assertions.assertEquals(OptionalInt.empty(), FileReaderUtils.parseReturnValue("  return"));
        Assertions.assertEquals(OptionalInt.empty(), FileReaderUtils.parseReturnValue("one()"));
        Assertions.assertEquals(OptionalInt.empty(), FileReaderUtils.parseReturnValue(""));
    }

    /**
     * tests parseMethodName()
     */
    @Test
    @DisplayName("Testing FileReaderUtils.parseMethodName()")
    public void testParseMethodName() {
        Assertions.assertEquals(Optional.of("first"),
                FileReaderUtils.parseMethodName("first()"));
        Assertions.assertEquals(Optional.of("first"),
                FileReaderUtils.parseMethodName("void first()"));
        Assertions.assertEquals(Optional.of("first"),
                FileReaderUtils.parseMethodName("  int first()"));
        Assertions.assertEquals(Optional.of("first"),
                FileReaderUtils.parseMethodName("   first () "));
        Assertions.assertEquals(Optional.empty(), FileReaderUtils.parseMethodName("return"));
        Assertions.assertEquals(Optional.empty(), FileReaderUtils.parseMethodName("first"));
        Assertions.assertEquals(Optional.empty(), FileReaderUtils.parseMethodName(""));
        Assertions.assertEquals(Optional.empty(), FileReaderUtils.parseMethodName("       "));
        Assertions.assertEquals(Optional.empty(), FileReaderUtils.parseMethodName("r9483h"));
        Assertions.assertEquals(Optional.empty(), FileReaderUtils.parseMethodName("()"));
    }

    /**
     * tests parseArguments()
     */
    @Test
    @DisplayName("Testing FileReaderUtils.parseArguments()")
    public void testParseArguments() {
        final int[] empty = new int[0];
        final int[] singleValue = {5};
        final int[] multipleValues = {4, 2, 7};
        Assertions.assertNull(FileReaderUtils.parseArguments("    return"));
        Assertions.assertNull(FileReaderUtils.parseArguments("return 5"));
        Assertions.assertArrayEquals(empty, FileReaderUtils.parseArguments("first()"));
        Assertions.assertArrayEquals(singleValue, FileReaderUtils.parseArguments("second(5) "));
        Assertions.assertArrayEquals(multipleValues,
                FileReaderUtils.parseArguments(" second(4, 2,7)"));
    }

    /**
     * Creates a new IntStack and Stack before each test
     */
    @BeforeEach
    public void setup() {
        is = new IntStack();
        ps = new ProgramStack();
    }

    /**
     * tests size()
     */
    @Test
    @DisplayName("Testing IntStack size()")
    public void testingIntStackSize() {
        final int[] values = {5, 4};
        Assertions.assertEquals(0, is.size());
        is.push(values[0]);
        Assertions.assertEquals(1, is.size());
        is.push(values[1]);
        Assertions.assertEquals(2, is.size());
        is.pop();
        Assertions.assertEquals(1, is.size());
    }

    /**
     * tests push()
     */
    @Test
    @DisplayName("Testing IntStack push()")
    public void testingIntStackPush() {
        Assertions.assertEquals(0, is.size());
        is.push(3);
        Assertions.assertEquals(1, is.size());
        Assertions.assertEquals(3, is.peek());
        is.push(4);
        Assertions.assertEquals(2, is.size());
        Assertions.assertEquals(4, is.peek());
    }

    /**
     * tests pop()
     */
    @Test
    @DisplayName("Testing IntStack pop()")
    public void testingIntStackPop() {
        Assertions.assertThrows(EmptyStackException.class, () -> is.pop());
        is.push(3);
        is.push(4);
        is.push(1);
        is.push(2);
        Assertions.assertEquals(2, is.pop());
        Assertions.assertEquals(1, is.pop());
        Assertions.assertEquals(4, is.pop());
        Assertions.assertEquals(3, is.pop());
        Assertions.assertThrows(EmptyStackException.class, () -> is.pop());
    }

    /**
     * tests peek()
     */
    @Test
    @DisplayName("Testing IntStack peek()")
    public void testingIntStackPeek() {
        Assertions.assertThrows(EmptyStackException.class, () -> is.peek()); // throws on empty
        is.push(3);
        is.push(4);
        is.push(1);
        is.push(2);
        Assertions.assertEquals(2, is.peek()); // sees top value on stack
        Assertions.assertEquals(2, is.peek()); // doesn't remove on peek
        is.pop();
        Assertions.assertEquals(1, is.peek()); // sees new top value
    }

    /**
     * tests isEmpty()
     */
    @Test
    @DisplayName("Testing IntStack isEmpty()")
    public void testingIntStackIsEmpty() {
        Assertions.assertTrue(is.isEmpty());
        is.push(4);
        Assertions.assertFalse(is.isEmpty());
    }

    /**
     * tests IntStack toString()
     */
    @Test
    @DisplayName("Testing IntStack toString()")
    public void testingIntStackToString() {
        String emptyStack = """
                |          |
                |----------|
                +----------+""";
        String singleDigit = """
                |          |
                |----------|
                |        1 |
                +----------+""";
        String multipleSingleDigits = """
                |          |
                |----------|
                |        3 |
                |        2 |
                |        1 |
                +----------+""";
        String multipleDigits = """
                |          |
                |----------|
                |      -30 |
                |    50000 |
                +----------+""";
        final int[] values = {50_000, -30};
        Assertions.assertEquals(emptyStack, is.toString());
        is.push(1);
        Assertions.assertEquals(singleDigit, is.toString());
        is.push(2);
        is.push(3);
        Assertions.assertEquals(multipleSingleDigits, is.toString());
        is.pop();
        is.pop();
        is.pop();
        is.push(values[0]);
        is.push(values[1]);
        Assertions.assertEquals(multipleDigits, is.toString());
    }

    /**
     * tests methodToProgramCounter()
     */
    @Test
    @DisplayName("Testing ProgramStack methodToProgramCounter()")
    public void testMethodToProgramCounter() {
        final int[] values = {1530, 1627, 6895};
        Assertions.assertEquals(values[0], ps.methodToProgramCounter("one"));
        Assertions.assertEquals(values[1], ps.methodToProgramCounter("two", 3));
        Assertions.assertEquals(values[2], ps.methodToProgramCounter("three", 2, 3, 1, 4));
    }

    /**
     * tests callMethod()
     */
    @Test
    @DisplayName("Testing ProgramStack callMethod()")
    public void testCallMethod() {
        String line1 = """
                |          |
                |----------|
                |        1 |
                |     1530 |
                +----------+""";
        String line2 = """
                |          |
                |----------|
                |        2 |
                |        4 |
                |     1627 |
                |        1 |
                |     1530 |
                +----------+""";
        ps.callMethod("one");
        Assertions.assertEquals(line1, ps.toString());
        ps.callMethod("two", 4);
        Assertions.assertEquals(line2, ps.toString());
    }

    /**
     * tests returnFromMethod
     */
    @Test
    @DisplayName("Testing ProgramStack returnFromMethod(value)")
    public void testReturnFromMethod() {
        String line3 = """
                |          |
                |----------|
                |        2 |
                |        8 |
                |     1530 |
                +----------+""";
        final int returnValue = 8;
        ps.callMethod("one");
        ps.callMethod("two", 4);
        ps.returnFromMethod(returnValue);
        Assertions.assertEquals(line3, ps.toString());
    }

    /**
     * tests returnFromMethod()
     */
    @Test
    @DisplayName("Testing ProgramStack returnFromMethod()")
    public void testReturnFromMethodNoValue() {
        String line4 = """
                |          |
                |----------|
                +----------+""";
        final int returnValue = 8;
        ps.callMethod("one");
        ps.callMethod("two", 4);
        ps.returnFromMethod(returnValue);
        ps.returnFromMethod();
        System.out.println(ps);
        Assertions.assertEquals(line4, ps.toString());
    }
}
