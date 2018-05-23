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
public class Example5 {
    static int example(int x1, int y1) {
         int z =0;
        if (x1 > 1  ) 
        {
            if(y1>5)
            { 
                z=2;
            }
            else
            {
                z =1;
            }
         }
        else
        {
            if(y1>7)
            {
                z = 7;
            }
            else
            {
                z =9;
            }
        }
        
        z = x1 + y1;
        return z;
    }
    
    public static int noOfArguments = 2;
    public static void main(String[] args)
    {
        int x = Integer.valueOf(args[0]);
        int y = Integer.valueOf(args[1]);
        System.out.println(example(x,y));
    }
}
