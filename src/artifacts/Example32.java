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
public class Example32 {
     static int example(int[] x) {

        if (x[0] > 0  && x[1]>1 ) 
        {
            x[0] = x[1] + 3;
        }
        int z = x[0]+x[1];
        return z+2;
    }
   
    public static int noOfArguments = 3;
    public static void main(String[] args)
    {
//        int x = Integer.valueOf(args[0]);
//        int y = Integer.valueOf(args[1]);
        int[] a = {10,20};
        System.out.println(example(a));
    }
}
