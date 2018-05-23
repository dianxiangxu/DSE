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
public class Example14 {
     static int example(int x1, int y1) {
        int a = x1 * 5;

        if (a != 11) {
            x1 = y1 * 2;
        }

        if (x1 == 6) {
            while (x1 != 2) {
                y1 = y1 + 2;
                while (y1 > 0) {
                    y1--;
                }
                if (x1 >= 0) {
                    System.out.println("assert");
                }
                x1 = 0;
            }
        } else {
            y1 = 5;
        }
        x1++;
        y1 = -y1;
        return y1;

    }
     
     public static int noOfArguments = 2;
    public static void main(String[] args)
    {
        int x = Integer.valueOf(args[0]);
        int y = Integer.valueOf(args[1]);
        System.out.println(example(x,y));
    }
}
