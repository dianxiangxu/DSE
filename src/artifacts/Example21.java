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
public class Example21 {
    static int example(int x, int y) {

        if (x != 0) {
            x = x + 0;
            while (y > 0) {
                x = x + 2;
                y = y - 1;
            }
        } else {
            x = x - 2;
        }

        if (x != 0) {
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
