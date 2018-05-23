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
public class Example13 {
    static int example(int x1, int y1) {
        int a = x1 + 5;
        int b = a - y1;

        if (b > a || b == a) {
            a = a * 5;
        } else {
            b = b * 5;
        }
        x1 = a * b;
        y1 = a / b;

        return x1;
    }
    
    public static int noOfArguments = 2;
    public static void main(String[] args)
    {
        int x = Integer.valueOf(args[0]);
        int y = Integer.valueOf(args[1]);
        System.out.println(example(x,y));
    }
}
