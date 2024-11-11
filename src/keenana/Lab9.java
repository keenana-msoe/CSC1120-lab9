/*
 * Course: CSC 1120 121
 * Term: Spring 2024
 * Assignment: Lab 9
 * Name: Andrew Keenan
 * Created: 3-20-2024
 */
package keenana;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.OptionalInt;
import java.util.Scanner;

/**
 * driver class for the entireity of lab 9
 */
public class Lab9 {
    public static void main(String[] args) {
        ProgramStack ps = new ProgramStack();
        String file;
        Scanner scan = new Scanner(System.in);
        System.out.println("Please enter the file name you wish to read");
        file = scan.next();
        Scanner f;
        try{
            File input = new File(file);
            f = new Scanner(input);
        } catch (FileNotFoundException e){
            System.out.println("Error with FIleIO file doesnt exist");
            f = null;
        }

        while (f.hasNext()){
            String s = f.nextLine();
            if (!s.contains("blah")){
                if (s.contains("return")){
                    if (FileReaderUtils.isVoidReturn(s)){
                        ps.returnFromMethod();
                    } else {
                        try {
                            ps.returnFromMethod(Integer.parseInt(
                                    String.valueOf(FileReaderUtils.parseReturnValue(s))));
                        } catch (NumberFormatException e){
                            ps.returnFromMethod();
                        }
                    }
                } else {
                    String name = String.valueOf(FileReaderUtils.parseMethodName(s));
                    int[] arguments = FileReaderUtils.parseArguments(s);
                    ps.callMethod(name, arguments);
                }
            }
            System.out.println(s);
            System.out.println(ps.toString());
        }
        System.out.println("Call stack complete");
    }
}
