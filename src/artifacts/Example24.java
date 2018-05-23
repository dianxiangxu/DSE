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
public class Example24 {
    /*
	 * New example to really see how making new expressions work
	 * @author Hannah Johnson
	 */


    static int example(int x1, int y1) {
        x1 = x1 * 2;
        while (x1 != 2) {
            y1 = y1 + 2;
            while (y1 > 0) {
                y1--;
            }
            if (x1 == 0) {
                System.out.println("assert");
                System.exit(2);
            }
            x1++;
        }
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
