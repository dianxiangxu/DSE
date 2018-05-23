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
public class Example8 {
     static int example(int x1, int y1) {

        if (x1 > 0  && y1>1 ) 
        {
            x1 = x1 + 3;
        }
        int z = m(x1);
        return z;
    }
     
     static int m(int x)
     {
         int y = 2*x;
         return y;
     }
     
     public static int noOfArguments = 2;
    public static void main(String[] args)
    {
        int x = Integer.valueOf(args[0]);
        int y = Integer.valueOf(args[1]);
        System.out.println(example(x,y));
    }
}
