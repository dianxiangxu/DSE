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
public class Example27 {
     static int example(int x1, int y1) {

        if(y1>5)
            x1= x1+2;
         
        while (x1 < 100 ) 
        {
            x1 =  x1 + 30;
        }
        
        if(y1<50)
            x1= x1*3;
        else
            x1= x1*2;
        
        int z = x1 + y1;
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
