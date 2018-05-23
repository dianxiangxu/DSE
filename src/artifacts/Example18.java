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
public class Example18 {
    public double example_7(double x, double y) {
        double ret = -1.00;
        if (x == 0) {
            ret = x * 2;
        } else if (y >= ret) {
            ret = y;
        }

        if (ret == 0) {
            System.out.println("assertion violated");
        }
        return ret;
    }
    
    public static int noOfArguments = 2;
    public static void main(String[] args)
    {
        int x = Integer.valueOf(args[0]);
        int y = Integer.valueOf(args[1]);
        //System.out.println(example(x,y));
    }
}
