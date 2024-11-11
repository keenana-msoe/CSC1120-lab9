/*
 * Course: CSC 1120 121
 * Term: Spring 2024
 * Assignment: Lab 9
 * Name: Andrew Keenan
 * Created: 3-20-2024
 */
package keenana;

/**
 * class to simulate the program stack in the JVM
 */
public class ProgramStack {
    private IntStack is;

    /**
     * constructor for the program stack using an intstack
     */
    public ProgramStack() {
        is = new IntStack();
    }

    /**
     * simulates calling a method on the stack
     * pushes the correct values to the stack
     * @param name the name of the method
     * @param arguments the arguments for the method
     */
    public void callMethod(String name, int... arguments) {
        if (arguments == null){
            arguments = new int[0];
        }
        is.push(methodToProgramCounter(name, arguments));
        for (int i : arguments){
            is.push(i);
        }
        is.push(1 + arguments.length);
    }

    /**
     * simulates returning from a method with a return value
     * @param returnValue the value to be returned from the method
     */
    public void returnFromMethod(int returnValue) {
        returnFromMethod();
        if (is.peek() != 0){
            int size = is.pop();
            is.push(returnValue);
            is.push(size + 1);
        }
    }

    /**
     * simulates returning from a method without a return value or a
     * void method
     */
    public void returnFromMethod() {
        int size = is.pop();
        for (int i = 0; i < size; i++){
            is.pop();
        }
    }

    /**
     * simulates the transitio from a method name to its program counter int
     * uses a basic formula to do so
     * @param name the same of the method being added
     * @param arguments the arguments of the method being translated
     * @return the int value of the program counter
     */
    public int methodToProgramCounter(String name, int... arguments) {
        int retVal = 0;
        for (int i = 0; i < name.length(); i++){
            retVal += name.charAt(i);
            retVal = retVal * 2;
        }
        if (arguments.length > 0){
            retVal++;
        }
        return retVal;
    }

    @Override
    public String toString() {
        return is.toString();
    }
}
