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
public class Example29 {
    static int example(int x1, int y1, int z1) {
         
        int x = x1;
        int y = y1;
        int z = z1;
        
        int k = 0;
//        x= 2*x;
//        y= y+1;
//        y = 2*y;
        
        while(k<3)
        {
            if((k % 2) == 0)
            {
                x = x+y;     
                
            }
            else
            {
                x = x-y;
            }
            k++;    
        }    
        if(x > 5)
            z = x + y +z ;
        return z;
    }
    public static int noOfArguments = 3;
    public static void main(String[] args)
    {
        int x = Integer.valueOf(args[0]);
        int y = Integer.valueOf(args[1]);
        int z = Integer.valueOf(args[2]);
        System.out.println(example(x,y,z));
    }
}
