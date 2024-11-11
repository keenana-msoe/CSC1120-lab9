/*
 * Course: CSC 1120 121
 * Term: Spring 2024
 * Assignment: Lab 9
 * Name: Andrew Keenan
 * Created: 3-20-2024
 */
package keenana;


import java.util.Optional;
import java.util.OptionalInt;

/**
 * the class that describes how to parse the data read form a file
 */
public class FileReaderUtils {
    /**
     * determines if it is a void return value
     * @param aReturn the string with the method call
     * @return if it is a boolean or not
     */
    public static boolean isVoidReturn(String aReturn) {
        return aReturn.trim().equals("return");
    }

    /**
     * determines the return value
     *
     * @param s the input string
     * @return the return value if it is not void
     */
    public static OptionalInt parseReturnValue(String s) {
        s = s.trim();

        if (!s.equals("return") && s.contains("return")){
            return OptionalInt.of(Integer.parseInt(s.substring(s.indexOf(" ") + 1)));
        }
        return OptionalInt.empty();
    }

    /**
     * parses the method name based off of the line of text
     * @param s the input string line
     * @return the methodName as a string
     */
    public static Optional<String> parseMethodName(String s) {
        int p1 = s.indexOf('(');

        if (p1 <= 0) {
            return Optional.empty();
        }

        if (s.charAt(p1 - 1) == ' ') {
            p1--;
        }

        int start = p1 - 1;
        while (start >= 0 && s.charAt(start) != ' ') {
            start--;
        }
        return Optional.of(s.substring(start + 1, p1));
    }

    /**
     * parses the arguments from the string
     * @param s the string passed in
     * @return the array of args
     */
    public static int[] parseArguments(String s) {
        int[] args;
        s = s.replace(" ", "");

        if (!s.contains("(")) {
            return null;
        }

        String arg = s.substring(s.indexOf("(") + 1, s.indexOf(")"));
        if (arg.isEmpty()){
            return new int[0];
        }
        String[] sargs = arg.split(",");
        args = new int[sargs.length];

        try {
            for (int i = 0; i < sargs.length; i++){
                args[i] = Integer.parseInt(sargs[i]);
            }
        } catch (IllegalArgumentException e){
            System.out.println("error parsing args");
        }
        return args;
    }
}
