/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package artifacts;

/**
 *
 * @author Md Nazmul Karim
 */
public class Example22 {
     static int example(int x, int y) {
        if (x == 0) {
            y = 0;
        } else {
            y = 0;
        }

        if (y > 0) {
            x = y;
        } else {
            x = y + 1;
        }

        if (x >= 0) {
            System.out.println("assert");
        }
        return x;
    }
     
     public static int noOfArguments = 2;
    public static void main(String[] args)
    {
        int x = Integer.valueOf(args[0]);
        int y = Integer.valueOf(args[1]);
        System.out.println(example(x,y));
    }
}
