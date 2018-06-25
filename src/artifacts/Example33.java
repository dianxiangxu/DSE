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
public class Example33 {
     static int example(int x, int y) {

        if (x > 0  && y>1 ) 
        {
            x = x + 3;
        }
        
        int z = x+y;
        return z+2;
    }
   
    public static int noOfArguments = 2;
    public static void main(String[] args)
    {
        int x = Integer.valueOf(args[0]);
        int y = Integer.valueOf(args[1]);

        System.out.println(example(x,y));
    }
}
