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
public class Example19 {
    public double example_6(double x, double ret) {
        double y = 0.0 * x;
        double ter = ret / 5.0;
        int z = (int) y;
        if (x > ret && y != z) {
            ret = 2 * ter + z;
        }
        return ret;
    }
    
    public static int noOfArguments = 2;
    public static void main(String[] args)
    {
        int x = Integer.valueOf(args[0]);
        int y = Integer.valueOf(args[1]);
      //  System.out.println(example(x,y));
    }
}
